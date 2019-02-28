package com.example.mm.bank.ui.fragments.homeCycle.posts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.posts_details.PostsDetails;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.ui.custom.LikeViewCheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mm.bank.ui.fragments.homeCycle.posts.OnItemPostDetailsSend.POST_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsDetailsFragment extends Fragment {


    @BindView(R.id.Posts_Details_ImageView)
    ImageView PostsDetailsImageView;
    @BindView(R.id.Posts_Details_TV_Title)
    TextView PostsDetailsTVTitle;
    Unbinder unbinder;

    @BindView(R.id.Details_Posts_textView)
    TextView DetailsPostsTextView;
    @BindView(R.id.item_posts_Details_Like_ChickBox)
    LikeViewCheckBox itemPostsDetailsLikeChickBox;

    private int postId;

    public PostsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        postId = bundle.getInt(POST_ID);

        getApiPostsDetailsCall();
        return view;
    }

    /**
     * Get Details Posts Using API Call
     * */
    private void getApiPostsDetailsCall() {

        Call<PostsDetails> postsDetailsCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getPostDetails(SharedPrefManager.getInstance(getContext()).getApiToken(), postId);

        postsDetailsCall.enqueue(new Callback<PostsDetails>() {
            @Override
            public void onResponse(Call<PostsDetails> call, Response<PostsDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        PostsDetailsTVTitle.setText(response.body().getPostsDetailsData().getTitle());
                        Glide.with(getContext())
                                .load(response.body().getPostsDetailsData().getThumbnailFullPath())
                                .into(PostsDetailsImageView);
                        DetailsPostsTextView.setText(response.body().getPostsDetailsData().getContent());

                        if (response.body().getPostsDetailsData().getIsFavourite()){
                            itemPostsDetailsLikeChickBox.setChecked(true);
                        }else {
                            itemPostsDetailsLikeChickBox.setChecked(false);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<PostsDetails> call, Throwable t) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnClick(R.id.item_posts_Details_Like_ChickBox)
//    public void onViewClicked() {
//    }
}
