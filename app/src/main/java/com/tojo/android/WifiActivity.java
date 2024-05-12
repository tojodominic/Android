package com.tojo.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.stealthcopter.networktools.SubnetDevices;
import com.stealthcopter.networktools.subnet.Device;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class WifiActivity extends AppCompatActivity {

    TextView txtStatus,txtOp;
    Button btnScan;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<String> ipList;

    private TextView ipAddressesTextView;
    private List<String> ipLists;
    private int threadsCompleted; // Counter to track completed Threads
    private int totalThreads; // Total number of Threads to execute

    String ipAddr;

    Handler ObjHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        txtStatus = (TextView) findViewById(R.id.statusWifiActivity);
        txtOp = (TextView) findViewById(R.id.opWifiActivity);
        btnScan = (Button) findViewById(R.id.btnScanWifiWifiActivity);

        recyclerView=findViewById(R.id.recyclerviewWifi);

        ipList = new ArrayList<>();

//        if (!Python.isStarted()){
//            Python.start(new AndroidPlatform(this));
//        }
//        Python py = Python.getInstance();


        ipLists = new ArrayList<>();
        threadsCompleted = 0;
        totalThreads = 255; // Total number of Threads to execute

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().startsWith("192.")) {
                        String ipAddress = inetAddress.getHostAddress(); // Get IP address without formatting
                        ipAddr = removeLastCharacter(ipAddress);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),ipAddr,Toast.LENGTH_LONG).show();

                final long startTimeMillis = System.currentTimeMillis();
                txtStatus.setText("Scanning...");

                for (int i = 1; i <= 255; i++) {
                    final String host = ipAddr + i;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InetAddress inetAddress = InetAddress.getByName(host);
                                if (inetAddress.isReachable(3000)) {
                                    String ipAddress = inetAddress.getHostAddress();
                                    synchronized (ipLists) {
                                        ipLists.add(ipAddress); // Add the IP address to the list
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                // Increment the counter when a Thread completes its execution
                                synchronized (WifiActivity.this) {
                                    threadsCompleted++;
                                    if (threadsCompleted == totalThreads) {
                                        // All Threads have completed, update the UI
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), ipLists.toString(), Toast.LENGTH_LONG).show();

                                                List<WifiData> wifiAdapterList = new ArrayList<>();
                                                for(int i=0;i< ipLists.size();i++){
                                                    String IP = ipLists.get(i).toString();
                                                    WifiData wifiData = new WifiData();
                                                    wifiData.setIp(IP);
                                                    wifiAdapterList.add(wifiData);
                                                }



                                                //setting recycle view...
                                                recyclerView.setHasFixedSize(true);
                                                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));

                                                adapter=new CustomAdapterWifi(wifiAdapterList,getApplicationContext());
                                                recyclerView.setAdapter(adapter);

                                                txtStatus.setText("Scan Completed...");
                                            }
                                        });

                                    }
                                }
                            }
                        }
                    }).start();
                }
            }
        });


    }




    public static String removeLastCharacter(String ipAddress) {
        // Split the IP address string by dot (.)
        String[] parts = ipAddress.split("\\.");

        // Check if the IP address has at least one part
        if (parts.length > 0) {
            // Remove the last part
            parts[parts.length - 1] = "";

            // Join the parts back into an IP address string
            return String.join(".", parts);
        }

        return ipAddress;
    }
}