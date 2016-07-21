package com.app.bedomax.tagadata.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.app.bedomax.tagadata.database.MyDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;
import com.raizlabs.android.dbflow.structure.provider.BaseProviderModel;
import com.raizlabs.android.dbflow.structure.provider.ContentUtils;


/**
 * Created by Jorge on 04/07/16.
 */
@Table(database = MyDatabase.class)
@TableEndpoint(name = NewModel.NAME, contentProvider = MyDatabase.class)

public class NewModel extends BaseProviderModel <NewModel> implements Parcelable {

    public static final String NAME = "NewModel";


    @ContentUri(path = NAME, type = ContentUri.ContentType.VND_MULTIPLE + NAME)
    public static final Uri CONTENT_URI = ContentUtils.buildUriWithAuthority(MyDatabase.AUTHORITY);

    public NewModel() {
    }

    @Column
    @PrimaryKey
    private Long id;

    @Column
    @SerializedName("tweet")
    private  String name;

    @Column
    private String image;

    @Column
    private String url;

    @Column
    private boolean visible;

    protected NewModel(Parcel in) {
        id = in.readLong();
        name = in.readString();
        image = in.readString();
        url = in.readString();
        visible = in.readByte() != 0;
    }

    public static final Creator<NewModel> CREATOR = new Creator<NewModel>() {
        @Override
        public NewModel createFromParcel(Parcel in) {
            return new NewModel(in);
        }

        @Override
        public NewModel[] newArray(int size) {
            return new NewModel[size];
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewModel aNew = (NewModel) o;

        if (visible != aNew.visible) return false;
        if (id != null ? !id.equals(aNew.id) : aNew.id != null) return false;
        if (name != null ? !name.equals(aNew.name) : aNew.name != null) return false;
        if (image != null ? !image.equals(aNew.image) : aNew.image != null) return false;
        return url != null ? url.equals(aNew.url) : aNew.url == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (visible ? 1 : 0);
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public Uri getDeleteUri() {
        return MyDatabase.NewProviderModel.CONTENT_URI;
    }

    @Override
    public Uri getInsertUri() {
        return MyDatabase.NewProviderModel.CONTENT_URI;
    }

    @Override
    public Uri getUpdateUri() {
        return MyDatabase.NewProviderModel.CONTENT_URI;
    }

    @Override
    public Uri getQueryUri() {
        return MyDatabase.NewProviderModel.CONTENT_URI;
    }
}
