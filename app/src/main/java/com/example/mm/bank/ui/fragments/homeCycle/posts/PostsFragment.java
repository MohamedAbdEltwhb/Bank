package com.example.mm.bank.ui.fragments.homeCycle.posts;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.OnPostClickListener;
import com.example.mm.bank.adapter.PostsFragmentAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.posts.PostsDatum;
import com.example.mm.bank.data.model.posts.Posts;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.fragments.homeCycle.home.NewRequestFragment;

import java.util.List;

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

    private OnItemPostDetailsSend mOnItemPostDetailsSend;

    @BindView(R.id.Posts_RecyclerView)
    RecyclerView PostsRecyclerView;

    private List<PostsDatum> postsList;
    private PostsFragmentAdapter postsAdapter;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);

        getApiPostsCall();

        return view;
    }

    private void getApiPostsCall() {

        Call<Posts> postsCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getPosts(SharedPrefManager.getInstance(getContext()).getApiToken(), 1);

        postsCall.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        postsList = response.body().getPostsData().getData();
                        postsAdapter = new PostsFragmentAdapter(postsList, getContext(), new OnPostClickListener() {
                            @Override
                            public void setOnPostClickListener(int position) {
                                mOnItemPostDetailsSend.onSentItemDetails(postsList.get(position).getId());
                            }
                        });
                        PostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        PostsRecyclerView.setAdapter(postsAdapter);
                        postsAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        });
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
                getActivity().getSupportFragmentManager(),
                R.id.Home_Cycle_FL_Fragment_Container,
                null,
                null);
    }


}
