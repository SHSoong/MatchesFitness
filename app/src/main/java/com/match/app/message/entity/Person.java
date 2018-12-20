package com.match.app.message.entity;

import android.os.Parcel;
import android.os.Parcelable;

/*******
 * 健身者信息
 */
public class Person implements Parcelable {
    private String name;
    private String logUrl;
    private boolean isFriend = false; // 是否好友

    protected Person(Parcel in) {
        name = in.readString();
        logUrl = in.readString();
        isFriend = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(logUrl);
        dest.writeByte((byte) (isFriend ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public Person() {
    }


    public Person(String name, String logUrl) {
        this.name = name;
        this.logUrl = logUrl;
    }

    public Person(String name, String logUrl, Boolean isFriend) {
        this.name = name;
        this.logUrl = logUrl;
        this.isFriend = isFriend;
    }
}
