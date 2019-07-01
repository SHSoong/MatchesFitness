package com.match.app.message.entity;

import com.match.app.MyApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*****
 * Create by John on 2018/8/5
 * 保存登录信息
 */
public class User implements Serializable {

    private static final long serialVersionUID = 6927152376472605640L;
    private static final String TAG = "Account";
    private static User instance;

    private String token = "";//user id
    private String loginName ="";//登录名，同mobile(废弃)
    private String name = "";    // 昵称
    private String birthday = ""; //  出生日期
    private Integer sex = 0;        //性别 0:男 1:女
    private Integer hasExp = 0;  //是否有经验1有， 0或者null其它无
    private String logo = "";        //头像
    private String lastLoginDate = "";// 更新时间，上次登录时间
    private Integer hasInfo = 0;// 1为已经完善，
    private boolean isLogin = false;

    private User() {
    }

    public static User getInstance() {
        if (instance == null) {
            String path = MyApp.app.getCacheDir().getAbsolutePath() + "/" + TAG;
            Object o = restoreObject(path);
            if (null == o) {
                o = new User();
                saveObject(path, o);
            }
            instance = (User) o;
        }
        return instance;
    }

    /******
     * 重置
     */
    public void reset() {
        token = "";
        loginName = "";
        name = "";
        birthday = "";
        sex = 0;
        hasExp = 0;
        logo = "";
        lastLoginDate = "";
        hasInfo = 0;
        isLogin = false;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getHasExp() {
        return hasExp;
    }

    public void setHasExp(Integer hasExp) {
        this.hasExp = hasExp;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getHasInfo() {
        return hasInfo;
    }

    public void setHasInfo(Integer hasInfo) {
        this.hasInfo = hasInfo;
    }

    /*****
     * 保存
     */
    public void save() {
        String path = MyApp.app.getCacheDir().getAbsolutePath() + "/" + TAG;
        saveObject(path, this);
    }


    /**
     * 序列化 保存
     *
     * @param path
     * @param saveObject
     */


    public static final void saveObject(String path, Object saveObject) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File f = new File(path);
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(saveObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*****
     * 反序列化 取出
     * @param path
     * @return
     */
    private static Object restoreObject(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object o = null;
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            o = ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {

                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return o;
    }


}
