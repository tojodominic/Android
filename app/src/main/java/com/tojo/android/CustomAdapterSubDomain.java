package com.tojo.android;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterSubDomain extends RecyclerView.Adapter<CustomAdapterSubDomain.MyViewHolderSubDomain> {
    Context context;
    List<SubDomain> subDomainList;
    public CustomAdapterSubDomain(List<SubDomain> subDomainList, Context applicationContext) {
        this.subDomainList=subDomainList;
        this.context=applicationContext;
    }

    @NonNull
    @Override
    public MyViewHolderSubDomain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subdomain,parent,false);
        return new MyViewHolderSubDomain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSubDomain holder, int position) {
        final SubDomain subDomain = subDomainList.get(position);
        //Toast.makeText(context.getApplicationContext(),subDomain.SubDomainName,Toast.LENGTH_LONG).show();
        holder.txtSubDomain.setText(subDomain.SubDomainName);
        holder.txtStatus.setText(subDomain.Status);
        holder.txtMethods.setText(subDomain.Methods);

        holder.btnPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent( view.getContext(),OpenPorts.class );
                in.putExtra("PORT", subDomain);
                view.getContext().startActivity( in );
            }
        });
        holder.btnDirecory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent( view.getContext(),DirectoryView.class );
                in.putExtra("DIR", subDomain);
                view.getContext().startActivity( in );
            }
        });
        holder.btnWhois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent( view.getContext(),WhoisView.class );
                in.putExtra("DIR", subDomain);
                view.getContext().startActivity( in );
            }
        });
        holder.btnDNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent( view.getContext(),DNSView.class );
                in.putExtra("DIR", subDomain);
                view.getContext().startActivity( in );
            }
        });
        holder.btnTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent( view.getContext(),TechView.class );
                in.putExtra("DIR", subDomain);
                view.getContext().startActivity( in );
            }
        });

    }

    @Override
    public int getItemCount() {
        return subDomainList.size();
    }

    public class MyViewHolderSubDomain extends RecyclerView.ViewHolder {
        TextView txtSubDomain,txtStatus,txtMethods;
        Button btnPort,btnDirecory,btnWhois,btnDNS,btnTech;
        public MyViewHolderSubDomain(@NonNull View itemView) {
            super(itemView);
            txtMethods = itemView.findViewById(R.id.methodsItemSubDomain);
            txtStatus = itemView.findViewById(R.id.statusItemSubDomain);
            txtSubDomain = itemView.findViewById(R.id.subdomainItemSubdomain);
            btnPort = itemView.findViewById(R.id.btnportsItemSubDomain);
            btnDirecory = itemView.findViewById(R.id.btndirectoriesItemSubDomain);
            btnDNS = itemView.findViewById(R.id.btndnsItemSubDomain);
            btnWhois = itemView.findViewById(R.id.btnwhoisItemSubDomain);
            btnTech = itemView.findViewById(R.id.btntechItemSubDomain);
        }
    }
}
