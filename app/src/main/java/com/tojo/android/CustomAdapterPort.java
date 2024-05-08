package com.tojo.android;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterPort extends RecyclerView.Adapter<CustomAdapterPort.MyViewHolderPort> {
    Context context;
    List<Port> portList;
    public CustomAdapterPort(List<Port> portList, Context applicationContext) {
        this.context =applicationContext;
        this.portList=portList;

    }

    @NonNull
    @Override
    public MyViewHolderPort onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_port,parent,false);
        return new MyViewHolderPort(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderPort holder, int position) {
        final Port port = portList.get(position);
        holder.txtBanner.setText(port.banner);
        holder.txtPortNo.setText(port.portNo);
        holder.txtService.setText(port.service);
    }


    @Override
    public int getItemCount() {
        return portList.size();
    }

    public class MyViewHolderPort extends RecyclerView.ViewHolder {
        TextView txtPortNo,txtBanner,txtService;
        public MyViewHolderPort(@NonNull View itemView) {
            super(itemView);
            txtBanner=itemView.findViewById(R.id.bannerItemPort);
            txtPortNo=itemView.findViewById(R.id.portNoItemPort);
            txtService=itemView.findViewById(R.id.serviceItemPort);
        }
    }
}
