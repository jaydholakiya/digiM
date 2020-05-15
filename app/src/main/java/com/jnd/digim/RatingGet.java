package com.jnd.digim;

public class RatingGet {
    String complain;
    String starRating;
    long complainTime;

    public RatingGet(){}

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public long getComplainTime() {
        return complainTime;
    }

    public void setComplainTime(long complainTime) {
        this.complainTime = complainTime;
    }

    public RatingGet(String complain, String starRating, long complainTime) {
        this.complain = complain;
        this.starRating = starRating;
        this.complainTime = complainTime;
    }
}
