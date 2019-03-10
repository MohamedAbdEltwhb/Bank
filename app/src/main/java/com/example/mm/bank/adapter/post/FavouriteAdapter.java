package com.example.mm.bank.adapter.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.bank.R;
import com.example.mm.bank.data.model.posts.post_favourites_list.FavouritesDatum;
import com.example.mm.bank.ui.custom.LikeViewCheckBox;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouritePostsHolder>{

    private List<FavouritesDatum> datumList;
    private Context mContext;

    private OnPostClickListener mOnPostClickListener;

    public FavouriteAdapter(List<FavouritesDatum> data, Context mContext, OnPostClickListener mOnPostClickListener) {
        this.datumList = data;
        this.mContext = mContext;
        this.mOnPostClickListener = mOnPostClickListener;
    }

    @NonNull
    @Override
    public FavouritePostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouritePostsHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_posts_layout, parent, false), mOnPostClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritePostsHolder holder, int position) {
        FavouritesDatum favouritesDatum = datumList.get(position);

        holder.checkBoxLike.setChecked(favouritesDatum.getIsFavourite());
        holder.PostsItemTVTitle.setText(favouritesDatum.getTitle());
        Glide.with(mContext)
                .load(favouritesDatum.getThumbnailFullPath())
                .into(holder.PostItemImageView);

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    class FavouritePostsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LikeViewCheckBox checkBoxLike;
        private TextView PostsItemTVTitle;
        private ImageView PostItemImageView;

        private OnPostClickListener onPostClickListener;

        public FavouritePostsHolder(View itemView, OnPostClickListener onPostClickListener) {
            super(itemView);
            checkBoxLike = itemView.findViewById(R.id.item_posts_Like_button);
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
