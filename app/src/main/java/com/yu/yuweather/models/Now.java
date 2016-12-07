package com.yu.yuweather.models;

public class Now {

    /**
     * city : 广州
     * cnty : 中国
     * id : CN101280101
     * lat : 23.108000
     * lon : 113.265000
     * update : {"loc":"2016-11-07 14:53","utc":"2016-11-07 06:53"}
     */

    private BasicBean basic;
    /**
     * cond : {"code":"101","txt":"多云"}
     * fl : 30
     * hum : 52
     * pcpn : 0
     * pres : 1014
     * tmp : 29
     * vis : 8
     * wind : {"deg":"111","dir":"东北风","sc":"3-4","spd":"13"}
     */

    private NowBean now;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public static class BasicBean {
        private String city;
        private String id;
        private String lat;
        private String lon;
        /**
         * loc : 2016-11-07 14:53
         * utc : 2016-11-07 06:53
         */

        private UpdateBean update;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public static class UpdateBean {
            private String loc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }
        }
    }

    public static class NowBean {
        /**
         * code : 101
         * txt : 多云
         */

        private CondBean cond;
        private String hum;
        private String pcpn;
        private String tmp;
        /**
         * deg : 111
         * dir : 东北风
         * sc : 3-4
         * spd : 13
         */

        private WindBean wind;

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class CondBean {
            private String code;
            private String txt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class WindBean {
            private String dir;

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }
        }
    }
}
