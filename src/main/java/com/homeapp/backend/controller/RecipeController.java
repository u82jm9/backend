package com.homeapp.backend.controller;

import com.homeapp.backend.models.DTORecipe;
import com.homeapp.backend.models.ProcessedRecipe;
import com.homeapp.backend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@RestController
@RequestMapping("Recipes/")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private final Logger logger = Logger.getLogger(RecipeController.class.getName());

    @Autowired
    public RecipeService recipeService;

    @PostMapping("ProcessRecipe")
    public ResponseEntity<ProcessedRecipe> processRecipe(@RequestBody DTORecipe recipeLink) {
        logger.log(INFO, "Processing recipe from link : " + recipeLink.getRecipeLink());
        ProcessedRecipe recipe = recipeService.processLink(recipeLink.getRecipeLink());
        logger.log(INFO, "Processed recipe successfully.");
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("GetValidSites")
    public ResponseEntity<List<String>> getValidSites() {
        logger.log(INFO, "Fetching valid sites for recipe processing.");
        List<String> validSites = recipeService.getValidSites();
        logger.log(INFO, "Fetched valid sites successfully.");
        return ResponseEntity.ok(validSites);
    }
}
