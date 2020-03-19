package ru.kaz.film.script.setting;

public class Setting {
    private int year;
    private String season;
    private String city;

    public int getYear() {
        return year;
    }

    public String getCity() {
        return city;
    }

    public String getSeason() {
        return season;
    }

    public Setting(int year, String season, String city){
        this.year = year;
        this.season = season;
        this.city = city;
    }
}
