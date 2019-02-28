package com.example.mm.bank.ui.fragments.homeCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.favourites_list.FavouritesList;
import com.example.mm.bank.data.rest.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        getApiFavouritesList(SharedPrefManager.getInstance(getContext()).getApiToken());
        return view;
    }

    /**
     * Get a Favourite Posts List Using API Call
     *
     * @param apiToken
     * */
    private void getApiFavouritesList (String apiToken){
        Call<FavouritesList> favouritesListCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getFavouritesList(apiToken);
        favouritesListCall.enqueue(new Callback<FavouritesList>() {
            @Override
            public void onResponse(Call<FavouritesList> call, Response<FavouritesList> response) {

            }

            @Override
            public void onFailure(Call<FavouritesList> call, Throwable t) {

            }
        });
    }

}
