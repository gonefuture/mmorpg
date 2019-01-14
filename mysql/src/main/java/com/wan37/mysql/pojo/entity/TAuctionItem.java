package com.wan37.mysql.pojo.entity;

import java.io.Serializable;
import java.util.Date;

public class TAuctionItem implements Serializable {
    private Integer auctionId;

    private Integer thingInfoId;

    private Integer number;

    private Integer auctionPrice;

    private Integer auctionMode;

    private Date pushTime;

    private String bidders;

    private Long publisherId;

    private static final long serialVersionUID = 1L;

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getThingInfoId() {
        return thingInfoId;
    }

    public void setThingInfoId(Integer thingInfoId) {
        this.thingInfoId = thingInfoId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(Integer auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public Integer getAuctionMode() {
        return auctionMode;
    }

    public void setAuctionMode(Integer auctionMode) {
        this.auctionMode = auctionMode;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getBidders() {
        return bidders;
    }

    public void setBidders(String bidders) {
        this.bidders = bidders == null ? null : bidders.trim();
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", auctionId=").append(auctionId);
        sb.append(", thingInfoId=").append(thingInfoId);
        sb.append(", number=").append(number);
        sb.append(", auctionPrice=").append(auctionPrice);
        sb.append(", auctionMode=").append(auctionMode);
        sb.append(", pushTime=").append(pushTime);
        sb.append(", bidders=").append(bidders);
        sb.append(", publisherId=").append(publisherId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}