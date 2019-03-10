package com.example.mm.bank.ui.fragments.homeCycle.posts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.posts.posts_details.PostsDetails;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.custom.LikeViewCheckBox;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;
import com.example.mm.bank.ui.fragments.homeCycle.home.NewRequestFragment;

import java.util.Objects;

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

    Unbinder unbinder;
    @BindView(R.id.Posts_Details_ImageView)
    ImageView PostsDetailsImageView;
    @BindView(R.id.Posts_Details_TV_Title)
    TextView PostsDetailsTVTitle;

    @BindView(R.id.Details_Posts_textView)
    TextView DetailsPostsTextView;
    @BindView(R.id.item_posts_Details_Like_ChickBox)
    LikeViewCheckBox itemPostsDetailsLikeChickBox;

    private int postId;

    public PostsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Get Post ID Clicked on */
        Bundle bundle = getArguments();
        postId = bundle.getInt(POST_ID);

        /* Get Details Posts Using API Call */
        getApiPostsDetailsCall();
        return view;
    }

    /**
     * Get Details Posts Using API Call
     */
    private void getApiPostsDetailsCall() {

        Call<PostsDetails> postsDetailsCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getPostDetails(SharedPrefManager.getInstance(getContext()).getApiToken(), postId, 1);

        postsDetailsCall.enqueue(new Callback<PostsDetails>() {
            @Override
            public void onResponse(Call<PostsDetails> call, Response<PostsDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        PostsDetailsTVTitle.setText(response.body().getData().getTitle());
                        Glide.with(getContext())
                                .load(response.body().getData().getThumbnailFullPath())
                                .into(PostsDetailsImageView);
                        DetailsPostsTextView.setText(response.body().getData().getContent());

                        if (response.body().getData().getIsFavourite()) {
                            itemPostsDetailsLikeChickBox.setChecked(true);
                        } else {
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
