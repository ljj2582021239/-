package com.example.expriment7;

public class Contacts {

    public String displayname;
    public String number;
    public String sex;

    @Override
    public String toString() {
        return "Contacts{" +
                "displayname='" + displayname + '\'' +
                ", number='" + number + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
