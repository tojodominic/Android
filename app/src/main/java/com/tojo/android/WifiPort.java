package com.tojo.android;
import android.os.Parcel;
import android.os.Parcelable;

public class WifiPort implements Parcelable {

    String portNo;
    String banner;
    String service;

    protected WifiPort(Parcel in) {
        portNo = in.readString();
        banner = in.readString();
        service = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(portNo);
        dest.writeString(banner);
        dest.writeString(service);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WifiPort> CREATOR = new Creator<WifiPort>() {
        @Override
        public WifiPort createFromParcel(Parcel in) {
            return new WifiPort(in);
        }

        @Override
        public WifiPort[] newArray(int size) {
            return new WifiPort[size];
        }
    };

    public WifiPort() {
    }

    public WifiPort(String portNo, String banner, String service) {
        this.portNo = portNo;
        this.banner = banner;
        this.service = service;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public static Creator<WifiPort> getCREATOR() {
        return CREATOR;
    }
}
