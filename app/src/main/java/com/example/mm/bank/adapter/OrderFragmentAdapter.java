package com.example.mm.bank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.bank.R;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderFragmentAdapter extends RecyclerView.Adapter<OrderFragmentAdapter.OrderFragmentHolder> {


    private Context mContext;

    @NonNull
    @Override
    public OrderFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderFragmentHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_order_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFragmentHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @OnClick({R.id.order_item_Details_Button, R.id.order_item_Call_Button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_item_Details_Button:
                break;
            case R.id.order_item_Call_Button:
                break;
        }
    }

    class OrderFragmentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.order_item_Blood_type_TV)
        TextView orderItemBloodTypeTV;
        @BindView(R.id.order_item_Status_name_TV)
        TextView orderItemStatusNameTV;
        @BindView(R.id.order_item_Hospital_TV)
        TextView orderItemHospitalTV;
        @BindView(R.id.order_item_City_TV)
        TextView orderItemCityTV;

        public OrderFragmentHolder(View itemView) {
            super(itemView);


        }
    }
}
