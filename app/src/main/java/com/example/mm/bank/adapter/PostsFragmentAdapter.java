package com.example.mm.bank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mm.bank.R;

import butterknife.BindView;

public class PostsFragmentAdapter extends RecyclerView.Adapter<PostsFragmentAdapter.PostsFragmentHolder> {

    private boolean likeImage = false;

    private Context mContext;

    @NonNull
    @Override
    public PostsFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsFragmentHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_posts_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsFragmentHolder holder, int position) {

        holder.itemPostsLikeButton.setImageResource(R.drawable.ic_like_a);
        holder.itemPostsLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeImage){
                    holder.itemPostsLikeButton.setImageResource(R.drawable.ic_lick_clicked);
                    likeImage = false;
                }else {
                    holder.itemPostsLikeButton.setImageResource(R.drawable.ic_like_a);
                    likeImage = true;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PostsFragmentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_posts_Like_button)
        ImageView itemPostsLikeButton;
        @BindView(R.id.Posts_item_TV_title)
        TextView PostsItemTVTitle;
        @BindView(R.id.Post_item_ImageView)
        ImageView PostItemImageView;

        public PostsFragmentHolder(View itemView) {
            super(itemView);
        }
    }
}
