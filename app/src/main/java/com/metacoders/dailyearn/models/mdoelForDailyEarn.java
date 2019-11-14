/*
 * Copyright (c) $today.year.This Code is written by Gamkiller .
 */

package com.metacoders.dailyearn.models;

public class mdoelForDailyEarn {
    String day , percent;

    public mdoelForDailyEarn() {
    }

    public mdoelForDailyEarn(String day, String precent) {
        this.day = day;
        this.percent = precent;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
