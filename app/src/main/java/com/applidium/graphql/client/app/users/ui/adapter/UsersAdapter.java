package com.applidium.graphql.client.app.users.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.applidium.graphql.client.app.users.model.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private final List<UserViewModel> dataSet = new ArrayList<>();
    private final UserClickedListener listener;

    public UsersAdapter(UserClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UsersViewHolder.makerHolder(parent);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        holder.bind(dataSet.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface UserClickedListener {
        void onUserClicked(UserViewModel user);
    }

    public void setContentViewModels(List<UserViewModel> viewModels) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(getResultCallback(viewModels), false);
        dataSet.clear();
        dataSet.addAll(viewModels);
        result.dispatchUpdatesTo(this);
    }

    private DiffUtil.Callback getResultCallback(final List<UserViewModel> viewModels) {
        return new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return dataSet.size();
            }

            @Override
            public int getNewListSize() {
                return viewModels.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return dataSet.get(oldItemPosition).equals(viewModels.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return dataSet.get(oldItemPosition).equals(viewModels.get(newItemPosition));
            }
        };
    }
}
