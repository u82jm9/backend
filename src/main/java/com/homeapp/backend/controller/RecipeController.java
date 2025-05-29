package com.homeapp.backend.controller;

import com.homeapp.backend.models.ProcessedRecipe;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Recipes/")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private final InfoLogger infoLogger = new InfoLogger();
    private final WarnLogger warnLogger = new WarnLogger();
    private final ErrorLogger errorLogger = new ErrorLogger();

    @Autowired
    public RecipeController() {
        // Constructor
    }

    @PostMapping("ProcessRecipe")
    public ResponseEntity<ProcessedRecipe> processRecipe(String recipeLink) {
        infoLogger.log("Processing recipe from link : " + recipeLink);
        ProcessedRecipe recipe = new ProcessedRecipe();

        infoLogger.log("Processed recipe successfully.");
        return ResponseEntity.ok(recipe);
    }


}
