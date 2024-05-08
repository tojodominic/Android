package com.tojo.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterPortList extends RecyclerView.Adapter<CustomAdapterPortList.MyViewHolderPortList> {
    Context context;
    List<WifiPort> wifiPortList;
    public CustomAdapterPortList(List<WifiPort> wifiPortList, Context applicationContext) {
        this.context = applicationContext;
        this.wifiPortList = wifiPortList;
    }

    @NonNull
    @Override
    public CustomAdapterPortList.MyViewHolderPortList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_portwifi,parent,false);
        return new MyViewHolderPortList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterPortList.MyViewHolderPortList holder, int position) {
        final WifiPort wifiPort = wifiPortList.get(position);

        holder.txtBanner.setText(wifiPort.banner);
        holder.txtPort.setText(wifiPort.portNo);
        holder.txtService.setText(wifiPort.service);

    }

    @Override
    public int getItemCount() {
        return wifiPortList.size();
    }

    public class MyViewHolderPortList extends RecyclerView.ViewHolder {
        TextView txtPort,txtBanner,txtService;
        public MyViewHolderPortList(@NonNull View itemView) {
            super(itemView);
            txtBanner = itemView.findViewById(R.id.bannerItemPortWifi);
            txtPort = itemView.findViewById(R.id.portNoItemPortWifi);
            txtService = itemView.findViewById(R.id.serviceItemPortWifi);
        }
    }
}
