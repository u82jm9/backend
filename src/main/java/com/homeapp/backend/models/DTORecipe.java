package com.homeapp.backend.models;

public class DTORecipe {
    private String recipeLink;

    public DTORecipe() {
    }

    public String getRecipeLink() {
        return recipeLink;
    }

    public void setRecipeLink(String recipeLink) {
        this.recipeLink = recipeLink;
    }

    @Override
    public String toString() {
        return "DTORecipe{" +
                "recipeLink='" + recipeLink + '\'' +
                '}';
    }
}
