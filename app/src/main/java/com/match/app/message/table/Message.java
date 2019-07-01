package com.match.app.message.table;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*******
 * 消息表
 *
 *
 */
@DatabaseTable(tableName = "tb_message")
public class Message {

    @DatabaseField(columnName = "message_id", generatedId = true)
    private int messageId;

    @DatabaseField(columnName = "conversation_token")
    private String conversationToken; // 属于哪个会话

    @DatabaseField(columnName = "send_token")
    private String sendToken;

    @DatabaseField(columnName = "speaker_name")
    private String speakerName;

    @DatabaseField(columnName = "receiver_token")
    private String receiverToken;

    @DatabaseField(columnName = "receiver_name")
    private String receiverName;

    @DatabaseField(columnName = "content")
    private String content;

    @DatabaseField(columnName = "time")
    private long time;

    @DatabaseField
    private String hisLogoUrl;

    @DatabaseField
    private String messageType;

    @DatabaseField(columnName = "status")
    private int status; // 状态  0 发送中，1发送成功，2 发送失败 ，3 未读，4 已读 -1 删除

    public static Message jsonToObject(String msg) {
        try {
            return new Gson().fromJson(msg, Message.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getConversationToken() {
        return conversationToken;
    }

    public void setConversationToken(String conversationToken) {
        this.conversationToken = conversationToken;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getSendToken() {
        return sendToken;
    }

    public void setSendToken(String sendToken) {
        this.sendToken = sendToken;
    }

    public String getReceiverToken() {
        return receiverToken;
    }

    public void setReceiverToken(String receiverToken) {
        this.receiverToken = receiverToken;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getHisLogoUrl() {
        return hisLogoUrl;
    }

    public void setHisLogoUrl(String hisLogoUrl) {
        this.hisLogoUrl = hisLogoUrl;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Message() {
    }

    public Message(String conversationToken, String sendToken, String speakerName, String receiverToken, String receiverName, String content, int time, String hisLogoUrl, int status) {
        this.conversationToken = conversationToken;
        this.sendToken = sendToken;
        this.speakerName = speakerName;
        this.receiverToken = receiverToken;
        this.receiverName = receiverName;
        this.content = content;
        this.time = time;
        this.hisLogoUrl = hisLogoUrl;
        this.status = status;
    }
}
