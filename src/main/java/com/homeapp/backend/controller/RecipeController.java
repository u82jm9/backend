package com.homeapp.backend.controller;

import com.homeapp.backend.models.DTORecipe;
import com.homeapp.backend.models.ProcessedRecipe;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Recipes/")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private final InfoLogger infoLogger = new InfoLogger();

    @Autowired
    public RecipeService recipeService;

    @PostMapping("ProcessRecipe")
    public ResponseEntity<ProcessedRecipe> processRecipe(@RequestBody DTORecipe recipeLink) {
        infoLogger.log("Processing recipe from link : " + recipeLink.getRecipeLink());
        ProcessedRecipe recipe = recipeService.processLink(recipeLink.getRecipeLink());
        infoLogger.log("Processed recipe successfully.");
        return ResponseEntity.ok(recipe);
    }


}
