package com.youedata.cd.industries.web.admin.util;


import com.alibaba.fastjson.JSONObject;
import com.youedata.cd.industries.pojo.street.Street;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenyongke on 2016/7/11.
 */
public class Address2Position {
    private static String urlStr;

    public static Point findPoint(String address) {
        return findPoint(address, null);
    }

    public static Point findPoint(String address, String city) {
        if (city == null || "".equals(city)) {
            city = "成都市";
        }
        urlStr = "http://api.map.baidu.com/geocoder/v2/";
        Point point = new Point();
        try {
            Map map = new HashMap();
            map.put("output", "json");
            map.put("ak", "d5zmtQBWxeuOyRFibXOQQGdXGdzLEfLm");
            map.put("address", address);
            map.put("city", city);
            Document doc = Jsoup.connect(urlStr).data(map).get();
            String str = doc.body().text().toString();
            JSONObject rootJSON = JSONObject.parseObject(str);
            JSONObject locationJSON = rootJSON.getJSONObject("result").getJSONObject("location");
            point.setLng(locationJSON.getDouble("lng"));
            point.setLat(locationJSON.getDouble("lat"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return point;
        }
    }

    public static String findLocation1(String address) {
        return findLocation(address, null);
    }

    public static String findLocation(String address, String city) {
        if (city == null || "".equals(city)) {
            city = "成都市";
        }
        Point point = Address2Position.findPoint(address, city);
        Map map = new HashMap();
        String street = null;
        map.put("output", "json");
        map.put("ak", "d5zmtQBWxeuOyRFibXOQQGdXGdzLEfLm");
        String location = point.getLat() + "," + point.getLng();
        map.put("location", location);
        try {
            Document doc = Jsoup.connect(urlStr).data(map).get();
            String str = doc.body().text().toString();
            JSONObject rootJSON = JSONObject.parseObject(str);
            JSONObject locationJSON = rootJSON.getJSONObject("result").getJSONObject("addressComponent");
            street = locationJSON.getString("street");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return street;
        }
    }




    public static Street findJsonObject1(String address) {
        String city = null;
        if (city == null || "".equals(city)) {
            city = "成都市";
        }
        Point point = Address2Position.findPoint(address, city);
        Street street = new Street();
        street.setName(address);
        street.setLongitude(point.getLng());
        street.setLatitude(point.getLat());
        return street;
    }














    public static Map<String, Object> getPosition(String address) {
        return getPosition(address, null);
    }

    public static Map<String, Object> getPosition(String address, String city) {
        Map<String, Object> position = new HashMap<String, Object>();
        Point point = findPoint(address, city);
        String street = findLocation(address, city);
        position.put("point", point);
        position.put("street", street);
        return position;
    }


    public static class Point {
        private Double lng;
        private Double lat;

        public Point() {
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "lng=" + lng +
                    ", lat=" + lat +
                    '}';
        }
    }

    public static void main(String[] args) {
        Point p = Address2Position.findPoint("北京");
        System.out.println(p);
    }
}


