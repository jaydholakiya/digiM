package com.jnd.digim;

public class Rating {

    String complaineeName;
    String email;
    String complain;
    Long complainTime;
    String complainId;
    Float starRating;

    public Rating(String complaineeName, String email, String complain, Long complainTime, String complainId, Float starRating) {
        this.complaineeName = complaineeName;
        this.email = email;
        this.complain = complain;
        this.complainTime = complainTime;
        this.complainId = complainId;
        this.starRating = starRating;
    }

    public void Rating() {}

    public String getComplaineeName() {
        return complaineeName;
    }

    public void setComplaineeName(String complaineeName) {
        this.complaineeName = complaineeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public Long getComplainTime() {
        return complainTime;
    }

    public void setComplainTime(Long complainTime) {
        this.complainTime = complainTime;
    }

    public String getComplainId() {
        return complainId;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public Float getStarRating() {
        return starRating;
    }

    public void setStarRating(Float starRating) {
        this.starRating = starRating;
    }
}