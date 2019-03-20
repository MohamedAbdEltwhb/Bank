package com.example.mm.bank.ui.fragments.homeCycle.posts;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.post.OnPostClickListener;
import com.example.mm.bank.adapter.post.PostsFragmentAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.posts.post.Posts;
import com.example.mm.bank.data.model.posts.post.PostsDatum;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.helper.utils.OnEndless;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.custom.CustomDialog;
import com.example.mm.bank.ui.fragments.homeCycle.home.NewRequestFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.Posts_RecyclerView)
    RecyclerView PostsRecyclerView;
    @BindView(R.id.Posts_fragment_ProgressBar)
    ProgressBar PostsFragmentProgressBar;

    /* member variable for the OnEndless */
    private OnEndless scrollListener;

    /* Interface to Communication Between {PostsFragment & PostsDetailsFragment} */
    private OnItemPostDetailsSend mOnItemPostDetailsSend;

    private List<PostsDatum> postsList;
    private PostsFragmentAdapter postsAdapter;

    //var
    private int maxPage = 0;

    private ProgressDialog mProgressDialog;

    /**
     * Configure Back Pressed Listener Button
     */
    public void onBackPressedListener() {
        ((HomeCycleActivity) Objects.requireNonNull(getActivity()))
                .setOnBackPressedListener(new BackPressedListener(getActivity()) {
                    @Override
                    public void doBack() {
                        getActivity().moveTaskToBack(true);
                    }
                });
    }

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBackPressedListener();

        mProgressDialog = new ProgressDialog(getContext(), R.style.MyProgressDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);


        postsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        PostsRecyclerView.setLayoutManager(linearLayoutManager);
        PostsRecyclerView.setHasFixedSize(true);
        PostsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        /* Create Adapter */
        postsAdapter = new PostsFragmentAdapter(postsList, getContext(), new OnPostClickListener() {

            /* RecyclerView Item Click Listener Using OnPostClickListener Interface */
            @Override
            public void setOnPostClickListener(int position) {

                /* Send Post ID From PostsFragment to PostsDetailsFragment Use OnItemPostDetailsSend Interface */
                mOnItemPostDetailsSend.onSentItemDetails(postsList.get(position).getId());
            }
        });

        scrollListener = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page < maxPage) {
                    if (maxPage != 0) {

                        /* Set ProgressBar Visible */
                        PostsFragmentProgressBar.setVisibility(View.VISIBLE);

                        /* Get All Posts Using API Call */
                        getApiPostsCall(SharedPrefManager.getInstance(getContext()).getApiToken(), current_page);
                    }
                }
            }
        };
        /* Adds the scroll listener to RecyclerView */
        PostsRecyclerView.addOnScrollListener(scrollListener);

        /* Set Adapter */
        PostsRecyclerView.setAdapter(postsAdapter);

        /* Check Network Connection Status */
        if(HelperMethod.checkConnection(Objects.requireNonNull(getActivity()))){

            /* Get All Posts Using API Call */
            getApiPostsCall(SharedPrefManager.getInstance(getContext()).getApiToken(), 1);

        }else {
            /* Show Dialog */
            new CustomDialog(getContext()).showCustomDialog();
        }

        return view;
    }

    /**
     * Get All Posts Using API Call
     *
     * @param apiToken
     * @param page
     */
    private void getApiPostsCall(String apiToken, int page) {
        Call<Posts> postsCall = RetrofitClient.getInstance().getApiServices().getAllPosts(apiToken, page);

        postsCall.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        // to Finish the ProgressDialog
                        mProgressDialog.dismiss();

                        maxPage = response.body().getData().getLastPage();
                        postsList.addAll(response.body().getData().getData());

                        /* Set ProgressBar In Visible */
                        PostsFragmentProgressBar.setVisibility(View.INVISIBLE);
                        postsAdapter.notifyDataSetChanged();
                    }else {
                        // to Finish the ProgressDialog
                        mProgressDialog.dismiss();

                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });
    }

    private void getApiPostsFilter() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        mOnItemPostDetailsSend = (OnItemPostDetailsSend) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Order_Fragment_FAB)
    public void onViewClicked() {
        HelperMethod.replaceFragments(
                new NewRequestFragment(),
                Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                R.id.Home_Cycle_FL_Fragment_Container,
                null,
                null);
    }


}
