package com.jnd.digim;

/*
 * Methods, Getters-Setters and constructors for getting the order
 * */
public class OrderGet {
    String orderType;
    String order;
    String orderId;
    String transactionId;
    String urlLink;
    long timeStamp;
    boolean orderReviewed;

    public OrderGet(){}

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean getOrderReviewed() {
        return orderReviewed;
    }

    public void setOrderReviewed(boolean orderReviewed) {
        this.orderReviewed = orderReviewed;
    }

    public OrderGet(int i,String orderType, String order, String orderId, String transactionId, String urlLink, long timeStamp, boolean orderReviewed) {
        this.orderType = orderType;
        this.order = order;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.urlLink = urlLink;
        this.timeStamp = timeStamp;
        this.orderReviewed = orderReviewed;
    }
}
