package com.tojo.android;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Domain implements Parcelable {
    String domainName;
    String time;
    String mode;
    String fullName;
    List<SubDomain> subDomainList;

    public Domain() {
    }

    public Domain(String domainName, String time, String mode, String fullName, List<SubDomain> subDomainList) {
        this.domainName = domainName;
        this.time = time;
        this.mode = mode;
        this.fullName = fullName;
        this.subDomainList = subDomainList;
    }

    protected Domain(Parcel in) {
        domainName = in.readString();
        time = in.readString();
        mode = in.readString();
        fullName = in.readString();
        subDomainList = in.createTypedArrayList(SubDomain.CREATOR);
    }

    public static final Creator<Domain> CREATOR = new Creator<Domain>() {
        @Override
        public Domain createFromParcel(Parcel in) {
            return new Domain(in);
        }

        @Override
        public Domain[] newArray(int size) {
            return new Domain[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public List<SubDomain> getSubDomainList() {
        return subDomainList;
    }

    public void setSubDomainList(List<SubDomain> subDomainList) {
        this.subDomainList = subDomainList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(domainName);
        parcel.writeString(time);
        parcel.writeString(mode);
        parcel.writeString(fullName);
        parcel.writeTypedList(subDomainList);
    }
}
