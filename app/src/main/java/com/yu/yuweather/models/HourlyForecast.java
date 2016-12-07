package com.yu.yuweather.models;

import java.util.List;

public class HourlyForecast {
    /**
     * summary : 晴朗将持续一整天。
     * icon : clear-day
     * data : [{"time":1478498400,"summary":"局部多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":11.2,"apparentTemperature":11.2,"dewPoint":-7.43,"humidity":0.26,"windSpeed":5.9,"windBearing":346,"cloudCover":0.34,"pressure":1029.89,"ozone":265.77},{"time":1478502000,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":10.49,"apparentTemperature":10.49,"dewPoint":-7.44,"humidity":0.28,"windSpeed":5.31,"windBearing":347,"cloudCover":0.22,"pressure":1030.16,"ozone":267.19},{"time":1478505600,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":9.16,"apparentTemperature":6.79,"dewPoint":-7.41,"humidity":0.3,"windSpeed":4.39,"windBearing":351,"cloudCover":0.09,"pressure":1030.62,"ozone":268.09},{"time":1478509200,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":7.77,"apparentTemperature":5.39,"dewPoint":-7.46,"humidity":0.33,"windSpeed":3.76,"windBearing":354,"cloudCover":0,"pressure":1031.09,"ozone":269.04},{"time":1478512800,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":6.71,"apparentTemperature":4.12,"dewPoint":-7.77,"humidity":0.35,"windSpeed":3.72,"windBearing":356,"cloudCover":0,"pressure":1031.61,"ozone":270.38},{"time":1478516400,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":5.87,"apparentTemperature":2.97,"dewPoint":-8.12,"humidity":0.36,"windSpeed":3.94,"windBearing":356,"cloudCover":0,"pressure":1032.16,"ozone":271.77},{"time":1478520000,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":5.19,"apparentTemperature":2.09,"dewPoint":-8.19,"humidity":0.37,"windSpeed":4.01,"windBearing":356,"cloudCover":0,"pressure":1032.74,"ozone":272.7},{"time":1478523600,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":4.54,"apparentTemperature":1.44,"dewPoint":-8.19,"humidity":0.39,"windSpeed":3.76,"windBearing":357,"cloudCover":0,"pressure":1033.37,"ozone":272.9},{"time":1478527200,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":3.84,"apparentTemperature":0.86,"dewPoint":-8.14,"humidity":0.41,"windSpeed":3.36,"windBearing":359,"cloudCover":0,"pressure":1034.01,"ozone":272.64},{"time":1478530800,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":3.09,"apparentTemperature":0.19,"dewPoint":-8.24,"humidity":0.43,"windSpeed":3.04,"windBearing":2,"cloudCover":0,"pressure":1034.55,"ozone":272.25},{"time":1478534400,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":2.41,"apparentTemperature":-0.56,"dewPoint":-8.41,"humidity":0.45,"windSpeed":2.95,"windBearing":7,"cloudCover":0,"pressure":1034.88,"ozone":271.71},{"time":1478538000,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":1.81,"apparentTemperature":-1.28,"dewPoint":-8.61,"humidity":0.46,"windSpeed":2.95,"windBearing":12,"cloudCover":0,"pressure":1035.08,"ozone":271.03},{"time":1478541600,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":1.29,"apparentTemperature":-1.81,"dewPoint":-8.79,"humidity":0.47,"windSpeed":2.84,"windBearing":15,"cloudCover":0,"pressure":1035.22,"ozone":270.65},{"time":1478545200,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":0.68,"apparentTemperature":-2.24,"dewPoint":-9.1,"humidity":0.48,"windSpeed":2.53,"windBearing":15,"cloudCover":0,"pressure":1035.24,"ozone":270.77},{"time":1478548800,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":0.02,"apparentTemperature":-2.56,"dewPoint":-9.5,"humidity":0.49,"windSpeed":2.11,"windBearing":13,"cloudCover":0,"pressure":1035.2,"ozone":271.18},{"time":1478552400,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":-0.36,"apparentTemperature":-2.32,"dewPoint":-9.74,"humidity":0.49,"windSpeed":1.6,"windBearing":9,"cloudCover":0,"pressure":1035.3,"ozone":271.75},{"time":1478556000,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":-0.11,"apparentTemperature":-0.11,"dewPoint":-9.45,"humidity":0.49,"windSpeed":1.01,"windBearing":358,"cloudCover":0,"pressure":1035.71,"ozone":272.51},{"time":1478559600,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":0.37,"apparentTemperature":0.37,"dewPoint":-9.09,"humidity":0.49,"windSpeed":0.52,"windBearing":314,"cloudCover":0,"pressure":1036.26,"ozone":273.42},{"time":1478563200,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":1.15,"apparentTemperature":1.15,"dewPoint":-8.79,"humidity":0.47,"windSpeed":0.76,"windBearing":254,"cloudCover":0,"pressure":1036.61,"ozone":274.24},{"time":1478566800,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":2.62,"apparentTemperature":2.62,"dewPoint":-8.54,"humidity":0.44,"windSpeed":1.32,"windBearing":239,"cloudCover":0,"pressure":1036.7,"ozone":274.96},{"time":1478570400,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":4.67,"apparentTemperature":3.09,"dewPoint":-8.38,"humidity":0.38,"windSpeed":1.89,"windBearing":235,"cloudCover":0,"pressure":1036.57,"ozone":275.58},{"time":1478574000,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":6.68,"apparentTemperature":5.02,"dewPoint":-8.21,"humidity":0.34,"windSpeed":2.34,"windBearing":232,"cloudCover":0,"pressure":1036.15,"ozone":275.82},{"time":1478577600,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":8.47,"apparentTemperature":6.93,"dewPoint":-7.84,"humidity":0.31,"windSpeed":2.61,"windBearing":229,"cloudCover":0,"pressure":1035.32,"ozone":275.42},{"time":1478581200,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":9.94,"apparentTemperature":8.59,"dewPoint":-7.26,"humidity":0.29,"windSpeed":2.76,"windBearing":226,"cloudCover":0,"pressure":1034.32,"ozone":274.64},{"time":1478584800,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":10.56,"apparentTemperature":10.56,"dewPoint":-6.48,"humidity":0.3,"windSpeed":2.79,"windBearing":223,"cloudCover":0,"pressure":1033.57,"ozone":274.02},{"time":1478588400,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":9.54,"apparentTemperature":8.19,"dewPoint":-5.76,"humidity":0.33,"windSpeed":2.65,"windBearing":222,"cloudCover":0,"pressure":1033.24,"ozone":273.83},{"time":1478592000,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":7.76,"apparentTemperature":6.25,"dewPoint":-5.21,"humidity":0.39,"windSpeed":2.38,"windBearing":221,"cloudCover":0,"pressure":1033.18,"ozone":273.8},{"time":1478595600,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":6.01,"apparentTemperature":4.35,"dewPoint":-5.06,"humidity":0.45,"windSpeed":2.21,"windBearing":222,"cloudCover":0,"pressure":1033.27,"ozone":273.77},{"time":1478599200,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":4.71,"apparentTemperature":2.81,"dewPoint":-5.13,"humidity":0.49,"windSpeed":2.22,"windBearing":226,"cloudCover":0,"pressure":1033.58,"ozone":273.74},{"time":1478602800,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":3.67,"apparentTemperature":1.48,"dewPoint":-5.26,"humidity":0.52,"windSpeed":2.33,"windBearing":231,"cloudCover":0,"pressure":1034.05,"ozone":273.7},{"time":1478606400,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":2.84,"apparentTemperature":0.43,"dewPoint":-5.4,"humidity":0.55,"windSpeed":2.41,"windBearing":233,"cloudCover":0,"pressure":1034.4,"ozone":273.42},{"time":1478610000,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":2.22,"apparentTemperature":-0.31,"dewPoint":-5.53,"humidity":0.56,"windSpeed":2.41,"windBearing":232,"cloudCover":0,"pressure":1034.58,"ozone":272.81},{"time":1478613600,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":1.74,"apparentTemperature":-0.82,"dewPoint":-5.64,"humidity":0.58,"windSpeed":2.36,"windBearing":229,"cloudCover":0,"pressure":1034.67,"ozone":271.97},{"time":1478617200,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":1.33,"apparentTemperature":-1.25,"dewPoint":-5.72,"humidity":0.59,"windSpeed":2.31,"windBearing":226,"cloudCover":0,"pressure":1034.65,"ozone":270.97},{"time":1478620800,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":0.91,"apparentTemperature":-1.69,"dewPoint":-5.78,"humidity":0.61,"windSpeed":2.25,"windBearing":224,"cloudCover":0,"pressure":1034.51,"ozone":269.72},{"time":1478624400,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":0.47,"apparentTemperature":-2.14,"dewPoint":-5.87,"humidity":0.62,"windSpeed":2.19,"windBearing":222,"cloudCover":0,"pressure":1034.24,"ozone":268.31},{"time":1478628000,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":0.03,"apparentTemperature":-2.56,"dewPoint":-6.02,"humidity":0.64,"windSpeed":2.11,"windBearing":220,"cloudCover":0,"pressure":1033.87,"ozone":267.12},{"time":1478631600,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":-0.47,"apparentTemperature":-3,"dewPoint":-6.26,"humidity":0.65,"windSpeed":1.99,"windBearing":217,"cloudCover":0,"pressure":1033.36,"ozone":266.27},{"time":1478635200,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":-0.99,"apparentTemperature":-3.41,"dewPoint":-6.53,"humidity":0.66,"windSpeed":1.85,"windBearing":214,"cloudCover":0,"pressure":1032.8,"ozone":265.64},{"time":1478638800,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":-1.28,"apparentTemperature":-3.59,"dewPoint":-6.73,"humidity":0.66,"windSpeed":1.74,"windBearing":210,"cloudCover":0,"pressure":1032.37,"ozone":265.34},{"time":1478642400,"summary":"晴朗","icon":"clear-night","precipIntensity":0,"precipProbability":0,"temperature":-0.98,"apparentTemperature":-3.12,"dewPoint":-6.53,"humidity":0.66,"windSpeed":1.65,"windBearing":208,"cloudCover":0,"pressure":1032.24,"ozone":265.53},{"time":1478646000,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":-0.51,"apparentTemperature":-2.49,"dewPoint":-6.3,"humidity":0.65,"windSpeed":1.6,"windBearing":206,"cloudCover":0.01,"pressure":1032.25,"ozone":266.05},{"time":1478649600,"summary":"晴朗","icon":"clear-day","precipIntensity":0,"precipProbability":0,"temperature":0.34,"apparentTemperature":-1.72,"dewPoint":-5.96,"humidity":0.63,"windSpeed":1.74,"windBearing":205,"cloudCover":0.07,"pressure":1032.12,"ozone":266.53},{"time":1478653200,"summary":"局部多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":2.03,"apparentTemperature":-0.32,"dewPoint":-5.31,"humidity":0.58,"windSpeed":2.2,"windBearing":204,"cloudCover":0.26,"pressure":1031.76,"ozone":266.88},{"time":1478656800,"summary":"局部多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":4.37,"apparentTemperature":1.87,"dewPoint":-4.46,"humidity":0.53,"windSpeed":2.85,"windBearing":203,"cloudCover":0.51,"pressure":1031.23,"ozone":267.19},{"time":1478660400,"summary":"多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":6.66,"apparentTemperature":4.22,"dewPoint":-3.62,"humidity":0.48,"windSpeed":3.43,"windBearing":204,"cloudCover":0.7,"pressure":1030.46,"ozone":267.4},{"time":1478664000,"summary":"多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":8.61,"apparentTemperature":6.32,"dewPoint":-2.77,"humidity":0.45,"windSpeed":3.94,"windBearing":205,"cloudCover":0.75,"pressure":1029.31,"ozone":267.37},{"time":1478667600,"summary":"多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":10.21,"apparentTemperature":10.21,"dewPoint":-1.88,"humidity":0.43,"windSpeed":4.37,"windBearing":207,"cloudCover":0.73,"pressure":1027.95,"ozone":267.25},{"time":1478671200,"summary":"多云","icon":"partly-cloudy-day","precipIntensity":0,"precipProbability":0,"temperature":10.98,"apparentTemperature":10.98,"dewPoint":-1.14,"humidity":0.43,"windSpeed":4.47,"windBearing":208,"cloudCover":0.74,"pressure":1026.8,"ozone":267.4}]
     */

    private HourlyBean hourly;

    public HourlyBean getHourly() {
        return hourly;
    }

    public void setHourly(HourlyBean hourly) {
        this.hourly = hourly;
    }

    public static class HourlyBean {
        /**
         * time : 1478498400
         * summary : 局部多云
         * icon : partly-cloudy-day
         * precipIntensity : 0
         * precipProbability : 0
         * temperature : 11.2
         * apparentTemperature : 11.2
         * dewPoint : -7.43
         * humidity : 0.26
         * windSpeed : 5.9
         * windBearing : 346
         * cloudCover : 0.34
         * pressure : 1029.89
         * ozone : 265.77
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
            private String precipProbability;
            private String temperature;
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

            public String getPrecipProbability() {
                return precipProbability;
            }

            public void setPrecipProbability(String precipProbability) {
                this.precipProbability = precipProbability;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
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
