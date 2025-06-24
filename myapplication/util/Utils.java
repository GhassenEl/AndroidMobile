package tn.esprit.myapplication.util;



import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

public class Utils {
    public static String getDeviceIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    String ip = addr.getHostAddress();
                    if (!addr.isLoopbackAddress() && ip.indexOf(':') < 0) {
                        return ip;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "127.0.0.1";
    }
}

