package org.thingsboard.sample.gpiocontrol.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by zhangxiang on 2018/3/4.
 */

public class GetInternetTime {

    private static long ucDate;

    /**
     * 获取互联网时间
     */
    public static Long getInternetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("http://time.tianqi.com");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            //取得网站日期时间
            ucDate = uc.getDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ucDate;
    }
}
