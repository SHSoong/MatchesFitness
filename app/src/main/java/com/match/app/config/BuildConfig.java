package com.match.app.config;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public interface BuildConfig {
    /**
     * 目录
     */
    public String dir = "matches/";

    //请求的 url
    String baseUrl = "http://match.boring-site.com/";
    //友盟
    String umengAppKey = "5c2045edb465f568cf00002c";
    String umengMessageSecret = "a2ea5a019da1a72ca6cb0117d8080019";
    String umengAppMasterSecret = "e7j2huuruowdgt8zxcua7ktltxlsu7i0";

    int PUSH_NOTIFICATION_ID = (0x001);
    String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
}
