package com.match.app.message.table;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*********
 * 会话表
 */
@DatabaseTable(tableName = "tb_conversation")
public class Conversation implements Parcelable {

    @DatabaseField(columnName = "conversation_id", generatedId = true)
    private String conversationId;

    @DatabaseField(columnName = "user_id")
    private int userId;

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
        userId = in.readInt();
        conversationId = in.readString();
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
        if (var1 != null && var2 != null) {
            if (!TextUtils.isEmpty(var2.getConversationId())) {
                if (var1.getConversationId().equals(var2.getConversationId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public long getLastTime() {
        return lastTime;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(conversationId);
        parcel.writeString(hisName);
        parcel.writeString(hisLogoUrl);
        parcel.writeLong(lastTime);
        parcel.writeString(lastMessage);
        parcel.writeInt(status);
    }
}
