package com.tojo.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterWifi extends RecyclerView.Adapter<CustomAdapterWifi.MyViewHolderWifi> {
    Context context;
    List<WifiData> wifiDataList;
    public CustomAdapterWifi(List<WifiData> wifiAdapterList, Context applicationContext) {
        this.context=applicationContext;
        this.wifiDataList=wifiAdapterList;
    }

    @NonNull
    @Override
    public MyViewHolderWifi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi,parent,false);
        return new MyViewHolderWifi(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderWifi holder, int position) {
        final WifiData wifiData = wifiDataList.get(position);
        holder.txtIp.setText(wifiData.ip);

        ///
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context.getApplicationContext(), String.valueOf(domain.getSubDomainList().size()),Toast.LENGTH_LONG).show();

//                Intent in=new Intent( view.getContext(),ViewWifiPort.class );
//                //in.putExtra("domainName",domain.domainName);
//                //in.putExtra("fullName",domain.fullName);
//                //in.putExtra("Mode",domain.mode);
//                //in.putExtra("time",String.valueOf(domain.getSubDomainList().size()));
//                in.putExtra("ADC",wifiData);
//                view.getContext().startActivity( in );

            }
        });


    }


    @Override
    public int getItemCount() {
        return wifiDataList.size();
    }

    public class MyViewHolderWifi extends RecyclerView.ViewHolder{
        TextView txtIp;
        public MyViewHolderWifi(@NonNull View itemView) {
            super(itemView);
            txtIp = itemView.findViewById(R.id.ipItemWifi);
        }
    }
}
