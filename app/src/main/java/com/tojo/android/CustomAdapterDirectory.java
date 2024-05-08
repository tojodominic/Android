package com.tojo.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapterDirectory extends RecyclerView.Adapter<CustomAdapterDirectory.MyViewHolder> {
    Context context;
    List<Directory> directoryList;
    public CustomAdapterDirectory(List<Directory> directoryList, Context applicationContext) {
        this.directoryList=directoryList;
        this.context=applicationContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_directory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterDirectory.MyViewHolder holder, int position) {
        final Directory directory = directoryList.get(position);
        holder.txtStatus.setText(directory.status);
        holder.txtPath.setText(directory.path);

        holder.txtUrl.setText(directory.urls.toString());
    }



    @Override
    public int getItemCount() {
        return directoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPath,txtStatus,txtUrl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUrl=itemView.findViewById(R.id.urlsItemDirectory);
            txtPath=itemView.findViewById(R.id.pathItemDirectory);
            txtStatus=itemView.findViewById(R.id.statusItemDirectory);
        }
    }
}
