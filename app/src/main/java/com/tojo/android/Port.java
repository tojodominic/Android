package com.tojo.android;

import android.os.Parcel;
import android.os.Parcelable;

public class Port implements Parcelable {
    String portNo;
    String service;
    String banner;

    public Port() {
    }

    public Port(String portNo, String service, String banner) {
        this.portNo = portNo;
        this.service = service;
        this.banner = banner;
    }

    protected Port(Parcel in) {
        portNo = in.readString();
        service = in.readString();
        banner = in.readString();
    }

    public static final Creator<Port> CREATOR = new Creator<Port>() {
        @Override
        public Port createFromParcel(Parcel in) {
            return new Port(in);
        }

        @Override
        public Port[] newArray(int size) {
            return new Port[size];
        }
    };

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(portNo);
        parcel.writeString(service);
        parcel.writeString(banner);
    }
}
