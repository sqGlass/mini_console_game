package com.consoleGame.parser;

public class Args {

    private String en_count;
    private String walls_count;
    private String size;
    private String profile;

    public Args() {
        en_count = "5";
        walls_count = "50";
        size = "30";
        profile = "production";
    }

    public void run()
    {
        System.out.println(en_count + " " + walls_count + " " + size);
    }

    public String getEn_count() {
        return en_count;
    }

    public String getWalls_count() {
        return walls_count;
    }

    public String getSizee() {
        return size;
    }

    public String getProfile() {
        return profile;
    }
}
