package com.applidium.graphql.client.app.selection.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.applidium.graphql.client.app.selection.model.FieldViewModel;

import java.util.ArrayList;
import java.util.List;

public class FieldAdapter extends RecyclerView.Adapter<FieldViewHolder> {

    private final List<FieldViewModel> dataSet = new ArrayList<>();
    private final FieldCheckedListener listener;

    public FieldAdapter(FieldCheckedListener listener) {
        this.listener = listener;
    }

    @Override
    public FieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FieldViewHolder.makeHolder(parent);
    }

    @Override
    public void onBindViewHolder(FieldViewHolder holder, int position) {
        holder.bind(dataSet.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setContentViewModels(List<FieldViewModel> fields) {
        dataSet.clear();
        dataSet.addAll(fields);
        notifyDataSetChanged();
    }

    public interface FieldCheckedListener {
        void onFieldChecked(FieldViewModel model, boolean isChecked);
    }

    @Override
    public void onViewDetachedFromWindow(FieldViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.setOnclickListener(null);
    }
}
