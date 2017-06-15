package com.applidium.graphql.client.app.user.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.user.model.PostViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.body) TextView body;
    @BindView(R.id.date) TextView date;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.comments) Button comments;
    @BindView(R.id.votes) Button votes;

    private PostViewModel postViewModel;
    private PostAdapter.PostClickedListener listener;

    public static PostViewHolder makerHolder(ViewGroup parent) {
        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.partial_post, parent, false);

        return new PostViewHolder(view);
    }

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(PostViewModel postViewModel, PostAdapter.PostClickedListener listener) {
        this.postViewModel = postViewModel;
        this.listener = listener;
        title.setText(postViewModel.title());
        body.setText(postViewModel.body());
        comments.setText(itemView.getContext().getString(R.string.number_comments, String.valueOf(postViewModel.numberOfComments())));
        votes.setText(itemView.getContext().getString(R.string.number_votes, String.valueOf(postViewModel.votesCount())));
        author.setText(postViewModel.author());
        date.setText(postViewModel.creationDate());

        comments.setOnClickListener(getOnCommentClickedListener());
        votes.setOnClickListener(getOnVotesClickedListener());
    }

    private View.OnClickListener getOnVotesClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVotesClicked(postViewModel);
                }
            }
        };
    }

    private View.OnClickListener getOnCommentClickedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCommentClicked(postViewModel);
                }
            }
        };
    }
}
