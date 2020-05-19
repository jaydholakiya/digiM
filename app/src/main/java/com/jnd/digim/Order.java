package com.jnd.digim;

/*
 * Methods, Getters-Setters and constructors for sending the order
 * */
public class Order {
    String order;
    String orderType;
    String orderId;
    String email;
    String orderDateTime;
    String urlLink;
    String transactionId;
    String contactNo;
    Long timeStamp;
    Boolean orderReviewed;

    public Order(String order, String orderType, String orderId, String email, String orderDateTime, String urlLink, String transactionId, String contactNo, Long timeStamp, Boolean orderReviewed) {
        this.order = order;
        this.orderType = orderType;
        this.orderId = orderId;
        this.email = email;
        this.orderDateTime = orderDateTime;
        this.urlLink = urlLink;
        this.transactionId = transactionId;
        this.contactNo = contactNo;
        this.timeStamp = timeStamp;
        this.orderReviewed = orderReviewed;
    }

    public String getOrder() {
        return order;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public Boolean getOrderReviewed() {
        return orderReviewed;
    }

    public void Order(){}

}
