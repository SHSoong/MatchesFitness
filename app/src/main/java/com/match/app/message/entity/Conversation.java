package com.match.app.message.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*********
 * 会话表
 */
@DatabaseTable(tableName = "tb_conversation")
public class Conversation implements Parcelable{

    @DatabaseField(columnName = "id", id = true, generatedId = true)
    private int id;

    @DatabaseField(columnName = "him")
    private String him;

    @DatabaseField(columnName = "his_name")
    private String hisName;

    @DatabaseField(columnName = "his_logo_url")
    private String hisLogoUrl;

    protected Conversation(Parcel in) {
        id = in.readInt();
        him = in.readString();
        hisName = in.readString();
        hisLogoUrl = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHim() {
        return him;
    }

    public void setHim(String him) {
        this.him = him;
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

    public Conversation(int id, String him, String hisName, String hisLogoUrl) {
        this.id = id;
        this.him = him;
        this.hisName = hisName;
        this.hisLogoUrl = hisLogoUrl;
    }

    public Conversation() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(him);
        parcel.writeString(hisName);
        parcel.writeString(hisLogoUrl);
    }
}
