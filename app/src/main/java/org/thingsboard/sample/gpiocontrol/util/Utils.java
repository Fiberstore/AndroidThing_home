package org.thingsboard.sample.gpiocontrol.util;

import android.app.Activity;
import android.content.Context;
import android.os.PowerManager;

/**
 * @author 作者：张祥 on 2018/3/5 0005.
 *         邮箱：847874028@qq.com
 *         版本：v1.0
 *         功能：
 */

public class Utils {

    /**退出当前应用*/
    public static void exitApp(Activity activity){
        PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
        wakeLock.release();
    }
}
