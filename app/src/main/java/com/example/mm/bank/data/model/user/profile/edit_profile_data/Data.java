
package com.example.mm.bank.data.model.user.profile.edit_profile_data;

import com.example.mm.bank.data.model.user.regester.RegisterClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    @Expose
    private RegisterClient user;

    public RegisterClient getUser() {
        return user;
    }

    public void setUser(RegisterClient user) {
        this.user = user;
    }

}
