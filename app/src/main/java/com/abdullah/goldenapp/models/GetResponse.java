package com.abdullah.goldenapp.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class GetResponse implements Serializable {

    @SerializedName("root")
    @Expose
    private Root root;


    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

}

