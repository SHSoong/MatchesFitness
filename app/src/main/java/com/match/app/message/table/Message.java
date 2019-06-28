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

    @DatabaseField(columnName = "conversation_id")
    private String conversationId; // 属于哪个会话

    @DatabaseField
    private String speaker; // 谁说的

    @DatabaseField(columnName = "speaker_name")
    private String speakerName;

    @DatabaseField
    private String receiver;

    @DatabaseField(columnName = "receiver_name")
    private String receiverName;

    @DatabaseField(columnName = "content")
    private String content;

    @DatabaseField(columnName = "time")
    private int time;

    @DatabaseField
    private String hisLogoUrl;

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

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public Message() {
    }

    public Message(String conversationId, String speaker, String speakerName, String receiver, String receiverName, String content, int time, String hisLogoUrl, int status) {
        this.conversationId = conversationId;
        this.speaker = speaker;
        this.speakerName = speakerName;
        this.receiver = receiver;
        this.receiverName = receiverName;
        this.content = content;
        this.time = time;
        this.hisLogoUrl = hisLogoUrl;
        this.status = status;
    }
}
