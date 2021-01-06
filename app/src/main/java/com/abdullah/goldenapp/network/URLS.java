package com.abdullah.goldenapp.network;

public class URLS {

    public static final String BASE_URL = "https://www.goldapi.io/api/";
    public static  final String KEY = "goldapi-108hkukh810uep-io";

    private static final String goldCode = "XAU";
    private static final String silverCode = "XAG";
    private static final String currency = "USD";


    public String goldPriceURL(){
        return BASE_URL+goldCode+"/"+currency;
    }

    public String silverPriceURL(){
        return BASE_URL+silverCode+"/"+currency;
    }

    //'headers': {
    //    'x-access-token': 'goldapi-108hkukh810uep-io', //goldapi-108hkukh810uep-io
    //    'Content-Type': 'application/json'
    //  }

}
