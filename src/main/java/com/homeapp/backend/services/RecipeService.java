package com.homeapp.backend.services;

import com.homeapp.backend.models.ProcessedRecipe;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.models.logger.InfoLogger;
import com.homeapp.backend.models.logger.WarnLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private static final InfoLogger infoLogger = new InfoLogger();
    private static final WarnLogger warnLogger = new WarnLogger();
    private static final ErrorLogger errorLogger = new ErrorLogger();

    /**
     * Process a recipe link and return a ProcessedRecipe object.
     *
     * @param recipeLink the recipe link to process
     * @return a ProcessedRecipe object containing the processed recipe data
     */
    public ProcessedRecipe processLink(String recipeLink) {
        ProcessedRecipe processedRecipe = new ProcessedRecipe();
        try {
            Optional<Element> e;
            Document doc = Jsoup.connect(recipeLink).get();
            if (recipeLink.contains("bbc.co.uk")) {
                infoLogger.log("Processing BBC recipe link: " + recipeLink);
                e = Optional.ofNullable(doc.getElementById("main-content"));
                if (e.isPresent()) {
                    Element mainContent = e.get();
                    processedRecipe.setRecipeName(mainContent.getElementById("main-heading").select("h1").text());
                    populateBBCIngredients(mainContent, processedRecipe);
                    populateBBCInstructions(mainContent, processedRecipe);
                    populateBBCNotes(mainContent, processedRecipe);
                } else {
                    warnLogger.log("No main content found for recipe: " + recipeLink);
                }
            } else if (recipeLink.contains("allrecipes.com")) {
                infoLogger.log("Processing allrecipes recipe link: " + recipeLink);
                e = Optional.ofNullable(doc.getElementById("main"));
                if (e.isPresent()) {
                    Element mainContent = e.get();
                    processedRecipe.setRecipeName(mainContent.getElementById("article-header--recipe_1-0").select("h1").text());
                    populateAllRecipeIngredients(mainContent, processedRecipe);
                    populateAllRecipeInstructions(mainContent, processedRecipe);
                } else {
                    warnLogger.log("No main content found for recipe: " + recipeLink);
                }
            } else if (recipeLink.contains("bbcgoodfood.com")) {
                infoLogger.log("Processing BBC Good Food recipe link: " + recipeLink);
                e = Optional.ofNullable(doc.select("div.post.recipe").first());
                if (e.isPresent()) {
                    Element mainContent = e.get();
                    processedRecipe.setRecipeName(mainContent.select("div.post-header__title").select("h1").text());
                    populateBBCGoodFoodIngredients(mainContent, processedRecipe);
                    populateBBCGoodFoodInstructions(mainContent, processedRecipe);
                } else {
                    warnLogger.log("No main content found for recipe: " + recipeLink);
                }
            }
        } catch (IOException e) {
            errorLogger.log("An IOException occurred processing recipe: " + recipeLink + "!! See error message: " + e);
        }
        return processedRecipe;
    }

    private void populateBBCGoodFoodInstructions(Element mainContent, ProcessedRecipe processedRecipe) {
        List<Element> instructionElements = mainContent.select("ul.method-steps__list").first().select("li");
        List<String> instructions = extractTextFromElements(instructionElements);
        List<String> trimmed = instructions.stream()
                .map(s -> s.replaceFirst("(?i)^step\\s*\\d+\\s*", ""))
                .collect(Collectors.toList());
        processedRecipe.setInstructions(trimmed);
    }

    private void populateBBCGoodFoodIngredients(Element mainContent, ProcessedRecipe processedRecipe) {
        HashMap<String, List<String>> ingredientsMap = new HashMap<>();
        List<Element> ingredientSections = mainContent.getElementById("ingredients-list").select("section");
        ingredientSections.remove(0);
        for (Element section : ingredientSections) {
            String sectionTitle = "";
            sectionTitle = section.select("h3").text();
            List<Element> ingredientElements = section.select("li");
            if (sectionTitle.isEmpty()) {
                sectionTitle = "Main";
            }
            if (!ingredientElements.isEmpty()) {
                ingredientsMap.put(sectionTitle, extractTextFromElements(ingredientElements));
            }
        }
        processedRecipe.setIngredients(ingredientsMap);
    }

    private void populateAllRecipeInstructions(Element mainContent, ProcessedRecipe processedRecipe) {
        List<Element> instructionElements = mainContent.select("div.mm-recipes-steps__content").first().select("li");
        processedRecipe.setInstructions(extractTextFromElements(instructionElements));
    }

    private void populateAllRecipeIngredients(Element mainContent, ProcessedRecipe processedRecipe) {
        HashMap<String, List<String>> ingredientsMap = new HashMap<>();
        List<Element> ingredientElements = mainContent.select("div.mm-recipes-structured-ingredients").first().select("li");
        ingredientsMap.put("Main", extractTextFromElements(ingredientElements));
        processedRecipe.setIngredients(ingredientsMap);
    }

    private void populateBBCNotes(Element mainContent, ProcessedRecipe processedRecipe) {
        Element notesElement = mainContent.select("div.e1p7pssy1").first();
        if (notesElement != null) {
            String additionalNotes = notesElement.select("div.eap7u6q0").text();
            processedRecipe.setAdditionalNotes(additionalNotes);
        } else {
            warnLogger.log("No additional notes found for recipe: " + processedRecipe.getRecipeName());
        }
    }

    private void populateBBCInstructions(Element mainContent, ProcessedRecipe processedRecipe) {
        List<String> instructions = new ArrayList<>();
        List<Element> instructionElements = mainContent.select("div.e10q0gy41").first().select("li");
        for (Element instruction : instructionElements) {
            instructions.add(instruction.text());
        }
        processedRecipe.setInstructions(instructions);
    }

    private void populateBBCIngredients(Element mainContent, ProcessedRecipe processedRecipe) {
        HashMap<String, List<String>> ingredientsMap = new HashMap<>();

        Element allIngredientsDiv = mainContent.select("div.e1hdfwc21").get(0);
        Integer numberOfIngredientSections = allIngredientsDiv.select("h3").size();
        for (int i = 0; i < numberOfIngredientSections; i++) {
            String key = allIngredientsDiv.select("h3").get(i).text();
            if (key.equals("")) {
                key = "Main";
            }
            List<Element> ingredientElements = allIngredientsDiv.select("div.e1hdfwc20").get(i).select("li");
            ingredientsMap.put(key, extractTextFromElements(ingredientElements));
        }
        processedRecipe.setIngredients(ingredientsMap);
    }

    private List<String> extractTextFromElements(List<Element> elements) {
        List<String> texts = new ArrayList<>();
        for (Element e : elements) {
            texts.add(e.text());
        }
        return texts;
    }

    public List<String> getValidSites() {
        infoLogger.log("Returning valid sites that can be processed.");
        List<String> validSites = new ArrayList<>();
        validSites.add("bbc.co.uk/food/recipes");
        validSites.add("allrecipes.com");
        validSites.add("bbcgoodfood.com");
        return validSites;
    }
}
