package com.adith.xpense.tracker.models;

import android.media.Image;

import java.util.ArrayList;

public class ExpensesRecyler {
    public String name, amount, date;
    public int image;

    public ExpensesRecyler(String name, String amount, String date, int image) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.image = image;
    }
}