package com.applidium.graphqlient;

import com.applidium.graphqlient.tree.QLElement;
import com.applidium.graphqlient.tree.QLFragment;
import com.applidium.graphqlient.tree.QLLeaf;
import com.applidium.graphqlient.tree.QLNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QLParser {

    public static final String QUERY_KEYWORD = "query";
    public static final String FRAGMENT_KEYWORD = "fragment";
    private String initialString;
    private String toParse;
    private QLQuery query;
    private List<QLFragment> fragments;
    private final Map<Integer, QLNode> currentPosition = new HashMap<>();
    private int elevation = 0;
    private QueryDelimiter delimiter;

    public QLParser() {
        delimiter = new QueryDelimiter();
    }

    public QLParser(String toParse) {
        super();
        toParse = toParse.replaceAll("[\n\r]", "");
        this.toParse = initialString = toParse;
    }

    public void setToParse(String toParse) {
        toParse = toParse.replaceAll("[\n\r]", "");
        this.toParse = initialString = toParse;
    }

    public QLQuery begin() {
        currentPosition.clear();
        elevation = 0;
        if (toParse == null || toParse.isEmpty()) {
            return null; // TODO (kelianclerc) 23/5/17 create exception
        }
        parseFragment(initialString);
        getHeader();
        getRootElement();
        return query;
    }

    private void parseFragment(String searchString) {
        Pattern pattern = Pattern.compile(FRAGMENT_KEYWORD + "\\s[a-zA-Z0-9]*\\son\\s[a-zA-Z0-9]*\\s\\{");
        Matcher matcher = pattern.matcher(searchString);
        if (matcher.find()) {
            int beginIndex = matcher.start();
            String fragmentString = blockFetch(searchString, beginIndex);
            processFragment();
            parseFragment(searchString.substring(beginIndex + fragmentString.length() + 1));
        }
    }

    private void processFragment() {
        // TODO (kelianclerc) 31/5/17  
    }

    private void getHeader() {
        int endIndex = toParse.indexOf("{");
        if (endIndex < 0) {
            return; // TODO (kelianclerc) 23/5/17 create exception
        }

        String substring = toParse.substring(0, endIndex);
        if (substring.startsWith(QUERY_KEYWORD)) {
            parseQuery(endIndex, substring);
        }
        else if (substring.length() == 0) {
            parseQuery(0, "");
        }
    }

    private void parseQuery(int endIndex, String substring) {
        substring = substring.replace(QUERY_KEYWORD, "");
        substring = substring.replaceAll(" ", "");
        QLElement element = createElementFromString(substring);
        this.query = new QLQuery(
            element.getName() != null && element.getName().length() > 0 ? element.getName() : null
        );
        List<QLVariablesElement> params = new ArrayList<>();
        for (String key: element.getParameters().keySet()) {
            Object o = element.getParameters().get(key);
            if (o instanceof QLVariablesElement) {
                params.add((QLVariablesElement) o);
            }
        }
        this.query.setParameters(params);
        trimString(endIndex + 1);
        this.toParse = toParse.replaceAll(" ", "");
    }

    private void getRootElement() {
        int endIndex = toParse.indexOf("{");
        if (endIndex < 0) {
            return; // TODO (kelianclerc) 23/5/17 create exception
        }

        String substring = toParse.substring(0, endIndex);
        substring = substring.replaceAll(" ", "");

        QLElement element = createElementFromString(substring);
        QLNode node = new QLNode(element);
        currentPosition.put(elevation,  node);
        elevation ++;
        trimString(endIndex + 1);

        processNextField();
    }

    private void processNextField() {

        if (toParse.length() <= 0) {
            return;
        }

        delimiter.analyze(toParse);


        if (delimiter.isNextCloseCurly()) {
            handleClosingCurly();
        }
        else if (delimiter.isNextSimpleField()) {
            handleSimpleField(delimiter.endCarret);
        }
        else if (delimiter.isNextFieldWithParameters()) {
            handleFieldWithParameters(delimiter.endCarret);
        }
        else if (delimiter.isNextNodeWithoutParams()) {
            handleNodeWithoutParameter(delimiter.endCarret);
        }
        else if (delimiter.isNextLastField()) {
            handleLastField(delimiter.endCarret);
        }
    }

    private void handleClosingCurly() {
        elevation--;
        if (elevation < 0) {
            return;
        }
        if (elevation == 0) {
            query.append(currentPosition.get(elevation));
        }
        trimString(1);
        processNextField();
    }

    private void handleSimpleField(int nextCommaIndex) {
        QLElement field = createElementFromString(toParse.substring(0, nextCommaIndex));
        trimString(nextCommaIndex + 1);
        currentPosition.get(elevation - 1).addChild(new QLLeaf(field));
        processNextField();
    }

    private void handleFieldWithParameters(int nextCloseBraceIndex) {
        QLElement field = createElementFromString(toParse.substring(0, nextCloseBraceIndex + 1));
        trimString(nextCloseBraceIndex + 1);
        if (toParse.charAt(0) == '{') {
            QLNode childNode = new QLNode(field);
            if (elevation > 0) {
                currentPosition.get(elevation - 1).addChild(childNode);
            }
            currentPosition.put(elevation, childNode);
            elevation++;
            trimString(1);
        }
        else {
            currentPosition.get(elevation - 1).addChild(new QLLeaf(field));
            if (toParse.charAt(0) == ',') {
                trimString(1);
            }
        }
        processNextField();
    }

    private void handleNodeWithoutParameter(int nextCurlyIndex) {
        QLElement field = createElementFromString(toParse.substring(0, nextCurlyIndex));
        QLNode childNode = new QLNode(field);
        if (elevation > 0) {
            currentPosition.get(elevation - 1).addChild(childNode);
        }
        currentPosition.put(elevation, childNode);
        elevation++;
        trimString(nextCurlyIndex + 1);

        processNextField();
    }

    private void handleLastField(int endCarret) {
        QLElement field = createElementFromString(toParse.substring(0, endCarret));
        currentPosition.get(elevation - 1).addChild(new QLLeaf(field));
        trimString(endCarret + 1);
        processNextField();
    }

    private QLElement createElementFromString(String substring) {
        substring.replaceAll(" ", "");
        if (substring.length() > 0) {
            String[] stringList = substring.split("[(]");

            QLElement element;
            element = getFieldName(stringList[0]);
            if (stringList.length > 1) {
                element.setParameters(getParameters(stringList[1]));
            }
            return element;
        } else {
            return new QLElement("");
        }
    }

    private QLElement getFieldName(String s) {
        QLElement element;
        String name = s;
        if (name.indexOf(":")>0) {
            String[] aliasName = name.split("[:]");
            element = new QLElement(aliasName[1]);
            element.setAlias(aliasName[0]);
        } else {
            element = new QLElement(name);
        }
        return element;
    }

    private Map<String, Object> getParameters(String stringParameters) {
        stringParameters = stringParameters.replace(")", "");
        Map<String, Object> params = new HashMap<>();
        String[] stringParametersSplit = stringParameters.split("[,]");
        for (String param : stringParametersSplit) {
            String[] unit = param.split("[:]");
            if (unit.length > 1) {
                if (unit[0].charAt(0) == '$') {
                    params.put(unit[0], parseVariableType(unit));
                } else if (unit[1].charAt(0) == '$') {
                    params.put(unit[0], new QLVariablesElement(unit[1].replace("$", "")));
                } else {
                    unit[1]= unit[1].replaceAll("\"", "");
                    params.put(unit[0], unit[1]);
                }
            }
        }
        return params;
    }

    private QLVariablesElement parseVariableType(String[] unit) {
        QLVariablesElement element = new QLVariablesElement();
        if (unit[1].contains("!")) {
            element.setMandatory(true);
            unit[1] = unit[1].replace("!", "");
        } else {
            element.setMandatory(false);
        }
        element.setName(unit[0].replace("$",""));
        switch (unit[1]) {
            case "Boolean":
                element.setType(QLType.BOOLEAN);
                break;
            case "String":
                element.setType(QLType.STRING);
                break;
            case "Int":
                element.setType(QLType.INT);
                break;
            case "ID":
                element.setType(QLType.ID);
                break;
            case "Float":
                element.setType(QLType.FLOAT);
                break;
            default:
                // TODO (kelianclerc) 23/5/17 error or enum
                break;
        }
        return element;
    }

    private String blockFetch(String globalString, String beginString) {
        int beginIndex = globalString.indexOf(beginString);
        return blockFetch(globalString, beginIndex);
    }

    private String blockFetch(String globalString, int beginIndex) {
        String substring = globalString.substring(beginIndex);
        int localElevation = 0;
        boolean firstOpening = true;
        for (int i = 0; i < substring.length(); i++) {
            if (substring.charAt(i) == '{') {
                if (firstOpening) {
                    firstOpening = false;
                }
                localElevation++;
            } else if (substring.charAt(i) == '}') {
                elevation--;
            }

            if (!firstOpening) {
                if (elevation == 0) {
                    return globalString.substring(beginIndex, i + 1);
                }
            }
        }
        return globalString;
    }

    private void trimString(int start) {
        this.toParse = toParse.substring(start);
    }

    private class QueryDelimiter {

        public static final int MAX_VALUE = 1010100202;
        private int nextCloseCurlyIndex;
        private int nextCurlyIndex;
        private int nextCloseBraceIndex;
        private int nextBraceIndex;
        private int nextCommaIndex;
        private int endCarret;

        public QueryDelimiter() {
        }

        public void analyze(String toAnalyze) {
            nextCommaIndex = toAnalyze.indexOf(",");
            nextBraceIndex = toAnalyze.indexOf("(");
            nextCloseBraceIndex = toAnalyze.indexOf(")");
            nextCurlyIndex = toAnalyze.indexOf("{");
            nextCloseCurlyIndex = toAnalyze.indexOf("}");

            nextCommaIndex = ifNegativeMakeGreat(nextCommaIndex);
            nextBraceIndex = ifNegativeMakeGreat(nextBraceIndex);
            nextCurlyIndex = ifNegativeMakeGreat(nextCurlyIndex);
            nextCloseBraceIndex = ifNegativeMakeGreat(nextCloseBraceIndex);
            nextCloseCurlyIndex = ifNegativeMakeGreat(nextCloseCurlyIndex);
        }


        private int ifNegativeMakeGreat(int toCheck) {
            if (toCheck < 0) {
                return MAX_VALUE;
            }
            return toCheck;
        }

        public boolean isNextCloseCurly() {
            boolean b = nextCloseCurlyIndex == 0;
            if(b) {
                endCarret = 0;
            }
            return b;
        }

        public boolean isNextSimpleField() {
            boolean b = nextCommaIndex < nextBraceIndex && nextCommaIndex < nextCurlyIndex;
            if (b) {
                endCarret = nextCommaIndex;
            }
            return b;
        }

        public boolean isNextFieldWithParameters() {
            boolean b = nextBraceIndex < nextCommaIndex && nextBraceIndex < nextCurlyIndex;
            if (b) {
                endCarret = nextCloseBraceIndex;
            }
            return b;
        }

        public boolean isNextNodeWithoutParams() {
            boolean b = nextCurlyIndex < nextCommaIndex && nextCurlyIndex < nextBraceIndex;
            if (b) {
                endCarret = nextCurlyIndex;
            }
            return b;
        }

        public boolean isNextLastField() {
            boolean b = nextCloseCurlyIndex < nextCommaIndex && nextCloseCurlyIndex < nextCurlyIndex;
            if (b) {
                endCarret = nextCloseCurlyIndex;
            }
            return b;
        }
    }
}
