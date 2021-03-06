package com.example.taquio.trasearch.Samok;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edward on 11/03/2018.
 */

class Junkshop implements Parcelable {

    String bsnBusinessName, bsnEmail, bsnLocation, userType,
            bsnMobile, bsnPhone, image, imagePermit, image_thumb,
            userId, deviceToken;
    Boolean isVerify;
    public Junkshop(){}

    protected Junkshop(Parcel in) {
        bsnBusinessName = in.readString();
        bsnEmail = in.readString();
        bsnLocation = in.readString();
        userType = in.readString();
        bsnMobile = in.readString();
        bsnPhone = in.readString();
        image = in.readString();
        imagePermit = in.readString();
        image_thumb = in.readString();
        userId = in.readString();
        deviceToken = in.readString();
        byte tmpIsVerify = in.readByte();
        isVerify = tmpIsVerify == 0 ? null : tmpIsVerify == 1;
    }

    public static final Creator<Junkshop> CREATOR = new Creator<Junkshop>() {
        @Override
        public Junkshop createFromParcel(Parcel in) {
            return new Junkshop(in);
        }

        @Override
        public Junkshop[] newArray(int size) {
            return new Junkshop[size];
        }
    };

    @Override
    public String toString() {
        return "Junkshop{" +
                "bsnBusinessName='" + bsnBusinessName + '\'' +
                ", bsnEmail='" + bsnEmail + '\'' +
                ", bsnLocation='" + bsnLocation + '\'' +
                ", userType='" + userType + '\'' +
                ", bsnMobile='" + bsnMobile + '\'' +
                ", bsnPhone='" + bsnPhone + '\'' +
                ", image='" + image + '\'' +
                ", imagePermit='" + imagePermit + '\'' +
                ", image_thumb='" + image_thumb + '\'' +
                ", userId='" + userId + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", isVerify=" + isVerify +
                '}';
    }

    public String getBsnBusinessName() {
        return bsnBusinessName;
    }

    public void setBsnBusinessName(String bsnBusinessName) {
        this.bsnBusinessName = bsnBusinessName;
    }

    public String getBsnEmail() {
        return bsnEmail;
    }

    public void setBsnEmail(String bsnEmail) {
        this.bsnEmail = bsnEmail;
    }

    public String getBsnLocation() {
        return bsnLocation;
    }

    public void setBsnLocation(String bsnLocation) {
        this.bsnLocation = bsnLocation;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBsnMobile() {
        return bsnMobile;
    }

    public void setBsnMobile(String bsnMobile) {
        this.bsnMobile = bsnMobile;
    }

    public String getBsnPhone() {
        return bsnPhone;
    }

    public void setBsnPhone(String bsnPhone) {
        this.bsnPhone = bsnPhone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePermit() {
        return imagePermit;
    }

    public void setImagePermit(String imagePermit) {
        this.imagePermit = imagePermit;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Boolean getVerify() {
        return isVerify;
    }

    public void setVerify(Boolean verify) {
        isVerify = verify;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bsnBusinessName);
        parcel.writeString(bsnEmail);
        parcel.writeString(bsnLocation);
        parcel.writeString(userType);
        parcel.writeString(bsnMobile);
        parcel.writeString(bsnPhone);
        parcel.writeString(image);
        parcel.writeString(imagePermit);
        parcel.writeString(image_thumb);
        parcel.writeString(userId);
        parcel.writeString(deviceToken);
        parcel.writeByte((byte) (isVerify == null ? 0 : isVerify ? 1 : 2));
    }
}
