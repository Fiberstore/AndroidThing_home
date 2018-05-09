package com.thingsboard.home.bean;

import java.util.List;

/**
 * Created by zhangxiang on 2018/3/9.
 */

public class WeatherInfoBean {


    /**
     * status : 0
     * msg : ok
     * result : {"city":"武汉","cityid":"179","citycode":"101200101","date":"2018-03-09","week":"星期五","weather":"晴","temp":"6","temphigh":"15","templow":"4","img":"0","humidity":"88","pressure":"1021","windspeed":"2.3","winddirect":"静风","windpower":"0级","updatetime":"2018-03-09 21:00","index":[{"iname":"空调指数","ivalue":"较少开启","detail":"您将感到很舒适，一般不需要开启空调。"},{"iname":"运动指数","ivalue":"较不宜","detail":"天气较好，但考虑天气寒冷，推荐您进行室内运动，户外运动时请注意保暖并做好准备活动。"},{"iname":"紫外线指数","ivalue":"中等","detail":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"},{"iname":"感冒指数","ivalue":"易发","detail":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"},{"iname":"洗车指数","ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"iname":"空气污染扩散指数","ivalue":"中","detail":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},{"iname":"穿衣指数","ivalue":"较冷","detail":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}],"aqi":{"so2":"9","so224":"8","no2":"78","no224":"50","co":"0.890","co24":"0.790","o3":"56","o38":"101","o324":"104","pm10":"103","pm1024":"74","pm2_5":"44","pm2_524":"54","iso2":"4","ino2":"40","ico":"9","io3":"18","io38":"53","ipm10":"77","ipm2_5":"61","aqi":"77","primarypollutant":"PM10","quality":"良","timepoint":"2018-03-09 20:00:00","aqiinfo":{"level":"二级","color":"#FFFF00","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响","measure":"极少数异常敏感人群应减少户外活动"}},"daily":[{"date":"2018-03-09","week":"星期五","sunrise":"06:42","sunset":"18:26","night":{"weather":"晴","templow":"4","img":"0","winddirect":"东南风","windpower":"微风"},"day":{"weather":"晴","temphigh":"15","img":"0","winddirect":"东南风","windpower":"微风"}},{"date":"2018-03-10","week":"星期六","sunrise":"06:41","sunset":"18:27","night":{"weather":"多云","templow":"5","img":"1","winddirect":"东风","windpower":"微风"},"day":{"weather":"多云","temphigh":"18","img":"1","winddirect":"南风","windpower":"微风"}},{"date":"2018-03-11","week":"星期日","sunrise":"06:40","sunset":"18:28","night":{"weather":"多云","templow":"8","img":"1","winddirect":"南风","windpower":"微风"},"day":{"weather":"多云","temphigh":"18","img":"1","winddirect":"东南风","windpower":"微风"}},{"date":"2018-03-12","week":"星期一","sunrise":"06:39","sunset":"18:29","night":{"weather":"多云","templow":"9","img":"1","winddirect":"东风","windpower":"微风"},"day":{"weather":"多云","temphigh":"20","img":"1","winddirect":"西风","windpower":"微风"}},{"date":"2018-03-13","week":"星期二","sunrise":"06:37","sunset":"18:29","night":{"weather":"小雨","templow":"10","img":"7","winddirect":"东南风","windpower":"微风"},"day":{"weather":"小雨","temphigh":"20","img":"7","winddirect":"西风","windpower":"微风"}},{"date":"2018-03-14","week":"星期三","sunrise":"06:36","sunset":"18:30","night":{"weather":"中雨","templow":"12","img":"8","winddirect":"东南风","windpower":"微风"},"day":{"weather":"小雨","temphigh":"20","img":"7","winddirect":"东南风","windpower":"微风"}},{"date":"2018-03-15","week":"星期四","sunrise":"06:35","sunset":"18:31","night":{"weather":"小雨","templow":"10","img":"7","winddirect":"东南风","windpower":"微风"},"day":{"weather":"小雨","temphigh":"20","img":"7","winddirect":"东南风","windpower":"微风"}}],"hourly":[{"time":"21:00","weather":"晴","temp":"9","img":"0"},{"time":"22:00","weather":"晴","temp":"8","img":"0"},{"time":"23:00","weather":"晴","temp":"7","img":"0"},{"time":"0:00","weather":"晴","temp":"7","img":"0"},{"time":"1:00","weather":"晴","temp":"6","img":"0"},{"time":"2:00","weather":"晴","temp":"6","img":"0"},{"time":"3:00","weather":"晴","temp":"6","img":"0"},{"time":"4:00","weather":"晴","temp":"4","img":"0"},{"time":"5:00","weather":"晴","temp":"6","img":"0"},{"time":"6:00","weather":"晴","temp":"6","img":"0"},{"time":"7:00","weather":"晴","temp":"7","img":"0"},{"time":"8:00","weather":"晴","temp":"8","img":"0"},{"time":"9:00","weather":"晴","temp":"10","img":"0"},{"time":"10:00","weather":"晴","temp":"12","img":"0"},{"time":"11:00","weather":"晴","temp":"14","img":"0"},{"time":"12:00","weather":"多云","temp":"15","img":"1"},{"time":"13:00","weather":"多云","temp":"16","img":"1"},{"time":"14:00","weather":"多云","temp":"17","img":"1"},{"time":"15:00","weather":"多云","temp":"18","img":"1"},{"time":"16:00","weather":"多云","temp":"17","img":"1"},{"time":"17:00","weather":"多云","temp":"16","img":"1"},{"time":"18:00","weather":"多云","temp":"15","img":"1"},{"time":"19:00","weather":"多云","temp":"14","img":"1"},{"time":"20:00","weather":"多云","temp":"13","img":"1"}]}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * city : 武汉
         * cityid : 179
         * citycode : 101200101
         * date : 2018-03-09
         * week : 星期五
         * weather : 晴
         * temp : 6
         * temphigh : 15
         * templow : 4
         * img : 0
         * humidity : 88
         * pressure : 1021
         * windspeed : 2.3
         * winddirect : 静风
         * windpower : 0级
         * updatetime : 2018-03-09 21:00
         * index : [{"iname":"空调指数","ivalue":"较少开启","detail":"您将感到很舒适，一般不需要开启空调。"},{"iname":"运动指数","ivalue":"较不宜","detail":"天气较好，但考虑天气寒冷，推荐您进行室内运动，户外运动时请注意保暖并做好准备活动。"},{"iname":"紫外线指数","ivalue":"中等","detail":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"},{"iname":"感冒指数","ivalue":"易发","detail":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。"},{"iname":"洗车指数","ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"iname":"空气污染扩散指数","ivalue":"中","detail":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},{"iname":"穿衣指数","ivalue":"较冷","detail":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}]
         * aqi : {"so2":"9","so224":"8","no2":"78","no224":"50","co":"0.890","co24":"0.790","o3":"56","o38":"101","o324":"104","pm10":"103","pm1024":"74","pm2_5":"44","pm2_524":"54","iso2":"4","ino2":"40","ico":"9","io3":"18","io38":"53","ipm10":"77","ipm2_5":"61","aqi":"77","primarypollutant":"PM10","quality":"良","timepoint":"2018-03-09 20:00:00","aqiinfo":{"level":"二级","color":"#FFFF00","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响","measure":"极少数异常敏感人群应减少户外活动"}}
         * daily : [{"date":"2018-03-09","week":"星期五","sunrise":"06:42","sunset":"18:26","night":{"weather":"晴","templow":"4","img":"0","winddirect":"东南风","windpower":"微风"},"day":{"weather":"晴","temphigh":"15","img":"0","winddirect":"东南风","windpower":"微风"}},{"date":"2018-03-10","week":"星期六","sunrise":"06:41","sunset":"18:27","night":{"weather":"多云","templow":"5","img":"1","winddirect":"东风","windpower":"微风"},"day":{"weather":"多云","temphigh":"18","img":"1","winddirect":"南风","windpower":"微风"}},{"date":"2018-03-11","week":"星期日","sunrise":"06:40","sunset":"18:28","night":{"weather":"多云","templow":"8","img":"1","winddirect":"南风","windpower":"微风"},"day":{"weather":"多云","temphigh":"18","img":"1","winddirect":"东南风","windpower":"微风"}},{"date":"2018-03-12","week":"星期一","sunrise":"06:39","sunset":"18:29","night":{"weather":"多云","templow":"9","img":"1","winddirect":"东风","windpower":"微风"},"day":{"weather":"多云","temphigh":"20","img":"1","winddirect":"西风","windpower":"微风"}},{"date":"2018-03-13","week":"星期二","sunrise":"06:37","sunset":"18:29","night":{"weather":"小雨","templow":"10","img":"7","winddirect":"东南风","windpower":"微风"},"day":{"weather":"小雨","temphigh":"20","img":"7","winddirect":"西风","windpower":"微风"}},{"date":"2018-03-14","week":"星期三","sunrise":"06:36","sunset":"18:30","night":{"weather":"中雨","templow":"12","img":"8","winddirect":"东南风","windpower":"微风"},"day":{"weather":"小雨","temphigh":"20","img":"7","winddirect":"东南风","windpower":"微风"}},{"date":"2018-03-15","week":"星期四","sunrise":"06:35","sunset":"18:31","night":{"weather":"小雨","templow":"10","img":"7","winddirect":"东南风","windpower":"微风"},"day":{"weather":"小雨","temphigh":"20","img":"7","winddirect":"东南风","windpower":"微风"}}]
         * hourly : [{"time":"21:00","weather":"晴","temp":"9","img":"0"},{"time":"22:00","weather":"晴","temp":"8","img":"0"},{"time":"23:00","weather":"晴","temp":"7","img":"0"},{"time":"0:00","weather":"晴","temp":"7","img":"0"},{"time":"1:00","weather":"晴","temp":"6","img":"0"},{"time":"2:00","weather":"晴","temp":"6","img":"0"},{"time":"3:00","weather":"晴","temp":"6","img":"0"},{"time":"4:00","weather":"晴","temp":"4","img":"0"},{"time":"5:00","weather":"晴","temp":"6","img":"0"},{"time":"6:00","weather":"晴","temp":"6","img":"0"},{"time":"7:00","weather":"晴","temp":"7","img":"0"},{"time":"8:00","weather":"晴","temp":"8","img":"0"},{"time":"9:00","weather":"晴","temp":"10","img":"0"},{"time":"10:00","weather":"晴","temp":"12","img":"0"},{"time":"11:00","weather":"晴","temp":"14","img":"0"},{"time":"12:00","weather":"多云","temp":"15","img":"1"},{"time":"13:00","weather":"多云","temp":"16","img":"1"},{"time":"14:00","weather":"多云","temp":"17","img":"1"},{"time":"15:00","weather":"多云","temp":"18","img":"1"},{"time":"16:00","weather":"多云","temp":"17","img":"1"},{"time":"17:00","weather":"多云","temp":"16","img":"1"},{"time":"18:00","weather":"多云","temp":"15","img":"1"},{"time":"19:00","weather":"多云","temp":"14","img":"1"},{"time":"20:00","weather":"多云","temp":"13","img":"1"}]
         */

        private String city;
        private String cityid;
        private String citycode;
        private String date;
        private String week;
        private String weather;
        private String temp;
        private String temphigh;
        private String templow;
        private String img;
        private String humidity;
        private String pressure;
        private String windspeed;
        private String winddirect;
        private String windpower;
        private String updatetime;
        private AqiBean aqi;
        private List<IndexBean> index;
        private List<DailyBean> daily;
        private List<HourlyBean> hourly;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getTemphigh() {
            return temphigh;
        }

        public void setTemphigh(String temphigh) {
            this.temphigh = temphigh;
        }

        public String getTemplow() {
            return templow;
        }

        public void setTemplow(String templow) {
            this.templow = templow;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(String windspeed) {
            this.windspeed = windspeed;
        }

        public String getWinddirect() {
            return winddirect;
        }

        public void setWinddirect(String winddirect) {
            this.winddirect = winddirect;
        }

        public String getWindpower() {
            return windpower;
        }

        public void setWindpower(String windpower) {
            this.windpower = windpower;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<DailyBean> getDaily() {
            return daily;
        }

        public void setDaily(List<DailyBean> daily) {
            this.daily = daily;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
        }

        public static class AqiBean {
            /**
             * so2 : 9
             * so224 : 8
             * no2 : 78
             * no224 : 50
             * co : 0.890
             * co24 : 0.790
             * o3 : 56
             * o38 : 101
             * o324 : 104
             * pm10 : 103
             * pm1024 : 74
             * pm2_5 : 44
             * pm2_524 : 54
             * iso2 : 4
             * ino2 : 40
             * ico : 9
             * io3 : 18
             * io38 : 53
             * ipm10 : 77
             * ipm2_5 : 61
             * aqi : 77
             * primarypollutant : PM10
             * quality : 良
             * timepoint : 2018-03-09 20:00:00
             * aqiinfo : {"level":"二级","color":"#FFFF00","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响","measure":"极少数异常敏感人群应减少户外活动"}
             */

            private String so2;
            private String so224;
            private String no2;
            private String no224;
            private String co;
            private String co24;
            private String o3;
            private String o38;
            private String o324;
            private String pm10;
            private String pm1024;
            private String pm2_5;
            private String pm2_524;
            private String iso2;
            private String ino2;
            private String ico;
            private String io3;
            private String io38;
            private String ipm10;
            private String ipm2_5;
            private String aqi;
            private String primarypollutant;
            private String quality;
            private String timepoint;
            private AqiinfoBean aqiinfo;

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            public String getSo224() {
                return so224;
            }

            public void setSo224(String so224) {
                this.so224 = so224;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getNo224() {
                return no224;
            }

            public void setNo224(String no224) {
                this.no224 = no224;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getCo24() {
                return co24;
            }

            public void setCo24(String co24) {
                this.co24 = co24;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getO38() {
                return o38;
            }

            public void setO38(String o38) {
                this.o38 = o38;
            }

            public String getO324() {
                return o324;
            }

            public void setO324(String o324) {
                this.o324 = o324;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm1024() {
                return pm1024;
            }

            public void setPm1024(String pm1024) {
                this.pm1024 = pm1024;
            }

            public String getPm2_5() {
                return pm2_5;
            }

            public void setPm2_5(String pm2_5) {
                this.pm2_5 = pm2_5;
            }

            public String getPm2_524() {
                return pm2_524;
            }

            public void setPm2_524(String pm2_524) {
                this.pm2_524 = pm2_524;
            }

            public String getIso2() {
                return iso2;
            }

            public void setIso2(String iso2) {
                this.iso2 = iso2;
            }

            public String getIno2() {
                return ino2;
            }

            public void setIno2(String ino2) {
                this.ino2 = ino2;
            }

            public String getIco() {
                return ico;
            }

            public void setIco(String ico) {
                this.ico = ico;
            }

            public String getIo3() {
                return io3;
            }

            public void setIo3(String io3) {
                this.io3 = io3;
            }

            public String getIo38() {
                return io38;
            }

            public void setIo38(String io38) {
                this.io38 = io38;
            }

            public String getIpm10() {
                return ipm10;
            }

            public void setIpm10(String ipm10) {
                this.ipm10 = ipm10;
            }

            public String getIpm2_5() {
                return ipm2_5;
            }

            public void setIpm2_5(String ipm2_5) {
                this.ipm2_5 = ipm2_5;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getPrimarypollutant() {
                return primarypollutant;
            }

            public void setPrimarypollutant(String primarypollutant) {
                this.primarypollutant = primarypollutant;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public String getTimepoint() {
                return timepoint;
            }

            public void setTimepoint(String timepoint) {
                this.timepoint = timepoint;
            }

            public AqiinfoBean getAqiinfo() {
                return aqiinfo;
            }

            public void setAqiinfo(AqiinfoBean aqiinfo) {
                this.aqiinfo = aqiinfo;
            }

            public static class AqiinfoBean {
                /**
                 * level : 二级
                 * color : #FFFF00
                 * affect : 空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响
                 * measure : 极少数异常敏感人群应减少户外活动
                 */

                private String level;
                private String color;
                private String affect;
                private String measure;

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getAffect() {
                    return affect;
                }

                public void setAffect(String affect) {
                    this.affect = affect;
                }

                public String getMeasure() {
                    return measure;
                }

                public void setMeasure(String measure) {
                    this.measure = measure;
                }
            }
        }

        public static class IndexBean {
            /**
             * iname : 空调指数
             * ivalue : 较少开启
             * detail : 您将感到很舒适，一般不需要开启空调。
             */

            private String iname;
            private String ivalue;
            private String detail;

            public String getIname() {
                return iname;
            }

            public void setIname(String iname) {
                this.iname = iname;
            }

            public String getIvalue() {
                return ivalue;
            }

            public void setIvalue(String ivalue) {
                this.ivalue = ivalue;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class DailyBean {
            /**
             * date : 2018-03-09
             * week : 星期五
             * sunrise : 06:42
             * sunset : 18:26
             * night : {"weather":"晴","templow":"4","img":"0","winddirect":"东南风","windpower":"微风"}
             * day : {"weather":"晴","temphigh":"15","img":"0","winddirect":"东南风","windpower":"微风"}
             */

            private String date;
            private String week;
            private String sunrise;
            private String sunset;
            private NightBean night;
            private DayBean day;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public NightBean getNight() {
                return night;
            }

            public void setNight(NightBean night) {
                this.night = night;
            }

            public DayBean getDay() {
                return day;
            }

            public void setDay(DayBean day) {
                this.day = day;
            }

            public static class NightBean {
                /**
                 * weather : 晴
                 * templow : 4
                 * img : 0
                 * winddirect : 东南风
                 * windpower : 微风
                 */

                private String weather;
                private String templow;
                private String img;
                private String winddirect;
                private String windpower;

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getTemplow() {
                    return templow;
                }

                public void setTemplow(String templow) {
                    this.templow = templow;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getWinddirect() {
                    return winddirect;
                }

                public void setWinddirect(String winddirect) {
                    this.winddirect = winddirect;
                }

                public String getWindpower() {
                    return windpower;
                }

                public void setWindpower(String windpower) {
                    this.windpower = windpower;
                }
            }

            public static class DayBean {
                /**
                 * weather : 晴
                 * temphigh : 15
                 * img : 0
                 * winddirect : 东南风
                 * windpower : 微风
                 */

                private String weather;
                private String temphigh;
                private String img;
                private String winddirect;
                private String windpower;

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getTemphigh() {
                    return temphigh;
                }

                public void setTemphigh(String temphigh) {
                    this.temphigh = temphigh;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getWinddirect() {
                    return winddirect;
                }

                public void setWinddirect(String winddirect) {
                    this.winddirect = winddirect;
                }

                public String getWindpower() {
                    return windpower;
                }

                public void setWindpower(String windpower) {
                    this.windpower = windpower;
                }
            }
        }

        public static class HourlyBean {
            /**
             * time : 21:00
             * weather : 晴
             * temp : 9
             * img : 0
             */

            private String time;
            private String weather;
            private String temp;
            private String img;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
