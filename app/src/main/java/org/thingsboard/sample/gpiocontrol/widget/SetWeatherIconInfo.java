package org.thingsboard.sample.gpiocontrol.widget;

import android.content.Context;
import android.widget.ImageView;

import org.thingsboard.sample.gpiocontrol.R;

/**
 * Created by zhangxiang on 2018/3/20.
 */

public class SetWeatherIconInfo {

    /**
     * 设置天气信息
     */
    public static void setWeatherInfo(Context context, String image, ImageView imageTodayWeatherIcon) {

        /**0 晴
         1 多云
         2 阴
         3 阵雨
         4 雷阵雨
         5 雷阵雨伴有冰雹
         6 雨夹雪
         7 小雨
         8 中雨
         9 大雨
         10 暴雨
         11 大暴雨
         12 特大暴雨
         13 阵雪
         14 小雪
         15 中雪
         16 大雪
         17 暴雪
         18 雾
         19 冻雨
         20 沙尘暴
         21 小雨-中雨
         22 中雨-大雨
         23 大雨-暴雨
         24 暴雨-大暴雨
         25 大暴雨-特大暴雨
         26 小雪-中雪
         27 中雪-大雪
         28 大雪-暴雪
         29 浮尘
         30 扬沙
         31 强沙尘暴
         32 浓雾
         39 台风
         49 强浓雾
         53 霾
         54 中毒霾
         55 重度霾
         56 严重霾
         57 大雾
         58 特强浓雾
         99 无
         301 雨
         302 雪*/
        //int[] iconArray = context.getResources().getIntArray(R.array.weather_icon_array);
        switch (image) {
            case "0":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_0_sun);
                break;
            case "1":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_1_duoyun);
                break;
            case "2":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_2_ying);
                break;
            case "3":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_3_zhenyu);
                break;
            case "4":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_4_leizhenyu);
                break;
            case "5":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_5_leizhenyu_bingbao);
                break;
            case "6":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_6_yujiaxue);
                break;
            case "7":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_7_xiaoyu);
                break;
            case "8":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_8_zhongyu);
                break;
            case "9":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_9_dayu);
                break;
            case "10":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_10_baoyu);
                break;
            case "11":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_11_dabaoyu);
                break;
            case "12":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_12_tedabaoyu);
                break;
            case "13":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_13_zhenxue);
                break;
            case "14":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_14_xiaoxue);
                break;
            case "15":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_15_zhongxue);
                break;
            case "16":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_16_daxue);
                break;
            case "17":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_17_baoxue);
                break;
            case "18":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_18_wu);
                break;
            case "19":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_19_dongyu);
                break;
            case "20":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_20_shachengbao);
                break;
            case "21":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_21_xiaoyu_zhonyu);
                break;
            case "22":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_22_zhongyu_dayu);
                break;
            case "23":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_23_dayu_baoyu);
                break;
            case "24":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_24_baoyu_dabaoyu);
                break;
            case "25":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_25_dabao_tedabaoyu);
                break;
            case "26":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_26_xiaoxue_zhongxue);
                break;
            case "27":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_27_zhongxue_daxue);
                break;
            case "28":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_28_daxue_baoxue);
                break;
            case "29":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_29_fucheng);
                break;
            case "30":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_30_yangchen);
                break;
            case "31":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_31_qiangshachen);
                break;
            case "32":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_32_nongwu);
                break;
            case "49":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_49_qiangnongwu);
                break;
            case "53":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_53_mai);
                break;
            case "54":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_54_zhongdumai);
                break;
            case "55":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_55_zzhongdumai);
                break;
            case "56":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_56_yanzhongmai);
                break;
            case "57":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_57_dawu);
                break;
            case "58":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_58_tedanongwu);
                break;
            case "99":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_99_non);
                break;
            case "301":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_301_yu);
                break;
            case "302":
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_302_xue);
                break;
            default:
                imageTodayWeatherIcon.setBackgroundResource(R.drawable.w_7_xiaoyu);
                break;
        }
    }
}
