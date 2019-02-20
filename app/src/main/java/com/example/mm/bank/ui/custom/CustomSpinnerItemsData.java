package com.example.mm.bank.ui.custom;

import java.util.ArrayList;

public class CustomSpinnerItemsData {

    private static final ArrayList<CustomSpinnerItem> mCustomListBloodType = new ArrayList<CustomSpinnerItem>(){{
        add(new CustomSpinnerItem("A+"));
        add(new CustomSpinnerItem("A-"));
        add(new CustomSpinnerItem("B+"));
        add(new CustomSpinnerItem("B-"));
        add(new CustomSpinnerItem("O+"));
        add(new CustomSpinnerItem("O-"));
        add(new CustomSpinnerItem("AB+"));
        add(new CustomSpinnerItem("AB-"));
    }};

    private static final ArrayList<CustomSpinnerItem> mCustomListCities = new ArrayList<CustomSpinnerItem>(){{
        add(new CustomSpinnerItem("Alexandria"));
        add(new CustomSpinnerItem("Aswan"));
        add(new CustomSpinnerItem("Asyut"));
        add(new CustomSpinnerItem("Beheira"));
        add(new CustomSpinnerItem("Beni Suef"));
        add(new CustomSpinnerItem("Cairo"));
        add(new CustomSpinnerItem("Dakahlia"));
        add(new CustomSpinnerItem("Damietta"));
        add(new CustomSpinnerItem("Faiyum"));
        add(new CustomSpinnerItem("Gharbia"));
        add(new CustomSpinnerItem("Giza"));
        add(new CustomSpinnerItem("Ismailia"));
        add(new CustomSpinnerItem("Kafr El Sheikh"));
        add(new CustomSpinnerItem("Luxor"));
        add(new CustomSpinnerItem("Matruh"));
        add(new CustomSpinnerItem("Minya"));
        add(new CustomSpinnerItem("Monufia"));
        add(new CustomSpinnerItem("New Valley"));
        add(new CustomSpinnerItem("North Sinai"));
        add(new CustomSpinnerItem("Port Said"));
        add(new CustomSpinnerItem("Qalyubia"));
        add(new CustomSpinnerItem("Red Sea"));
        add(new CustomSpinnerItem("Sharqia"));
        add(new CustomSpinnerItem("Sohag"));
        add(new CustomSpinnerItem("South Sinai"));
        add(new CustomSpinnerItem("Suez"));
    }};



    public static ArrayList<CustomSpinnerItem> getmCustomListBloodType() {
        return mCustomListBloodType;
    }

    public static ArrayList<CustomSpinnerItem> getmCustomListCities() {
        return mCustomListCities;
    }
}
