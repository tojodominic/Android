package com.tojo.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HomeUI extends AppCompatActivity {


    Button btnSaved,btnNormal,btnStaged,btnWifi,btnLogout,btnPassword;
    EditText edTarget;
    TextView txt;
    ProgressBar mProgressbar;

    SharedPreferences.Editor editor;
    SharedPreferences preferences;

    DatabaseReference reference;

    Handler ObjHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            mProgressbar = (ProgressBar) findViewById(R.id.progressBarHomeUi);
            Bundle Obj2Bundle = msg.getData();
//            String myMsg2 = Obj2Bundle.getString("ADC");
//            if (myMsg2.equals("1")){
//                mProgressbar.setVisibility(View.VISIBLE);
//            }
            //String[] myMsg = Obj2Bundle.getStringArray("EFG");
            //Map<String,Map> myMsg = (Map<String, Map>) Obj2Bundle.get("EFG");
            String myMsg = Obj2Bundle.getString("EFG");
            txt = (TextView) findViewById(R.id.textview);
            //txt.setText(myMsg.toString());
            // mProgressbar.setVisibility(View.INVISIBLE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ui);


        edTarget = (EditText) findViewById(R.id.targetHomeUI);
        btnSaved = (Button) findViewById(R.id.saveHomeUI);
        btnNormal = (Button) findViewById(R.id.normalScanHomeUI);
        btnStaged = (Button) findViewById(R.id.stagedScanHomeUI);
        btnWifi = (Button) findViewById(R.id.wifiScanHomeUI);
        btnPassword = (Button) findViewById(R.id.OtherModuleHomeUI);
        btnLogout = (Button) findViewById(R.id.logoutHomeUI);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBarHomeUi);
        mProgressbar.setVisibility(View.INVISIBLE);


        //shared preference --> check it contain data
        //remove before deployment...
        preferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
        String uname=preferences.getString("emailid",null);
//        if(uname!=null){
//            Toast.makeText(getApplicationContext(),uname+"12345",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getApplicationContext(),"Null Data",Toast.LENGTH_LONG).show();
//        }

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PasswordStrength.class));
            }
        });

        btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SavedResults.class));
            }
        });

        btnStaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),StagedHome.class));
            }
        });
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),WifiActivity.class));
            }
        });

        if (!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("main");

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Scanning on normal", Toast.LENGTH_SHORT).show();
                //Change the Phone no with logged user cred,,,
                //String phone="9447574692";
                preferences=getSharedPreferences("LOGIN",MODE_PRIVATE);
                String phone=preferences.getString("mobile",null);
                String host = edTarget.getText().toString().trim();

                String ref = host.replace(".","-");
                //getting the current date and time...
                DateFormat df= new SimpleDateFormat("MMM-dd-yyyy HH-mm-ss");
                Date dateobj = new Date();
                String tim = df.format(dateobj);
                //String refName = ref+" - "+tim;
                String refName = tim;

                //Making the Initial reference for first entry to DB...
                reference= FirebaseDatabase.getInstance().getReference().child("Data").child("ScanResult").child(phone).child(refName);


                //making new thread...

                Runnable ObjRunnable = new Runnable() {
                    Message ObjMessage = ObjHandler.obtainMessage();
                    Bundle ObjBundle = new Bundle();

                    @Override
                    public void run() {
                        //mProgressbar.setVisibility(View.VISIBLE);

                        //String domain = "x";
                        //String subDomains = "x";
                        //String innerDomain = "x";
                        //String status = "x";
                        //String sdomain = "x";
                        //btnNormal.setEnabled(false);
                        //Maing the ProgressBar Visible and Button Invisible...
                        ObjHandler.post(new Runnable() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void run() {
                                mProgressbar.setVisibility(0);
                                btnNormal.setEnabled(false);
                            }
                        });

                        Domain domain = new Domain();

                        Map<PyObject, PyObject> obj = py.getModule("main").callAttr("main",host).asMap();
                        //getting the domain name... e.g: google.com
                        String domainName = obj.get("domain").toString();
                        domain.setDomainName(domainName);
                        domain.setTime(df.format(dateobj));
                        domain.setMode("Normal");
                        domain.setFullName(refName);

                        //.ObjBundle.putString("EFG", domainName);
                        //getting the subdomain detials...
                        List<PyObject> subDomainDetials = obj.get("subdomains").asList();

                        boolean xsize = subDomainDetials.isEmpty();
                        ObjBundle.putString("EFG", String.valueOf(xsize));
                        //a temp list for storing all suddomain objects...
                        List <SubDomain> dataSubDomainQue = new ArrayList<>();

                        List <Port> dataPortQUe;
                        List<Directory> dataDirQue;

                        if(!subDomainDetials.isEmpty()){
                            //subdomain list is not empty...
                            for(int i=0;i<subDomainDetials.size();i++){
                                SubDomain subDomain = new SubDomain();
                                //getting the inner Domain name...
                                Map<PyObject,PyObject> innerdomainDet = obj.get("subdomains").asList().get(i).asMap();
                                //getting the inner-domain name...
                                String subdomainName = innerdomainDet.get("domain").toString();
                                //getting the status...
                                String status = innerdomainDet.get("status").toString();
                                //getting the allowed methods...
                                String methods;
                                try{
                                    methods = innerdomainDet.get("methods").toString();
                                }catch (Exception e){
                                    methods = "No-Data";
                                }
                                //getting the whois...
                                String whois = innerdomainDet.get("whois").toString();
                                //getting the DNS...
                                //String dns = innerdomainDet.get("DNS").toString();
                                //getting the technology...
                                String tech = innerdomainDet.get("tech").toString();
                                //adding data to subdomain object...
                                subDomain.setSubDomainName(subdomainName);
                                subDomain.setStatus(status);
                                subDomain.setDNS("No-Data");

                                subDomain.setTechnology(tech);
                                subDomain.setMethods(methods);
                                //getting the portlist
                                //a temp list for storing all the directories...
                                dataDirQue = new ArrayList<>();
                                //there is a chance for the directory list is empty...
                                List<PyObject> dirDetials = innerdomainDet.get("directory").asList();
                                if(!dirDetials.isEmpty()){
                                    for(int k=0;k<dirDetials.size();k++){
                                        Directory directory = new Directory();
                                        //traveling through each directory list elements...
                                        Map<PyObject,PyObject> innerPortDet = innerdomainDet.get("directory").asList().get(k).asMap();
                                        //getting path...
                                        String path = innerPortDet.get("path").toString();
                                        //getting status...
                                        String statusPath = innerPortDet.get("status").toString();

                                        List<String> urlObj = new ArrayList<>();


                                        List<PyObject> urlPyObj = innerPortDet.get("urls").asList();

                                        if(!urlPyObj.isEmpty()){
                                            for(int m=0;m<urlPyObj.size();m++){
                                                urlObj.add(String.valueOf(urlPyObj.get(m)));
                                            }
                                        }else {
                                            urlObj.add("No-Data");
                                        }

                                        directory.setUrls(urlObj);

                                        directory.setPath(path);
                                        directory.setStatus(statusPath);


                                        dataDirQue.add(directory);

                                    }
                                }else{
                                    //port list is empty...
                                    //adding an templete with no-data...
                                    Directory directory = new Directory();
                                    directory.setStatus("No-Data");
                                    directory.setPath("No-Data");
                                    List<String> urlObj = new ArrayList<>();
                                    urlObj.add("No-Data");
                                    directory.setUrls(urlObj);
                                    //adding to SubDoamin class...
                                    dataDirQue.add(directory);
                                }

                                subDomain.setWhois(whois);
                                //a temp list for storing all the ports...
                                dataPortQUe = new ArrayList<>();
                                //getting the ports...
                                //there is chance for the ports list is empty...
                                List<PyObject> portDetials = innerdomainDet.get("ports").asList();
                                if(!portDetials.isEmpty()){
                                    //port list is not empty
                                    for(int j=0;j<portDetials.size();j++){
                                        Port port = new Port();
                                        //traveling through each port list elements...
                                        Map<PyObject,PyObject> innerPortDet = innerdomainDet.get("ports").asList().get(j).asMap();
                                        //getting port number...
                                        String portNo = innerPortDet.get("portNo").toString();
                                        //getting service...
                                        String service = innerPortDet.get("service").toString();
                                        //getting banner...
                                        String banner = innerPortDet.get("banner").toString();

                                        port.setPortNo(portNo);
                                        port.setBanner(banner);
                                        port.setService(service);
                                        //adding to SubDoamin class...
                                        dataPortQUe.add(port);
                                    }
                                }else{
                                    //port list is empty...
                                    //adding an templete with no-data...
                                    Port port = new Port();
                                    port.setPortNo("No-Data");
                                    port.setBanner("No-Data");
                                    port.setService("No-Data");
                                    //adding to SubDoamin class...
                                    dataPortQUe.add(port);
                                }
                                subDomain.setPortList(dataPortQUe);

                                subDomain.setDirectoryList(dataDirQue);

                                dataSubDomainQue.add(subDomain);
                            }
                        }else {
                            //Subdomain list is empty..
                            //adding an templete with no-data...
                            SubDomain subDomain = new SubDomain();
                            subDomain.setWhois("No-Data");
                            subDomain.setSubDomainName("No-Data");
                            subDomain.setMethods("No-Data");
                            subDomain.setDNS("No-Data");
                            subDomain.setTechnology("No-Data");
                            subDomain.setStatus("No-Data");


                            dataPortQUe = new ArrayList<>();

                            Port port = new Port();
                            port.setPortNo("No-Data");
                            port.setBanner("No-Data");
                            port.setService("No-Data");
                            //adding to SubDoamin class...
                            dataPortQUe.add(port);
                            dataDirQue = new ArrayList<>();

                            Directory directory = new Directory();
                            directory.setStatus("No-Data");
                            directory.setPath("No-Data");
                            List<String> urlObj = new ArrayList<>();
                            urlObj.add("No-Data");
                            directory.setUrls(urlObj);
                            //adding to SubDoamin class...
                            dataDirQue.add(directory);


                            subDomain.setPortList(dataPortQUe);
                            subDomain.setDirectoryList(dataDirQue);

                            dataSubDomainQue.add(subDomain);
                        }

                        domain.setSubDomainList(dataSubDomainQue);
                        reference.setValue(domain).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Scan Result Uploaded",Toast.LENGTH_LONG).show();
                            }
                        });


                        ObjMessage.setData(ObjBundle);

                        ObjHandler.sendMessage(ObjMessage);


                        //Maing the ProgressBar INVisible and Button Visible...
                        ObjHandler.post(new Runnable() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void run() {
                                btnNormal.setEnabled(true);
                                mProgressbar.setVisibility(4);
                            }
                        });
                    }
                };
                Thread ObjBgThread = new Thread(ObjRunnable);
                ObjBgThread.start();
            }
        });


        //logout Button....
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "logout!...", Toast.LENGTH_SHORT).show();
                editor=getSharedPreferences("LOGIN",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),"Successfully Logout",Toast.LENGTH_LONG).show();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}