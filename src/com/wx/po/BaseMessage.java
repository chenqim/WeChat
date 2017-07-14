package com.wx.po;
/**
 * ��Ϣ����
 * @author Stephen
 *
 */
public class BaseMessage {
	//���շ�΢�ź�
	private String ToUserName;
	//���ͷ�΢�ź�
	private String FromUserName;
	//����ʱ��
	private long CreateTime;
	//��Ϣ����
	private String MsgType;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
