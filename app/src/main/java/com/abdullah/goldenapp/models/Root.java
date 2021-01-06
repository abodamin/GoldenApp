package com.abdullah.goldenapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Root implements Serializable {

    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    @SerializedName("metal")
    @Expose
    private String metal;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("exchange")
    @Expose
    private String exchange;

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("prev_close_price")
    @Expose
    private Double prevClosePrice;

    @SerializedName("open_price")
    @Expose
    private Double openPrice;

    @SerializedName("low_price")
    @Expose
    private Double lowPrice;

    @SerializedName("high_price")
    @Expose
    private Double highPrice;

    @SerializedName("open_time")
    @Expose
    private Integer openTime;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("ch")
    @Expose
    private Double ch;

    @SerializedName("chp")
    @Expose
    private Double chp;

    @SerializedName("ask")
    @Expose
    private Double ask;

    @SerializedName("bid")
    @Expose
    private Double bid;
    private final static long serialVersionUID = 6770729538710759390L;

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrevClosePrice() {
        return prevClosePrice;
    }

    public void setPrevClosePrice(Double prevClosePrice) {
        this.prevClosePrice = prevClosePrice;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Integer openTime) {
        this.openTime = openTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCh() {
        return ch;
    }

    public void setCh(Double ch) {
        this.ch = ch;
    }

    public Double getChp() {
        return chp;
    }

    public void setChp(Double chp) {
        this.chp = chp;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

}