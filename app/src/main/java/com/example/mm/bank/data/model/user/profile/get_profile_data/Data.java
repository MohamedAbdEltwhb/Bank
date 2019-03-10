
package com.example.mm.bank.data.model.user.profile.get_profile_data;

import com.example.mm.bank.data.model.user.login.LoginClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    @Expose
    private LoginClient user;

    public LoginClient getUser() {
        return user;
    }

    public void setUser(LoginClient user) {
        this.user = user;
    }

}
