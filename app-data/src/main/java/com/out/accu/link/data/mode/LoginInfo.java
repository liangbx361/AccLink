package com.out.accu.link.data.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/30
 */

public class LoginInfo implements Parcelable {

    public String user;

    public String password;

    public String server;

    public int port;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user);
        dest.writeString(this.password);
        dest.writeString(this.server);
        dest.writeInt(this.port);
    }

    public LoginInfo() {
    }

    protected LoginInfo(Parcel in) {
        this.user = in.readString();
        this.password = in.readString();
        this.server = in.readString();
        this.port = in.readInt();
    }

    public static final Parcelable.Creator<LoginInfo> CREATOR = new Parcelable.Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
