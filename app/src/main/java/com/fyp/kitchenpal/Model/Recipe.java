package com.fyp.kitchenpal.Model;

import java.util.List;

public class Recipe {

    String id;
    String recipeName;
    String imageURl;
    List<Ingredients> ingredients;
    String recipeMethod;
    Float rating;

    public Recipe() {

    }

    public Recipe(String id, String recipeName, String imageURl, List<Ingredients> ingredients, String recipeMethod, Float rating) {
        this.id = id;
        this.recipeName = recipeName;
        this.imageURl = imageURl;
        this.ingredients = ingredients;
        this.recipeMethod = recipeMethod;
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipeMethod() {
        return recipeMethod;
    }

    public void setRecipeMethod(String recipeMethod) {
        this.recipeMethod = recipeMethod;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
