package ADBproject.LookYourBookUp.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    @NotNull
    private String userId;
    @NotNull
    private String bibNum;
    @NotNull
    private String reviewHeading;
    @NotNull
    private int reviewRating;
    @NotNull
    private String reviewDescription;
    @NotNull
    private String recommend;

    public Review () {

    }

    public Review(int reviewId, String userId, String bibNum, String reviewHeading, int reviewRating, String reviewDescription, String recommend) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.bibNum = bibNum;
        this.reviewHeading = reviewHeading;
        this.reviewRating = reviewRating;
        this.reviewDescription = reviewDescription;
        this.recommend = recommend;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBibNum() {
        return bibNum;
    }

    public void setBibNum(String bibNum) {
        this.bibNum = bibNum;
    }

    public String getReviewHeading() {
        return reviewHeading;
    }

    public void setReviewHeading(String reviewHeading) {
        this.reviewHeading = reviewHeading;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
