
package com.example.mm.bank.data.model.posts_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsDetails {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PostsDetailsData postsDetailsData;

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

    public PostsDetailsData getPostsDetailsData() {
        return postsDetailsData;
    }

    public void setPostsDetailsData(PostsDetailsData postsDetailsData) {
        this.postsDetailsData = postsDetailsData;
    }
}
