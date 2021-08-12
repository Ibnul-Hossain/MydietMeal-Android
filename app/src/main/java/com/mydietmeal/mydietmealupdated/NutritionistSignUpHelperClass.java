package com.mydietmeal.mydietmealupdated;

public class NutritionistSignUpHelperClass {
    private String nutritionistName, nutritionistEmail, nutritionistGraduation,
            nutritionistPhone, nutritionistPassword;

    public NutritionistSignUpHelperClass(){

    }
    public NutritionistSignUpHelperClass(String nutritionistName,
                                         String nutritionistGraduation,
                                         String nutritionistEmail,
                                         String nutritionistPhone,
                                         String nutritionistPassword) {
        this.nutritionistName = nutritionistName;
        this.nutritionistGraduation = nutritionistGraduation;
        this.nutritionistEmail = nutritionistEmail;
        this.nutritionistPhone = nutritionistPhone;
        this.nutritionistPassword = nutritionistPassword;
    }

    public String getNutritionistName() {
        return nutritionistName;
    }

    public void setNutritionistName(String nutritionistName) {
        this.nutritionistName = nutritionistName;
    }

    public String getNutritionistEmail() {
        return nutritionistEmail;
    }

    public void setNutritionistEmail(String nutritionistEmail) {
        this.nutritionistEmail = nutritionistEmail;
    }

    public String getNutritionistGraduation() {
        return nutritionistGraduation;
    }

    public void setNutritionistGraduation(String nutritionistGraduation) {
        this.nutritionistGraduation = nutritionistGraduation;
    }

    public String getNutritionistPhone() {
        return nutritionistPhone;
    }

    public void setNutritionistPhone(String nutritionistPhone) {
        this.nutritionistPhone = nutritionistPhone;
    }

    public String getNutritionistPassword() {
        return nutritionistPassword;
    }

    public void setNutritionistPassword(String nutritionistPassword) {
        this.nutritionistPassword = nutritionistPassword;
    }
}
