package com.example.mm.bank.ui.fragments.homeCycle.order;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.order.OnOrderDetailsButtonClickListener;
import com.example.mm.bank.adapter.order.OrderFragmentAdapter;
import com.example.mm.bank.adapter.spinner.CustomSpinnerAdapter;
import com.example.mm.bank.adapter.spinner.SpinnerCitiesAdapter;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.blood_type.BloodDatum;
import com.example.mm.bank.data.model.blood_type.BloodType;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.cities.CitiesData;
import com.example.mm.bank.data.model.donation.donation_requests.Datum;
import com.example.mm.bank.data.model.donation.donation_requests.DonationRequests;
import com.example.mm.bank.data.model.donation.donation_requests_filter.DonationRequestsFilter;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.NewRequestFragment;

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
public class OrdersFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.order_customSpinner_Blood_type)
    Spinner orderCustomSpinnerBloodType;
    @BindView(R.id.order_customSpinner_Cities)
    Spinner orderCustomSpinnerCities;
    @BindView(R.id.Order_RecyclerView)
    RecyclerView OrderRecyclerView;

    private List<Datum> orderData;

    private String citiesID;
    private String blood_Type;

    private SendDonationDetails sendDonationDetails;

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


    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBackPressedListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* Set Blood Type to Spinner*/
        getSpinnerBloodTypes();

        /* Get Cities Using Api Call*/
        getSpinnerCities();

        /* Get All Donation Requests Using API Call */
        getDonationApiRequests(SharedPrefManager.getInstance(getContext()).getApiToken(), 1);


        return view;
    }

    private void chickCityIdAndBloodType(String citiesId, String bloodType) {
        getDonationApiRequestsFilter(
                SharedPrefManager.getInstance(getContext()).getApiToken(),
                bloodType,
                citiesId,
                1
        );


    }

    /**
     * Get Filter Donation Requests Using API Call
     *
     * @param apiToken
     * @param bloodType
     * @param cityId
     * @param page
     */
    private void getDonationApiRequestsFilter(String apiToken, String bloodType, String cityId, int page) {

        Toast.makeText(getContext(), bloodType, Toast.LENGTH_SHORT).show();
        Call<DonationRequestsFilter> donationRequestsFilterCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .donationRequestsFilterCall(apiToken, bloodType, cityId, page);

        donationRequestsFilterCall.enqueue(new Callback<DonationRequestsFilter>() {
            @Override
            public void onResponse(Call<DonationRequestsFilter> call, Response<DonationRequestsFilter> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        orderData = response.body().getData().getData();
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        OrderRecyclerView.setLayoutManager(mLayoutManager);

                        OrderFragmentAdapter adapter = new OrderFragmentAdapter(getContext(), orderData,
                                new OnOrderDetailsButtonClickListener() {
                                    @Override
                                    public void setOrderDetailsClick(int position) {
                                        sendDonationDetails.setDonationDetails(orderData.get(position).getId());
                                    }
                                });
                        OrderRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DonationRequestsFilter> call, Throwable t) {

            }
        });


    }

    /**
     * Get All Donation Requests Using API Call
     *
     * @param apiToken
     * @param page
     */
    private void getDonationApiRequests(String apiToken, int page) {
        Call<DonationRequests> donationRequestsCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .donationRequestsCall(apiToken, page);
        donationRequestsCall.enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse(Call<DonationRequests> call, final Response<DonationRequests> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {

                        orderData = response.body().getData().getData();
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        OrderRecyclerView.setLayoutManager(mLayoutManager);

                        OrderFragmentAdapter adapter = new OrderFragmentAdapter(getContext(), orderData,
                                new OnOrderDetailsButtonClickListener() {
                                    @Override
                                    public void setOrderDetailsClick(int position) {
                                        sendDonationDetails.setDonationDetails(orderData.get(position).getId());
                                    }
                                });
                        OrderRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DonationRequests> call, Throwable t) {

            }
        });

    }

    /**
     * Get Blood Types Using Api Call
     */
    private void getSpinnerBloodTypes() {

        Call<BloodType> call = RetrofitClient
                .getInstance()
                .getApiServices()
                .getBloodTypes();

        call.enqueue(new Callback<BloodType>() {
            @Override
            public void onResponse(@NonNull Call<BloodType> call, @NonNull final Response<BloodType> response) {

                if (response.isSuccessful()) {

                    if (response.body().getStatus() == 1) {
                        CustomSpinnerAdapter Adapter = new CustomSpinnerAdapter(getContext(), response.body().getData());
                        if (orderCustomSpinnerBloodType != null) {
                            orderCustomSpinnerBloodType.setAdapter(Adapter);
                            orderCustomSpinnerBloodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    BloodDatum bloodDatum = (BloodDatum) parent.getSelectedItem();
                                    blood_Type = bloodDatum.getId().toString();
                                    //Toast.makeText(getContext(), blood_Type, Toast.LENGTH_SHORT).show();

//                                    //mBloodTypeItemPosition = data.getId();
//                                    //mBloodTypeItemPosition = orderCustomSpinnerBloodType.getSelectedItemPosition();
//                                    CustomSpinnerItem item = (CustomSpinnerItem) parent.getSelectedItem();
//                                    blood_Type = item.getSpinnerItemName();

//                                    com.example.mm.bank.data.model.blood_type.BloodDatum item =
//                                            (com.example.mm.bank.data.model.blood_type.BloodDatum) parent.getSelectedItem();
//                                    blood_Type = item.getId().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BloodType> call, @NonNull Throwable t) {

            }
        });
    }

    /**
     * Get Cities Using Api Call
     */
    public void getSpinnerCities() {

        Call<Cities> citiesCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .getCities(new CitiesData().getGovernorateId());

        citiesCall.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(@NonNull Call<Cities> call, @NonNull final Response<Cities> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        SpinnerCitiesAdapter citiesAdapter = new SpinnerCitiesAdapter(getContext(), response.body().getData());
                        if (orderCustomSpinnerCities != null) {
                            orderCustomSpinnerCities.setAdapter(citiesAdapter);
                            orderCustomSpinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CitiesData data = (CitiesData) parent.getSelectedItem();
                                    citiesID = data.getId().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cities> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        sendDonationDetails = (SendDonationDetails) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.frameLayoutSearch, R.id.Posts_Fragment_FAB})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frameLayoutSearch:

                /* Get Filter Donation Requests Using API Call */
                chickCityIdAndBloodType(citiesID, blood_Type);
                break;

            case R.id.Posts_Fragment_FAB:
                HelperMethod.replaceFragments(
                        new NewRequestFragment(),
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                        R.id.Home_Cycle_FL_Fragment_Container,
                        null,
                        null);
                break;
        }
    }
}
