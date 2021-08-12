package com.mydietmeal.mydietmealupdated;

public class confirmSubscriptionHelperClass {
    String packageName, mealPrice, lunchPlace, lunchTime, totalPayable, transactionID, status;
    public confirmSubscriptionHelperClass(){

    }

    public confirmSubscriptionHelperClass(String packageName, String mealPrice, String lunchPlace,
                                          String lunchTime, String totalPayable,
                                          String transactionID, String status) {
        this.packageName = packageName;
        this.mealPrice = mealPrice;
        this.lunchPlace = lunchPlace;
        this.lunchTime = lunchTime;
        this.totalPayable = totalPayable;
        this.transactionID = transactionID;
        this.status = status;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getLunchPlace() {
        return lunchPlace;
    }

    public void setLunchPlace(String lunchPlace) {
        this.lunchPlace = lunchPlace;
    }

    public String getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public String getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(String totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
