package com.home.recyclerviewapp.repository;

import java.util.SplittableRandom;

public class CardData {
    private String title;
    private String description;
    private int colors;
    private boolean like;

    public CardData(String title, String description, int color, boolean like) {
        this.title = title;
        this.description = description;
        this.colors = color;
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getColors() {
        return colors;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
