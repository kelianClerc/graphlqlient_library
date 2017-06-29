package com.applidium.graphql.client.app.user.ui.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.applidium.graphql.client.app.user.model.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private final List<PostViewModel> dataSet = new ArrayList<>();
    private final PostClickedListener listener;

    public PostAdapter(PostClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PostViewHolder.makerHolder(parent);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(dataSet.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface PostClickedListener {
        void onCommentClicked(PostViewModel postViewModel);
        void onVotesClicked(PostViewModel postViewModel);
    }

    public void setContentViewModels(List<PostViewModel> viewModels) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(getResultCallback(viewModels), false);
        dataSet.clear();
        dataSet.addAll(viewModels);
        result.dispatchUpdatesTo(this);
    }

    private DiffUtil.Callback getResultCallback(final List<PostViewModel> viewModels) {
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
