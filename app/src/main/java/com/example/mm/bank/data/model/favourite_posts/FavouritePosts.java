
package com.example.mm.bank.data.model.favourite_posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouritePosts {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FavouriteData favouriteData;

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

    public FavouriteData getFavouriteData() {
        return favouriteData;
    }

    public void setFavouriteData(FavouriteData favouriteData) {
        this.favouriteData = favouriteData;
    }

}
