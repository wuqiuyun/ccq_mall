package per.sue.gear2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

/**
 * Created by sue on 2016/11/15.
 */
public class DeviceUtils {

    /**
     *获取Imei 号
     * @return
     */
    public static String getUniqueId(Context context) {
        String deviceId = "";
        try {
            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if(!TextUtils.isEmpty(imei)){
                deviceId = imei;
            }else {
                //wifi mac地址
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                String wifiMac = info.getMacAddress();
                if(!TextUtils.isEmpty(wifiMac)){
                    deviceId = wifiMac;
                }else {
                    //序列号（sn）
                    String sn = tm.getSimSerialNumber();
                    if(!TextUtils.isEmpty(sn)){
                        deviceId = sn;
                    }else{
                        //如果上面都没有， 则生成一个id：随机码
                        String uuid = getUUID(context);
                        if(!TextUtils.isEmpty(uuid)){
                            deviceId = uuid;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            deviceId = getUUID(context);
        }
        GearLog.e("getUniqueId : ", deviceId);
        return deviceId;
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context){
        String uuid = null;
        SharedPreferences sharedPreferences =  context.getSharedPreferences("uniqueid", context.MODE_PRIVATE);
        if(sharedPreferences != null){
            uuid = sharedPreferences.getString("uuid", "");
        }
        if(TextUtils.isEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uuid", uuid);
        }
        GearLog.i("getUUID : ", uuid);
        return uuid;
    }

}
