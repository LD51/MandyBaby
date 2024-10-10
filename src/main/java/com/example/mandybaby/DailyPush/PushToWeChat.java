package com.example.mandybaby.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.mandybaby.DailyPush.Anniversary;
import com.example.mandybaby.DailyPush.CaiHongPi;
import com.example.mandybaby.DailyPush.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PushToWeChat {
    /**
     * 测试号的appId和secret
     */
    @Value("${appId}")
    private  String appId;
    @Value("${secret}")
    private  String secret;
    //模版id
    @Value("${templateId}")
    private  String templateId;
    @Value("${templateId2}")
    private  String templateId2;


    @Autowired
    private Anniversary anniversary;
    private Weather weather;

    public void push(String openId){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId)
                .build();
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        //        templateMessage.addData(new WxMpTemplateData("name", "value", "#FF00FF"));
        //                templateMessage.addData(new WxMpTemplateData(name2, value2, color2));
        //填写变量信息，比如天气之类的
        JSONObject todayWeather = weather.getNanjiTianqi();
        templateMessage.addData(new WxMpTemplateData("riqi",todayWeather.getString("date") + "  "+ todayWeather.getString("week"),"#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi",todayWeather.getString("text_day"),"#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low",todayWeather.getString("low") + "","#173177"));
        templateMessage.addData(new WxMpTemplateData("high",todayWeather.getString("high")+ "","#FF6347" ));
        templateMessage.addData(new WxMpTemplateData("caihongpi",CaiHongPi.getCaiHongPi(),"#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai",anniversary.getLianAi()+"","#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri",anniversary.getShengRi()+"","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("jinianni",anniversary.getjinianri()+"","#FF3300"));
        templateMessage.addData(new WxMpTemplateData("shengri2",anniversary.getShengRi2()+"","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("jinju", CaiHongPi.getJinJu()+"","#C71585"));
        String beizhu = "";
        if(anniversary.getLianAi() == 0){
            beizhu = "今天是恋爱纪念日！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu",beizhu,"#FF0000"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void pushDrink(String openId){
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId2)
                .build();
        templateMessage.addData(new WxMpTemplateData("caihongpi",CaiHongPi.getCaiHongPi(),"#FF69B4"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
