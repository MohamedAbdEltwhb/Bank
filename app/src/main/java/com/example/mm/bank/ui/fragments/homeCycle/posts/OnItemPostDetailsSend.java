package com.example.mm.bank.ui.fragments.homeCycle.posts;

public interface OnItemPostDetailsSend {

    String POST_TITLE = "title";

    String THUMBNAIL_FULL_PATH = "thumbnail";

    void onSentItemDetails(String title, String thumbnailFullPath);
}
