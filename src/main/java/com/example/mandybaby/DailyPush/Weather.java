package com.example.mandybaby.Component;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;

public class Weather {
    private final String key = "YOUR_API_KEY"; // 你的API密钥
    private final String district_id = "YOUR_DISTRICT_ID"; // 区域ID

    public JSONObject getNanjiTianqi() {
        String result;
        JSONObject weatherData = new JSONObject();
        try {
            result = HttpUtil.get("https://devapi.qweather.com/v7/weather/now?location=" + district_id + "&key=" + key);
            JSONObject jsonObject = JSONObject.parseObject(result);

            if ("200".equals(jsonObject.getString("code"))) {
                JSONObject now = jsonObject.getJSONObject("now");
                weatherData.put("temperature", now.getString("temp"));
                weatherData.put("feelsLike", now.getString("feelsLike"));
                weatherData.put("weatherText", now.getString("text"));
                weatherData.put("windSpeed", now.getString("windSpeed"));
                weatherData.put("humidity", now.getString("humidity"));
                weatherData.put("pressure", now.getString("pressure"));
                weatherData.put("visibility", now.getString("vis"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 可以记录日志或抛出自定义异常
        }
        return weatherData;
    }

    public static void main(String[] args) {
        Weather service = new Weather();
        JSONObject weatherInfo = service.getNanjiTianqi();
        System.out.println(weatherInfo);
    }
}
