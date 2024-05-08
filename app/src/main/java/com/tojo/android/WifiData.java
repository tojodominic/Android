package com.tojo.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WifiData implements Parcelable {
    String ip;


    protected WifiData(Parcel in) {
        ip = in.readString();

    }

    public static final Creator<WifiData> CREATOR = new Creator<WifiData>() {
        @Override
        public WifiData createFromParcel(Parcel in) {
            return new WifiData(in);
        }

        @Override
        public WifiData[] newArray(int size) {
            return new WifiData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ip);
    }

    public WifiData() {
    }

    public WifiData(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public static Creator<WifiData> getCREATOR() {
        return CREATOR;
    }
}
