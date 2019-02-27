
package com.example.mm.bank.data.model.login;

import com.example.mm.bank.data.model.regester.RegisterData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RegisterData loginData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RegisterData getLoginData() {
        return loginData;
    }

    public void setLoginData(RegisterData loginData) {
        this.loginData = loginData;
    }

}
