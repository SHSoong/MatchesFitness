package com.match.app.message.table;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*********
 * 会话表
 */
@DatabaseTable(tableName = "tb_conversation")
public class Conversation implements Parcelable {

    @DatabaseField(columnName = "conversation_id", generatedId = true)
    private int conversationId;

    @DatabaseField(columnName = "his_token")
    private String hisToken; // 谁的对话

    @DatabaseField(columnName = "his_name")
    private String hisName;

    @DatabaseField(columnName = "his_logo_url")
    private String hisLogoUrl;

    @DatabaseField(columnName = "last_time")
    private long lastTime;

    @DatabaseField(columnName = "last_message")
    private String lastMessage;

    @DatabaseField
    private int status;

    public Conversation() {
    }

    protected Conversation(Parcel in) {
        conversationId = in.readInt();
        hisToken = in.readString();
        hisName = in.readString();
        hisLogoUrl = in.readString();
        lastTime = in.readInt();
        lastMessage = in.readString();
        status = in.readInt();
    }

    public static final Creator<Conversation> CREATOR = new Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel in) {
            return new Conversation(in);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };

    public static boolean equals(Conversation var1, Conversation var2) {
        return var1 != null && var2 != null && var1.getConversationId() == var2.getConversationId();
    }

    public long getLastTime() {
        return lastTime;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHisName() {
        return hisName;
    }

    public void setHisName(String hisName) {
        this.hisName = hisName;
    }

    public String getHisLogoUrl() {
        return hisLogoUrl;
    }

    public void setHisLogoUrl(String hisLogoUrl) {
        this.hisLogoUrl = hisLogoUrl;
    }

    public String getHisToken() {
        return hisToken;
    }

    public void setHisToken(String hisToken) {
        this.hisToken = hisToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(conversationId);
        parcel.writeString(hisToken);
        parcel.writeString(hisName);
        parcel.writeString(hisLogoUrl);
        parcel.writeLong(lastTime);
        parcel.writeString(lastMessage);
        parcel.writeInt(status);
    }
}
