
package com.example.mm.bank.data.model.user.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private LoginClient client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public LoginClient getClient() {
        return client;
    }

    public void setClient(LoginClient client) {
        this.client = client;
    }

}
