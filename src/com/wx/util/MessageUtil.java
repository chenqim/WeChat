package com.wx.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.wx.po.PictureText;
import com.wx.po.PictureTextMessage;
import com.wx.po.TextMessage;

public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";
	
	/**
	 * xml转map
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element e : list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 * 文本消息转xml
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 初始化回复的文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initTextMessage(String toUserName, String fromUserName, String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		return MessageUtil.textMessageToXml(textMessage);
	}
	
	/**
	 * 主菜单
	 * @return
	 */
	public static String mainMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
		sb.append("回复“1”查看天气功能说明\n");
		sb.append("回复“2”查看翻译功能说明\n");
		sb.append("回复“3”获取操作指南\n");
		sb.append("回复“4”获取Flappy Bird小游戏\n");
		sb.append("回复“5”获取五子棋小游戏\n\n");
		sb.append("回复“？”调出此菜单\n");
		sb.append("更多功能敬请期待");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("天气功能示例：\n\n");
		sb.append("武汉今天天气\n");
		sb.append("武汉明天天气\n");
		sb.append("武汉后天天气\n");
		sb.append("发送当前位置信息快捷获取当天天气信息");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("翻译功能示例：\n\n");
		sb.append("翻译足球\n");
		sb.append("翻译中国足球\n");
		sb.append("翻译football");
		return sb.toString();
	}
	
	public static String defaultMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("爱你哟😘小宝贝儿，请按提示操作~\n");
		sb.append("请回复“？”获取菜单");
		return sb.toString();
	}
	
	/**
	 * 图文消息消息转xml
	 * @param textMessage
	 * @return
	 */
	public static String pictureTextMessageToXml(PictureTextMessage pictureTextMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", pictureTextMessage.getClass());
		xstream.alias("item", new PictureText().getClass());
		return xstream.toXML(pictureTextMessage);
	}
	
	/**
	 * 组装图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initPictureTextMessage(String toUserName, String fromUserName){
		String message = null;
		List<PictureText> pictureTextList = new ArrayList<PictureText>();
		
		PictureTextMessage pictureTextMessage = new PictureTextMessage();
		PictureText pictureText = new PictureText();
		
		pictureText.setTitle("微信小功能操作指南");
		pictureText.setDescription("欢迎来到我的公众号！");
		pictureText.setPicUrl("http://sunnychen.duapp.com/WeChatPublicAccount/image/datiangou.jpg");
		pictureText.setUrl("http://sunnychen.duapp.com/WeChatPublicAccount/page/introduce.jsp");
		
		pictureTextList.add(pictureText);
		
		pictureTextMessage.setToUserName(fromUserName);
		pictureTextMessage.setFromUserName(toUserName);
		pictureTextMessage.setCreateTime(new Date().getTime());
		pictureTextMessage.setMsgType(MESSAGE_NEWS);
		pictureTextMessage.setArticles(pictureTextList);
		pictureTextMessage.setArticleCount(pictureTextList.size());
		
		message = pictureTextMessageToXml(pictureTextMessage);
		return message;
	}
	
	public static String initPictureTextMessage2(String toUserName, String fromUserName){
		String message = null;
		List<PictureText> pictureTextList = new ArrayList<PictureText>();
		
		PictureTextMessage pictureTextMessage = new PictureTextMessage();
		PictureText pictureText = new PictureText();
		
		pictureText.setTitle("Flappy Bird小游戏");
		pictureText.setDescription("一款会上瘾的小游戏！");
		pictureText.setPicUrl("http://sunnychen.duapp.com/WeChatPublicAccount/image/datiangou.jpg");
		pictureText.setUrl("http://sunnychen.duapp.com/WeChatPublicAccount/game/bird/index.html");
		
		pictureTextList.add(pictureText);
		
		pictureTextMessage.setToUserName(fromUserName);
		pictureTextMessage.setFromUserName(toUserName);
		pictureTextMessage.setCreateTime(new Date().getTime());
		pictureTextMessage.setMsgType(MESSAGE_NEWS);
		pictureTextMessage.setArticles(pictureTextList);
		pictureTextMessage.setArticleCount(pictureTextList.size());
		
		message = pictureTextMessageToXml(pictureTextMessage);
		return message;
	}
	
	public static String initPictureTextMessage3(String toUserName, String fromUserName){
		String message = null;
		List<PictureText> pictureTextList = new ArrayList<PictureText>();
		
		PictureTextMessage pictureTextMessage = new PictureTextMessage();
		PictureText pictureText = new PictureText();
		
		pictureText.setTitle("五子棋小游戏");
		pictureText.setDescription("考验你的智力！");
		pictureText.setPicUrl("http://sunnychen.duapp.com/WeChatPublicAccount/image/datiangou.jpg");
		pictureText.setUrl("http://sunnychen.duapp.com/WeChatPublicAccount/game/chess5/index.html");
		
		pictureTextList.add(pictureText);
		
		pictureTextMessage.setToUserName(fromUserName);
		pictureTextMessage.setFromUserName(toUserName);
		pictureTextMessage.setCreateTime(new Date().getTime());
		pictureTextMessage.setMsgType(MESSAGE_NEWS);
		pictureTextMessage.setArticles(pictureTextList);
		pictureTextMessage.setArticleCount(pictureTextList.size());
		
		message = pictureTextMessageToXml(pictureTextMessage);
		return message;
	}
	
}
