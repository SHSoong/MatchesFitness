package com.matches.fitness.ui.info;

/*******
 * 健身者信息
 */
public class Person {
    private String name;
    private String loginUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public Person(String name, String loginUrl) {
        this.name = name;
        this.loginUrl = loginUrl;
    }
}
