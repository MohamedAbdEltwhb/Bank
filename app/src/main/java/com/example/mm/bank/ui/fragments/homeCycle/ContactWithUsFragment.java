package com.example.mm.bank.ui.fragments.homeCycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mm.bank.R;
import com.example.mm.bank.data.local.SharedPrefManager;
import com.example.mm.bank.data.model.contact.ContactUs;
import com.example.mm.bank.data.rest.RetrofitClient;
import com.example.mm.bank.helper.BackPressedListener;
import com.example.mm.bank.helper.HelperMethod;
import com.example.mm.bank.ui.activities.HomeCycleActivity;
import com.example.mm.bank.ui.fragments.homeCycle.home.HomeFragment;

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
public class ContactWithUsFragment extends Fragment {


    @BindView(R.id.Contact_Fragment_TiL_Name)
    TextInputLayout ContactFragmentTiLName;
    @BindView(R.id.Contact_Fragment_TiL_Email)
    TextInputLayout ContactFragmentTiLEmail;
    @BindView(R.id.Contact_Fragment_TiL_Phone_Number)
    TextInputLayout ContactFragmentTiLPhoneNumber;
    @BindView(R.id.Contact_Fragment_TiL_Message_title)
    TextInputLayout ContactFragmentTiLMessageTitle;
    @BindView(R.id.Contact_Fragment_TiL_Message_Bode)
    TextInputLayout ContactFragmentTiLMessageBode;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Configure Back Pressed Listener Button */
        HelperMethod.onBackPressedListener(getContext(), getActivity());
    }

    public ContactWithUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_with_us, container, false);
        unbinder = ButterKnife.bind(this, view);

        ContactFragmentTiLName.getEditText().setText(SharedPrefManager.getInstance(getContext()).getUser().getName());
        ContactFragmentTiLEmail.getEditText().setText(SharedPrefManager.getInstance(getContext()).getUser().getEmail());
        ContactFragmentTiLPhoneNumber.getEditText().setText(SharedPrefManager.getInstance(getContext()).getUser().getPhone());
        return view;
    }

    /**
     * Extract Input Message Title and  Message Bode
     */
    private void extractInput() {
        String messageTitle = ContactFragmentTiLMessageTitle.getEditText().getText().toString();
        String messageBode = ContactFragmentTiLMessageBode.getEditText().getText().toString();

        if (TextUtils.isEmpty(messageTitle)) {
            ContactFragmentTiLMessageTitle.setError("Please Enter Your Message Title..");

        } else if (TextUtils.isEmpty(messageBode)) {
            ContactFragmentTiLMessageBode.setError("Please Enter Your Message..");

        } else {
            contactUs(SharedPrefManager.getInstance(getContext()).getApiToken(), messageTitle, messageBode);
        }
    }

    /**
     * Do Contact Us Using Api Call
     *
     * @param apiToken
     * @param messageTitle
     * @param messageBode
     */
    private void contactUs(String apiToken, String messageTitle, String messageBode) {
        Call<ContactUs> contactUsCall = RetrofitClient
                .getInstance()
                .getApiServices()
                .contactUs(apiToken, messageTitle, messageBode);

        contactUsCall.enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Contact_Button)
    public void onViewClicked() {
        extractInput();
    }
}
