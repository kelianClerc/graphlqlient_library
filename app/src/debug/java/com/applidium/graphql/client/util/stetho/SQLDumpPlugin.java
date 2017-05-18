package com.applidium.graphql.client.util.stetho;

import android.content.Context;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class SQLDumpPlugin implements DumperPlugin {

    private static final String FAILURE_MESSAGE = "Fail while writing to output";

    private final Context context;

    public SQLDumpPlugin(Context context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "sql_dump";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        List<String> args = dumpContext.getArgsAsList();
        if (args.isEmpty()) {
            printHelp(dumpContext);
            return;
        }
        switch (args.get(0)) {
            case "-h":
                printHelp(dumpContext);
                break;
            case "-l":
                listDatabase(dumpContext);
                break;
            default:
                handleOther(dumpContext, args);
                break;
        }
    }

    private void handleOther(DumperContext dumpContext, List<String> args) throws DumpException {
        if (args.get(0).startsWith("-")) {
            printHelp(dumpContext);
        } else {
            readDatabase(dumpContext, args.get(0));
        }
    }

    private void printHelp(DumperContext dumpContext) throws DumpException {
        PrintStream out = dumpContext.getStdout();
        try {
            out.write(("./script/dumpapp " + getName() + " [options] [dbname]\n" +
                "\n" +
                "List of options: \n" +
                "\t-h this help\n" +
                "\t-l list of existing database\n").getBytes(Charset.defaultCharset()));
            out.flush();
        } catch (IOException e) {
            throw new DumpException(FAILURE_MESSAGE);
        }
    }

    private void listDatabase(DumperContext dumpContext) throws DumpException {
        PrintStream out = dumpContext.getStdout();
        try {
            for (String db : context.databaseList()) {
                out.write((db + "\n").getBytes(Charset.defaultCharset()));
            }
            out.flush();
        } catch (IOException e) {
            throw new DumpException(FAILURE_MESSAGE);
        }
    }

    private void readDatabase(DumperContext dumpContext, String name) throws DumpException {
        if (Arrays.asList(context.databaseList()).contains(name)) {
            tryDumping(dumpContext, name);
        } else {
            tryDumpingError(dumpContext, name);
        }
    }

    private void tryDumpingError(DumperContext dumpContext, String name) throws DumpException {
        PrintStream err = dumpContext.getStderr();
        try {
            byte[] text = (name + " is not an existing database\n")
                .getBytes(Charset.defaultCharset());
            err.write(text);
            err.flush();
        } catch (IOException e) {
            throw new DumpException(FAILURE_MESSAGE);
        }
    }

    @SuppressWarnings("squid:S2095") // False positive
    private void tryDumping(DumperContext dumpContext, String name) throws DumpException {
        PrintStream out = dumpContext.getStdout();
        try {
            InputStream in = new FileInputStream(context.getDatabasePath(name));
            while (true) {
                int i = in.read();
                if (i == -1) {
                    break;
                }
                out.write(i);
            }
            out.write('\n');
            out.flush();
            in.close();
        } catch (FileNotFoundException e) {
            throw new DumpException("no db found");
        } catch (IOException e) {
            throw new DumpException("fail while reading the db");
        }
    }
}
