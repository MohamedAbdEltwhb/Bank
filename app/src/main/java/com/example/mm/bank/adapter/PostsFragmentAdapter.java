package com.example.mm.bank.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.bank.R;
import com.example.mm.bank.data.model.posts.PostsDatum;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PostsFragmentAdapter extends RecyclerView.Adapter<PostsFragmentAdapter.PostsFragmentHolder> {

    //private boolean likeImage = false;
    private List<PostsDatum> data;
    private Context mContext;

    private OnPostClickListener mOnPostClickListener;

    public PostsFragmentAdapter(List<PostsDatum> data, Context mContext, OnPostClickListener onPostClickListener) {
        this.data = data;
        this.mContext = mContext;
        this.mOnPostClickListener = onPostClickListener;
    }

    @NonNull
    @Override
    public PostsFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsFragmentHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_posts_layout, parent, false), mOnPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsFragmentHolder holder, int position) {

        PostsDatum postsDatum = data.get(position);

        holder.PostsItemTVTitle.setText(postsDatum.getTitle());

        Glide.with(mContext)
                .load(postsDatum.getThumbnailFullPath())
                .into(holder.PostItemImageView);

        holder.itemPostsLikeButton.setImageResource(R.drawable.ic_like_a);

        holder.itemPostsLikeButton.setOnClickListener(new View.OnClickListener() {
            ValueAnimator buttonColorAnim = null; // to hold the button animator
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if(buttonColorAnim != null){
                    buttonColorAnim.reverse();
                    buttonColorAnim = null;
                }
                else {
                    final ImageView button = (ImageView) v;
                    buttonColorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), R.drawable.ic_like_a, R.drawable.ic_lick_clicked);
                    buttonColorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            button.setImageResource((Integer) animator.getAnimatedValue());
                        }
                    });
                    buttonColorAnim.start();
                }
            }
        });









//        holder.frameLayoutImageViewlike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!likeImage) {
//                    holder.itemPostsLikeButton.setImageResource(R.drawable.ic_lick_clicked);
//                    likeImage = true;
//
//                } else {
//                    holder.itemPostsLikeButton.setImageResource(R.drawable.ic_like_a);
//                    likeImage = false;
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PostsFragmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        FrameLayout frameLayoutImageViewlike;
        private ImageView itemPostsLikeButton;
        private TextView PostsItemTVTitle;
        private ImageView PostItemImageView;

        private OnPostClickListener onPostClickListener;

        public PostsFragmentHolder(View itemView, OnPostClickListener onPostClickListener) {
            super(itemView);
            frameLayoutImageViewlike = itemView.findViewById(R.id.frameLayout_ImageView_like);
            itemPostsLikeButton = itemView.findViewById(R.id.item_posts_Like_button);
            PostsItemTVTitle = itemView.findViewById(R.id.Posts_item_TV_title);
            PostItemImageView = itemView.findViewById(R.id.Post_item_ImageView);

            this.onPostClickListener = onPostClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPostClickListener.setOnPostClickListener(getAdapterPosition());
        }
    }
}
