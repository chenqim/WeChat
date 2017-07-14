package com.wx.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.baidu.translate.demo.TransApi;
import com.wx.po.AccessToken;
import com.wx.po.trans.Trans;
import com.wx.po.trans.Trans_result;
import com.wx.po.weather.Daily;
import com.wx.po.weather.Location;
import com.wx.po.weather.Results;
import com.wx.po.weather.Root;
import com.xinzhi.weather.demo.DemoJava;

import net.sf.json.JSONObject;

public class WeixinUtil {
	// 微信
	private static final String APPID = "wx0d0e1b945ce0dd07";
	private static final String APPSECRET = "14fe59ab069e301e0be260ba6ac5337b";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 百度翻译
	private static final String APP_ID = "20170503000046258";
    private static final String SECURITY_KEY = "M6lGbXufKz4dYLJNDo4x";
	
	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if(entity != null){
			String result = EntityUtils.toString(entity,"UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}
	
	/**
	 * post请求
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * 获取accesstoken
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setAccess_token(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	/**
	 * 翻译接口对接
	 * @param source
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String translate(String source) throws ParseException, IOException{
		TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String jsonStr = api.getTransResult(source, "auto", "auto");
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Trans trans = (Trans) JSONObject.toBean(jsonObject, Trans.class);
        Trans_result[] trans_result = trans.getTrans_result();
        String dst = trans_result[0].getDst();
        return dst; 
	}
	
	/**
	 * 天气接口对接
	 * @param source
	 * @return
	 */
	public static String weather(String source, String start, String end){
		StringBuffer weather = new StringBuffer();;
		DemoJava demo = new DemoJava();
        try {
            String url = demo.generateGetDiaryWeatherURL(
                    source,
                    "zh-Hans",
                    "c",
                    start,
                    end
            );
            // System.out.println("URL:" + url);
            JSONObject jsonObject = WeixinUtil.doGetStr(url);
            Root root = (Root) JSONObject.toBean(jsonObject, Root.class);
            Results[] results = root.getResults();
            Results result = results[0];
            
            Location location = result.getLocation();
            String name = location.getName();
            
            Daily[] dailys = result.getDaily();
            Daily daily = dailys[0];
            
            // 日期
            String date = daily.getDate();
            // 白天天气情况文字描述
            String text_day = daily.getText_day();
            // 最低温度
            String low = daily.getLow();
            // 最高温度
            String high = daily.getHigh();
            // 夜晚天气情况文字描述
            String text_night = daily.getText_night();
            // 风向
            String wind_direction = daily.getWind_direction();
            // 风速
            String wind_speed = daily.getWind_speed();
            // 风力等级
            String wind_scale = daily.getWind_scale();
            weather.append(name);
            String day = "0".equals(start) ? "今天" : "1".equals(start) ? "明天":"后天";
            weather.append(day);
            weather.append("白天：" + text_day + "，傍晚：" + text_night +"，最高温度：" + high + "℃" + "，最低温度：" + low + "℃，");
            weather.append(wind_direction + "风" + wind_scale + "级，风速：" + wind_speed + "km/h");
            weather.append("\n" + date);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
		
		return weather.toString();
	}
	
}
