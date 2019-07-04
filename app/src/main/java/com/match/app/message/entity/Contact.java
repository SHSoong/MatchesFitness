package com.match.app.message.entity;

import com.match.app.utils.PinYinUtils;

public class Contact extends Person {

    private String pinyin;
    private String headerWord;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getHeaderWord() {
        return headerWord;
    }

    public void setHeaderWord(String headerWord) {
        this.headerWord = headerWord;
    }

    public Contact(String name, String logUrl, Boolean isFriend, String pinyin, String headerWord) {
        super(name, logUrl, isFriend);
        this.pinyin = pinyin;
        this.headerWord = headerWord;
    }

    public Contact(String name, String logUrl) {
        super(name, logUrl);
        this.pinyin = PinYinUtils.getPinyin(name);
        headerWord = pinyin.substring(0, 1);
    }
}
