package com.applidium.graphql.client.app.selection.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.selection.model.FieldViewModel;
import com.applidium.graphqlient.model.QLModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FieldViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.check_field) CheckBox checkBox;
    @BindView(R.id.field_type) TextView type;
    @BindView(R.id.field_name) TextView name;

    private FieldViewModel model;
    private FieldAdapter.FieldCheckedListener listener;

    public static FieldViewHolder makeHolder(ViewGroup parent) {
        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.partial_field, parent, false);

        return new FieldViewHolder(view);
    }

    public FieldViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(FieldViewModel model, FieldAdapter.FieldCheckedListener listener) {
        this.model = model;
        this.listener = listener;
        name.setText(trimPath(model.fieldName()));
        checkBox.setChecked(model.isChecked());

        if (model.type() instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) model.type();
            Type geneType = paramType.getActualTypeArguments()[0];
            if (QLModel.class.isAssignableFrom((Class<?>)geneType)) {
                type.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.greeny_blue));
            } else {
                type.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.mango));
            }
            String result = trimPath(paramType.getRawType().toString());
            result += "<";
            result += trimPath(geneType.toString());
            result += ">";
            type.setText(result);
        } else {
            if (QLModel.class.isAssignableFrom((Class<?>)model.type())) {
                type.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.greeny_blue));
                type.setText(trimPath(model.type().toString()));
            } else {
                type.setText(trimPath(model.type().toString()));
                type.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.mango));
            }
        }

        checkBox.setOnCheckedChangeListener(getOnClickListener());
    }

    private CompoundButton.OnCheckedChangeListener getOnClickListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onFieldChecked(model, isChecked);
                }
            }
        };
    }

    public static String trimPath(String s) {
        int lastIndex = s.lastIndexOf(".");
        return lastIndex == -1 ? s : s.substring(lastIndex + 1);
    }

    public void setOnclickListener(FieldAdapter.FieldCheckedListener li) {
        listener = li;
    }
}
