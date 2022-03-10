package com.home.recyclerviewapp.repository;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


//Добавили парселизацию и Date
public class NoteData implements Parcelable {
    private String title;
    private String description;
    private int colors;
    private boolean like;
    private Date date;

    protected NoteData(Parcel in) {
        title = in.readString();
        description = in.readString();
        colors = in.readInt();
        like = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(colors);
        dest.writeByte((byte) (like ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public NoteData(String title, String description, int color, boolean like, Date date) {
        this.title = title;
        this.description = description;
        this.colors = color;
        this.like = like;
        this.date = date;
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

    public void setDate(Date date) {
        this.date = date;
    }
}
