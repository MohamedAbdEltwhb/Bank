package com.example.mm.bank.ui.fragments.homeCycle.posts;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.post.FavouriteAdapter;
import com.example.mm.bank.adapter.post.OnPostClickListener;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.posts.post_favourites_list.FavouritesDatum;
import com.example.mm.bank.data.model.posts.post_favourites_list.PostFavouriteList;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    /* Interface to Communication Between {PostsFragment & PostsDetailsFragment} */
    private OnItemPostDetailsSend mOnItemPostDetailsSend;

    @BindView(R.id.FavoritePostRecyclerView)
    RecyclerView FavoritePostRecyclerView;
    Unbinder unbinder;

    private List<FavouritesDatum> mFavouritesPostsList;
    private FavouriteAdapter mFavouritesPostsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Get a Favourite Posts List Using API Call */
        getApiFavouritesList(SharedPrefManager.getInstance(getContext()).getApiToken());

        return view;
    }

    /**
     * Get a Favourite Posts List Using API Call
     *
     * @param apiToken
     */
    private void getApiFavouritesList(String apiToken) {
        Call<PostFavouriteList> favouritesListCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getFavouritesList(apiToken);
        favouritesListCall.enqueue(new Callback<PostFavouriteList>() {
            @Override
            public void onResponse(Call<PostFavouriteList> call, Response<PostFavouriteList> response) {
                mFavouritesPostsList = response.body().getData().getData();
                FavoritePostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mFavouritesPostsAdapter = new FavouriteAdapter(mFavouritesPostsList, getContext(), new OnPostClickListener() {

                    /* RecyclerView Item Click Listener Using OnPostClickListener Interface */
                    @Override
                    public void setOnPostClickListener(int position) {

                        /* Send Post ID From PostsFragment to PostsDetailsFragment Use OnItemPostDetailsSend Interface */
                        mOnItemPostDetailsSend.onSentItemDetails(mFavouritesPostsList.get(position).getId());
                    }
                });
                mFavouritesPostsAdapter.notifyDataSetChanged();
                FavoritePostRecyclerView.setAdapter(mFavouritesPostsAdapter);

            }

            @Override
            public void onFailure(Call<PostFavouriteList> call, Throwable t) {

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
}
