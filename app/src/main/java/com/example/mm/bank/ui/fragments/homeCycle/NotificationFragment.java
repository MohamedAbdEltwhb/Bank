package com.example.mm.bank.ui.fragments.homeCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.NotificationListAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.notifications.notifications_list.NotificationDatum;
import com.example.mm.bank.data.model.notifications.notifications_list.NotificationsList;
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
public class NotificationFragment extends Fragment {


    @BindView(R.id.NotificationFragmentRecyclerView)
    RecyclerView NotificationFragmentRecyclerView;
    Unbinder unbinder;

    private List<NotificationDatum> mDatumNotificationList;
    private NotificationListAdapter mNotificationListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Get Notification List Using API Call */
        getNotificationList(SharedPrefManager.getInstance(getContext()).getApiToken());

        return view;
    }

    /**
     * Get Notification List Using API Call
     *
     * @param apiToken
     */
    private void getNotificationList(String apiToken) {
        Call<NotificationsList> notificationsListCall = RetrofitClient
                .getInstance()
                .getApiServices().getNotificationsList(apiToken);

        notificationsListCall.enqueue(new Callback<NotificationsList>() {
            @Override
            public void onResponse(Call<NotificationsList> call, Response<NotificationsList> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        mDatumNotificationList = response.body().getData().getData();
                        NotificationFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        mNotificationListAdapter = new NotificationListAdapter(getContext(), mDatumNotificationList);
                        NotificationFragmentRecyclerView.setAdapter(mNotificationListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationsList> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
