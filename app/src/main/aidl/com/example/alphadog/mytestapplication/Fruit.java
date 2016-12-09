package com.example.alphadog.mytestapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alpha Dog on 2016/12/8.
 */

public class Fruit implements Parcelable{

    private String name;
    private long size;

    public Fruit(String name,long size) {
        this.name=name;
        this.size=size;
    }
    @Override
    public String toString() {
        return "吃"+size +"个"+ name ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.size);
    }

    protected Fruit(Parcel in) {
        this.name = in.readString();
        this.size = in.readLong();
    }

    public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
        @Override
        public Fruit createFromParcel(Parcel source) {
            return new Fruit(source);
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };
}
