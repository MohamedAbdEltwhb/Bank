package com.example.mm.bank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mm.bank.R;
import com.example.mm.bank.data.model.notifications.notifications_list.NotificationDatum;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationListHolder>{

    private Context mContext;
    private List<NotificationDatum> datumNotificationList;

    public NotificationListAdapter(Context mContext, List<NotificationDatum> datumNotificationList) {
        this.mContext = mContext;
        this.datumNotificationList = datumNotificationList;
    }

    @NonNull
    @Override
    public NotificationListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationListHolder(
                LayoutInflater.from(mContext)
                        .inflate(R.layout.item_notification_layout,
                                parent,
                                false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListHolder holder, int position) {
        NotificationDatum notificationDatum = datumNotificationList.get(position);
        holder.NotificationTitle.setText(notificationDatum.getTitle());

    }

    @Override
    public int getItemCount() {
        return datumNotificationList.size();
    }

    class NotificationListHolder extends RecyclerView.ViewHolder{
        private TextView NotificationTitle;

        public NotificationListHolder(View itemView) {
            super(itemView);
            NotificationTitle = itemView.findViewById(R.id.NotificationTitle);
        }
    }
}
