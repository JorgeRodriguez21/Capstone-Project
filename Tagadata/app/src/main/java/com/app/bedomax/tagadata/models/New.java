package com.app.bedomax.tagadata.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jorge on 04/07/16.
 */
public class New implements Parcelable {

    private Long id;

    @SerializedName("tweet")
    private  String name;

    private String image;

    private String url;

    private boolean visible;

    protected New(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
        url = in.readString();
        visible = in.readByte() != 0;
    }

    public static final Creator<New> CREATOR = new Creator<New>() {
        @Override
        public New createFromParcel(Parcel in) {
            return new New(in);
        }

        @Override
        public New[] newArray(int size) {
            return new New[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(url);
        dest.writeByte((byte) (visible ? 1 : 0));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public boolean isVisible() {
        return visible;
    }
}
