package com.yu.yuweather.models;

import java.util.List;

public class DailyForecast {

    /**
     * summary : 小雨持续至今天，且周五温度骤降到10°C。
     * icon : rain
     * data : [{"time":1478448000,"summary":"多云将持续至下午。","icon":"partly-cloudy-day","sunriseTime":1478472701,"sunsetTime":1478509736,"moonPhase":0.23,"precipIntensity":0.0483,"precipIntensityMax":0.2464,"precipIntensityMaxTime":1478455200,"precipProbability":0.5,"precipType":"rain","temperatureMin":2.68,"temperatureMinTime":1478530800,"temperatureMax":10.98,"temperatureMaxTime":1478498400,"apparentTemperatureMin":-0.24,"apparentTemperatureMinTime":1478530800,"apparentTemperatureMax":10.98,"apparentTemperatureMaxTime":1478498400,"dewPoint":-4.43,"humidity":0.53,"windSpeed":2.86,"windBearing":345,"cloudCover":0.54,"pressure":1030.11,"ozone":261.27},{"time":1478534400,"summary":"晴朗将持续一整天。","icon":"clear-day","sunriseTime":1478559170,"sunsetTime":1478596077,"moonPhase":0.27,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":-0.76,"temperatureMinTime":1478552400,"temperatureMax":10.57,"temperatureMaxTime":1478584800,"apparentTemperatureMin":-2.93,"apparentTemperatureMinTime":1478548800,"apparentTemperatureMax":10.57,"apparentTemperatureMaxTime":1478584800,"dewPoint":-7.18,"humidity":0.46,"windSpeed":1.05,"windBearing":250,"cloudCover":0,"pressure":1035.34,"ozone":273.15},{"time":1478620800,"summary":"多云将持续一整天。","icon":"partly-cloudy-night","sunriseTime":1478645640,"sunsetTime":1478682419,"moonPhase":0.3,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":-1.49,"temperatureMinTime":1478638800,"temperatureMax":10.97,"temperatureMaxTime":1478671200,"apparentTemperatureMin":-3.74,"apparentTemperatureMinTime":1478638800,"apparentTemperatureMax":10.97,"apparentTemperatureMaxTime":1478671200,"dewPoint":-3.51,"humidity":0.6,"windSpeed":2.58,"windBearing":208,"cloudCover":0.49,"pressure":1029.14,"ozone":269.5},{"time":1478707200,"summary":"局部多云将持续至下午。","icon":"partly-cloudy-day","sunriseTime":1478732109,"sunsetTime":1478768762,"moonPhase":0.34,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":-0.31,"temperatureMinTime":1478725200,"temperatureMax":13.16,"temperatureMaxTime":1478757600,"apparentTemperatureMin":-1.93,"apparentTemperatureMinTime":1478721600,"apparentTemperatureMax":13.16,"apparentTemperatureMaxTime":1478757600,"dewPoint":-4.3,"humidity":0.57,"windSpeed":1.52,"windBearing":271,"cloudCover":0.29,"pressure":1016.66,"ozone":334.2},{"time":1478793600,"summary":"局部多云持续至当晚。","icon":"partly-cloudy-night","sunriseTime":1478818578,"sunsetTime":1478855108,"moonPhase":0.37,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":-1.99,"temperatureMinTime":1478811600,"temperatureMax":9.53,"temperatureMaxTime":1478844000,"apparentTemperatureMin":-1.99,"apparentTemperatureMinTime":1478811600,"apparentTemperatureMax":7.64,"apparentTemperatureMaxTime":1478844000,"dewPoint":-10.43,"humidity":0.37,"windSpeed":0.94,"windBearing":276,"cloudCover":0.02,"pressure":1018.86,"ozone":326.52},{"time":1478880000,"summary":"多云将持续至晚上。","icon":"partly-cloudy-day","sunriseTime":1478905047,"sunsetTime":1478941455,"moonPhase":0.41,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":0.48,"temperatureMinTime":1478901600,"temperatureMax":11.69,"temperatureMaxTime":1478930400,"apparentTemperatureMin":-1.92,"apparentTemperatureMinTime":1478898000,"apparentTemperatureMax":11.69,"apparentTemperatureMaxTime":1478930400,"dewPoint":-5.62,"humidity":0.48,"windSpeed":0.63,"windBearing":17,"cloudCover":0.53,"pressure":1019.15,"ozone":324.43},{"time":1478966400,"summary":"晴朗将持续一整天。","icon":"clear-day","sunriseTime":1478991516,"sunsetTime":1479027803,"moonPhase":0.45,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":0.67,"temperatureMinTime":1478984400,"temperatureMax":13.57,"temperatureMaxTime":1479016800,"apparentTemperatureMin":0.67,"apparentTemperatureMinTime":1478984400,"apparentTemperatureMax":13.57,"apparentTemperatureMaxTime":1479016800,"dewPoint":-4.24,"humidity":0.5,"windSpeed":1.4,"windBearing":250,"cloudCover":0.09,"pressure":1017.03,"ozone":325.46},{"time":1479052800,"summary":"晴朗将持续一整天。","icon":"clear-day","sunriseTime":1479077985,"sunsetTime":1479114154,"moonPhase":0.48,"precipIntensity":0,"precipIntensityMax":0,"precipProbability":0,"temperatureMin":-0.67,"temperatureMinTime":1479074400,"temperatureMax":9.82,"temperatureMaxTime":1479099600,"apparentTemperatureMin":-2.25,"apparentTemperatureMinTime":1479078000,"apparentTemperatureMax":9.32,"apparentTemperatureMaxTime":1479099600,"dewPoint":-10.83,"humidity":0.35,"windSpeed":0.32,"windBearing":349,"cloudCover":0,"pressure":1023.44,"ozone":303.95}]
     */

    private DailyBean daily;

    public DailyBean getDaily() {
        return daily;
    }

    public void setDaily(DailyBean daily) {
        this.daily = daily;
    }

    public static class DailyBean {
        /**
         * time : 1478448000
         * summary : 多云将持续至下午。
         * icon : partly-cloudy-day
         * sunriseTime : 1478472701
         * sunsetTime : 1478509736
         * moonPhase : 0.23
         * precipIntensity : 0.0483
         * precipIntensityMax : 0.2464
         * precipIntensityMaxTime : 1478455200
         * precipProbability : 0.5
         * precipType : rain
         * temperatureMin : 2.68
         * temperatureMinTime : 1478530800
         * temperatureMax : 10.98
         * temperatureMaxTime : 1478498400
         * apparentTemperatureMin : -0.24
         * apparentTemperatureMinTime : 1478530800
         * apparentTemperatureMax : 10.98
         * apparentTemperatureMaxTime : 1478498400
         * dewPoint : -4.43
         * humidity : 0.53
         * windSpeed : 2.86
         * windBearing : 345
         * cloudCover : 0.54
         * pressure : 1030.11
         * ozone : 261.27
         */

        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private String time;
            private String summary;
            private String icon;
            private String sunriseTime;
            private String sunsetTime;
            private String precipProbability;
            private String temperatureMin;
            private String temperatureMinTime;
            private String temperatureMax;
            private String temperatureMaxTime;
            private String humidity;
            private String pressure;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getSunriseTime() {
                return sunriseTime;
            }

            public void setSunriseTime(String sunriseTime) {
                this.sunriseTime = sunriseTime;
            }

            public String getSunsetTime() {
                return sunsetTime;
            }

            public void setSunsetTime(String sunsetTime) {
                this.sunsetTime = sunsetTime;
            }

            public String getPrecipProbability() {
                return precipProbability;
            }

            public void setPrecipProbability(String precipProbability) {
                this.precipProbability = precipProbability;
            }

            public String getTemperatureMin() {
                return temperatureMin;
            }

            public void setTemperatureMin(String temperatureMin) {
                this.temperatureMin = temperatureMin;
            }

            public String getTemperatureMinTime() {
                return temperatureMinTime;
            }

            public void setTemperatureMinTime(String temperatureMinTime) {
                this.temperatureMinTime = temperatureMinTime;
            }

            public String getTemperatureMax() {
                return temperatureMax;
            }

            public void setTemperatureMax(String temperatureMax) {
                this.temperatureMax = temperatureMax;
            }

            public String getTemperatureMaxTime() {
                return temperatureMaxTime;
            }

            public void setTemperatureMaxTime(String temperatureMaxTime) {
                this.temperatureMaxTime = temperatureMaxTime;
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
        }
    }
}
