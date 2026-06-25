package com.homeapp.backend.services;

import com.homeapp.backend.models.ProcessedRecipe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.*;

@Service
public class RecipeService {

    private static final Logger logger = Logger.getLogger(RecipeService.class.getName());

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
            try {
                // Try with enhanced headers first
                Document doc = Jsoup.connect(recipeLink)
                        .timeout(10000)
                        .get();

                // Process the document based on the site
                processDocument(doc, recipeLink, processedRecipe);
            } catch (org.jsoup.HttpStatusException httpEx) {
                logger.log(SEVERE, "HTTP Error " + httpEx.getStatusCode() + " for recipe: " + recipeLink);
                if (httpEx.getStatusCode() == 403) {
                    logger.log(WARNING, "Recipe site blocked the request (403 Forbidden). The site may require JavaScript rendering or have Cloudflare protection. URL: " + recipeLink);
                }
                throw httpEx;
            }
        } catch (IOException e) {
            logger.log(SEVERE, "An IOException occurred processing recipe: " + recipeLink + "!! See error message: " + e);
        }
        return processedRecipe;
    }

    private void processDocument(Document doc, String recipeLink, ProcessedRecipe processedRecipe) {
        Optional<Element> e;
        if (recipeLink.contains("bbc.co.uk")) {
            logger.log(INFO, "Processing BBC recipe link: " + recipeLink);
            e = Optional.ofNullable(doc.getElementById("main-content"));
            if (e.isPresent()) {
                Element mainContent = e.get();
                processedRecipe.setRecipeName(mainContent.getElementById("main-heading").select("h1").text());
                populateBBCIngredients(mainContent, processedRecipe);
                populateBBCInstructions(mainContent, processedRecipe);
                populateBBCNotes(mainContent, processedRecipe);
            } else {
                logger.log(WARNING, "No main content found for recipe: " + recipeLink);
            }
        } else if (recipeLink.contains("bbcgoodfood.com")) {
            logger.log(INFO, "Processing BBC Good Food recipe link: " + recipeLink);
            e = Optional.ofNullable(doc.select("div.post.recipe").first());
            if (e.isPresent()) {
                Element mainContent = e.get();
                processedRecipe.setRecipeName(mainContent.select("div.post-header__title").select("h1").text());
                populateBBCGoodFoodIngredients(mainContent, processedRecipe);
                populateBBCGoodFoodInstructions(mainContent, processedRecipe);
            } else {
                logger.log(WARNING, "No main content found for recipe: " + recipeLink);
            }
        }
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

    private void populateBBCNotes(Element mainContent, ProcessedRecipe processedRecipe) {
        Element notesElement = mainContent.select("div.e1p7pssy1").first();
        if (notesElement != null) {
            String additionalNotes = notesElement.select("div.eap7u6q0").text();
            processedRecipe.setAdditionalNotes(additionalNotes);
        } else {
            logger.log(WARNING, "No additional notes found for recipe: " + processedRecipe.getRecipeName());
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
        logger.log(INFO, "Returning valid sites that can be processed.");
        List<String> validSites = new ArrayList<>();
        validSites.add("bbc.co.uk/food/recipes");
        validSites.add("allrecipes.com");
        validSites.add("bbcgoodfood.com");
        return validSites;
    }
}
