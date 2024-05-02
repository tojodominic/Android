package com.tojo.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Directory implements Parcelable {
    String path;
    String status;
    List<String> urls;

    public Directory() {
    }

    public Directory(String path, String status, List<String> urls) {
        this.path = path;
        this.status = status;
        this.urls = urls;
    }

    protected Directory(Parcel in) {
        path = in.readString();
        status = in.readString();
        urls = in.createStringArrayList();
    }

    public static final Creator<Directory> CREATOR = new Creator<Directory>() {
        @Override
        public Directory createFromParcel(Parcel in) {
            return new Directory(in);
        }

        @Override
        public Directory[] newArray(int size) {
            return new Directory[size];
        }
    };

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeString(status);
        parcel.writeStringList(urls);
    }
}
