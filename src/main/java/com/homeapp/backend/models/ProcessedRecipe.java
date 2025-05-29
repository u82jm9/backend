package com.homeapp.backend.models;

import java.util.HashMap;
import java.util.List;

public class ProcessedRecipe {

    private String recipeName;
    private HashMap<String, List<String>> ingredients;
    private List<String> instructions;
    private String additionalNotes;

    public ProcessedRecipe() {
    }

    public ProcessedRecipe(String recipeName, HashMap<String, List<String>> ingredients, List<String> instructions, String additionalNotes) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.additionalNotes = additionalNotes;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public HashMap<String, List<String>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<String, List<String>> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    @Override
    public String toString() {
        return "ProcessedRecipe{" +
                "recipeName='" + recipeName + '\'' +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}
