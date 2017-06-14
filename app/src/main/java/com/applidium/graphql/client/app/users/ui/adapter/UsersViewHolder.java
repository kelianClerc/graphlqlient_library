package com.applidium.graphql.client.app.users.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applidium.graphql.client.R;
import com.applidium.graphql.client.app.users.model.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.number_comments) TextView comments;
    @BindView(R.id.item) ViewGroup viewGroup;

    private UserViewModel userViewModel;
    private UsersAdapter.UserClickedListener listener;

    public static UsersViewHolder makerHolder(ViewGroup parent) {
        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.partial_user, parent, false);

        return new UsersViewHolder(view);
    }

    public UsersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(UserViewModel userViewModel, UsersAdapter.UserClickedListener listener) {
        this.userViewModel = userViewModel;
        this.listener = listener;
        name.setText(userViewModel.name());
        email.setText(userViewModel.email());
        comments.setText(itemView
            .getContext()
            .getString(R.string.number_comments, String.valueOf(userViewModel.numberOfComments()))
        );
        viewGroup.setOnClickListener(getClickListener(userViewModel));
    }

    private View.OnClickListener getClickListener(final UserViewModel userViewModel) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUserClicked(userViewModel);
                }
            }
        };
    }
}
