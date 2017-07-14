package com.wx.test;

import java.io.IOException;

import org.apache.http.ParseException;

import com.wx.po.AccessToken;
import com.wx.util.WeixinUtil;

public class WeixinTest {

	public static void main(String[] args) {
		try {
			//AccessToken token = WeixinUtil.getAccessToken();
			//System.out.println("access_token:"+ token.getAccess_token());
			//System.out.println("expires_in:"+ token.getExpires_in());
			//System.out.println(WeixinUtil.translate("足球"));
			//System.out.println(WeixinUtil.weather("ningbo"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}

}
