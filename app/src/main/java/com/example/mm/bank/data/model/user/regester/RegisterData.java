
package com.example.mm.bank.data.model.user.regester;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private RegisterClient registerClient;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public RegisterClient getRegisterClient() {
        return registerClient;
    }

    public void setRegisterClient(RegisterClient registerClient) {
        this.registerClient = registerClient;
    }

}
