package com.example.mm.bank.adapter.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.posts.post.PostsDatum;
import com.example.mm.bank.data.model.posts.post_toggle_favourite.PostToggleFavourite;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.ui.custom.LikeViewCheckBox;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragmentAdapter extends RecyclerView.Adapter<PostsFragmentAdapter.PostsFragmentHolder> {

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
    public void onBindViewHolder(@NonNull final PostsFragmentHolder holder, final int position) {

        final PostsDatum postsDatum = data.get(position);

        holder.PostsItemTVTitle.setText(postsDatum.getTitle());

        Glide.with(mContext)
                .load(postsDatum.getThumbnailFullPath())
                .into(holder.PostItemImageView);

        holder.checkBoxLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PostToggleFavourite> postToggleFavouriteCall = RetrofitClient
                        .getInstance().getApiServices().postToggleFavourite(
                                postsDatum.getId(), SharedPrefManager.getInstance(mContext).getApiToken()
                );

                /** Check if the Post ChickBox is Checked Mack API Call Using {PostID & API Token} */
                if (holder.checkBoxLike.isChecked()){

                    postToggleFavouriteCall.enqueue(new Callback<PostToggleFavourite>() {
                        @Override
                        public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 1) {
                                    notifyDataSetChanged();
                                    Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                                }else Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostToggleFavourite> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class PostsFragmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LikeViewCheckBox checkBoxLike;
        private TextView PostsItemTVTitle;
        private ImageView PostItemImageView;

        private OnPostClickListener onPostClickListener;

        public PostsFragmentHolder(View itemView, OnPostClickListener onPostClickListener) {
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
