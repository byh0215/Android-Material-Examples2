package com.saulmm.material;


/**
 * Created by Administrator on 2016/11/1.
 */
public class User {
    private String account;
    private String password;
    private String img;
    private String name;
    private String summary;
    private String position;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String ac, String pa,String na,String im,String su,String po) {
        account=ac;
        password=pa;
        name=na;
        img=im;
        summary=su;
        position=po;
    }

}