package com.tojo.android;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


public class WifiInfoHelper {


    private WifiManager wifiManager;


    public WifiInfoHelper(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }


    public String getWifiInfo() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        int frequency = wifiInfo.getFrequency();
        int networkId = wifiInfo.getNetworkId();
        String linkspeed = String.valueOf(wifiInfo.getLinkSpeed());
        String macaddress = wifiInfo.getMacAddress();
        int security = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            security = wifiInfo.getCurrentSecurityType();
        }
        int maxlinkspeed = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            maxlinkspeed = wifiInfo.getMaxSupportedRxLinkSpeedMbps();
        }
        String networkid = String.valueOf(wifiInfo.getNetworkId());
        int wifiStandard = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            wifiStandard = wifiInfo.getWifiStandard();
        }


        String capabilities = wifiManager.getConfiguredNetworks().stream()
                .filter(config -> config.networkId == networkId)
                .findFirst()
                .map(config -> config.allowedKeyManagement.toString())
                .orElse("Unknown");


        return "SSID: " + ssid + "\nFrequency: " + frequency + " MHz" + "\nMAC Address: " + macaddress + "\nWifi Standard: " + wifiStandard + "\nNetwork Id: " + networkid + "\nMaximum Link Speed: " + maxlinkspeed + "\nCurrent security Type: " + security + "\nLink Speed: " + linkspeed;
    }
}

