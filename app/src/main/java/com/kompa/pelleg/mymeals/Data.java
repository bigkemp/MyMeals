package com.kompa.pelleg.mymeals;

/**
 * Created by pelleg on 3/28/2018.
 */

public class Data {
    private String date;
    private String time;
    private String image;
    private String comments;
    private String calories;

    public Data(String date,String time,String image,String comments,String calories) {
        this.setDate(date);
        this.setTime(time);
        this.setImage(image);
        this.setComments(comments);
        this.setCalories(calories);
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String image ) {
        this.image = image;
    }

    public String getComments() {
        return comments;
    }

    public void setComments( String comments ) {
        this.comments = comments;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories( String calories ) {
        this.calories = calories;
    }
}
