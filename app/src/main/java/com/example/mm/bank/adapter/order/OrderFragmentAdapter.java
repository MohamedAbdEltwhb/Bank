package com.example.mm.bank.adapter.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mm.bank.R;
import com.example.mm.bank.data.model.donation.donation_requests.Datum;

import java.util.List;


public class OrderFragmentAdapter extends RecyclerView.Adapter<OrderFragmentAdapter.OrderFragmentHolder> {


    private Context mContext;
    private List<Datum> orderData;
    private OnOrderDetailsButtonClickListener listener;

    public OrderFragmentAdapter(Context mContext, List<Datum> orderData, OnOrderDetailsButtonClickListener listener) {
        this.mContext = mContext;
        this.orderData = orderData;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderFragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderFragmentHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_order_layout, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderFragmentHolder holder, int position) {
        Datum order = orderData.get(position);

        holder.orderItemBloodTypeTV.setText(order.getBloodType());
        holder.orderItemStatusNameTV.setText(order.getPatientName());
        holder.orderItemHospitalTV.setText(order.getHospitalName());
        holder.orderItemCityTV.setText(order.getCityId());
        //order.getClientId();

    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }


    class OrderFragmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView orderItemBloodTypeTV;
        private TextView orderItemStatusNameTV;
        private TextView orderItemHospitalTV;
        private TextView orderItemCityTV;

        private Button orderDetailsButton;
        private Button orderCallButton;

        private OnOrderDetailsButtonClickListener buttonClickListener;

        public OrderFragmentHolder(View itemView, OnOrderDetailsButtonClickListener listener) {
            super(itemView);
            orderItemBloodTypeTV = itemView.findViewById(R.id.order_item_Blood_type_TV);
            orderItemStatusNameTV = itemView.findViewById(R.id.order_item_Status_name_TV);
            orderItemHospitalTV = itemView.findViewById(R.id.order_item_Hospital_TV);
            orderItemCityTV = itemView.findViewById(R.id.order_item_City_TV);

            orderDetailsButton = itemView.findViewById(R.id.order_item_Details_Button);
            orderCallButton = itemView.findViewById(R.id.order_item_Call_Button);

            this.buttonClickListener = listener;
            orderDetailsButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            buttonClickListener.setOrderDetailsClick(getAdapterPosition());

//            HelperMethod.replaceFragments(
//                    new NewRequestFragment(),
//                    ((FragmentActivity)mContext).getSupportFragmentManager(),
//                    R.id.Home_Cycle_FL_Fragment_Container,
//                    null,
//                    null);
        }
    }
}
