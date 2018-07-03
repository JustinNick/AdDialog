package com.cxz.adlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 广告的实体类
 */
public class AdInfo implements Parcelable {

    private String adId;
    private String title;
    private String url;
    private String activityImg;
    private int activityId = -1;

    public AdInfo() {
    }

    protected AdInfo(Parcel in) {
        adId = in.readString();
        title = in.readString();
        url = in.readString();
        activityImg = in.readString();
        activityId = in.readInt();
    }

    public static final Creator<AdInfo> CREATOR = new Creator<AdInfo>() {
        @Override
        public AdInfo createFromParcel(Parcel in) {
            return new AdInfo(in);
        }

        @Override
        public AdInfo[] newArray(int size) {
            return new AdInfo[size];
        }
    };

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adId);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(activityImg);
        dest.writeInt(activityId);
    }
}
