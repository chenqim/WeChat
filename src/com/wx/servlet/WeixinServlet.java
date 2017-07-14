package com.wx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.dom4j.DocumentException;

import com.wx.util.MessageUtil;
import com.wx.util.WeixinUtil;
import com.wx.util.CheckUtil;

/**
 * Servlet implementation class WeixinServlet
 */
@WebServlet("/WeixinServlet")
public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeixinServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		PrintWriter out = response.getWriter();
		// 微信对接验证
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.write(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			
			String message = null;
			// 收到的是文本消息
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.secondMenu());
				}else if("3".equals(content)){
					//message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.secondMenu());
					message = MessageUtil.initPictureTextMessage(toUserName, fromUserName);
				}else if("4".equals(content)){
					message = MessageUtil.initPictureTextMessage2(toUserName, fromUserName);
				}else if("5".equals(content)){
					message = MessageUtil.initPictureTextMessage3(toUserName, fromUserName);
				}else if("?".equals(content) || "？".equals(content)){
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.mainMenu());
				}else if(content.startsWith("翻译")){
					String word = content.replaceAll("^翻译", "").trim();
					if("".equals(word)){
						message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.secondMenu());
					}else{
						message = MessageUtil.initTextMessage(toUserName, fromUserName, WeixinUtil.translate(word));
					}
				}else if(content.endsWith("今天天气")){
					String word = content.replaceAll("今天天气", "").trim();
					if("".equals(word)){
						message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.firstMenu());
					}else{
						message = MessageUtil.initTextMessage(toUserName, fromUserName, WeixinUtil.weather(word, "0", "1"));
					}
				}else if(content.endsWith("明天天气")){
					String word = content.replaceAll("明天天气", "").trim();
					if("".equals(word)){
						message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.firstMenu());
					}else{
						message = MessageUtil.initTextMessage(toUserName, fromUserName, WeixinUtil.weather(word, "1", "2"));
					}
				}else if(content.endsWith("后天天气")){
					String word = content.replaceAll("后天天气", "").trim();
					if("".equals(word)){
						message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.firstMenu());
					}else{
						message = MessageUtil.initTextMessage(toUserName, fromUserName, WeixinUtil.weather(word, "2", "3"));
					}
				}else{
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.defaultMenu());
				}
			// 收到的是事件
			}else if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
				String eventType = map.get("Event");
				// 关注事件
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initTextMessage(toUserName, fromUserName, MessageUtil.mainMenu());
				}
				// 取消关注事件
				if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){
					
				}
			// 收到的是地址信息
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String wd = map.get("Location_X");
				String jd = map.get("Location_Y");
				message = MessageUtil.initTextMessage(toUserName, fromUserName, WeixinUtil.weather(wd + ":" + jd, "0", "1"));
			}
			
			System.out.println(message);
			
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
	}

}
