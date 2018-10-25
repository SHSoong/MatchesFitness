package com.match.app.ui.im;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*******
 * 消息表
 */
@DatabaseTable(tableName = "tb_message")
public class Message {
    @DatabaseField(columnName = "id", id = true, generatedId = true)
    private int id;

    @DatabaseField(columnName = "conversation_id")
    private int conversation; // 属于哪个会话

    @DatabaseField(columnName = "speaker")
    private String speaker; // 谁说的

    @DatabaseField(columnName = "speaker_name")
    private String speakerName;

    @DatabaseField(columnName = "content")
    private String content;

    @DatabaseField(columnName = "time")
    private int time;

    @DatabaseField(columnName = "status")
    private int status; // 状态 0，未读，1已读

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversation() {
        return conversation;
    }

    public void setConversation(int conversation) {
        this.conversation = conversation;
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

    public Message() {
    }

    public Message(int id, int conversation, String speaker, String speakerName, String content, int time, int status) {
        this.id = id;
        this.conversation = conversation;
        this.speaker = speaker;
        this.speakerName = speakerName;
        this.content = content;
        this.time = time;
        this.status = status;
    }
}
