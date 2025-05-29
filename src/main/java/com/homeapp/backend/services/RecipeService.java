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
            e = Optional.ofNullable(doc.getElementById("main-content"));
            if (e.isPresent()) {
                Element mainContent = e.get();
                processedRecipe.setRecipeName(mainContent.getElementById("main-heading").select("h1").text());
                populateIngredients(mainContent, processedRecipe);
                populateInstructions(mainContent, processedRecipe);
                populateNotes(mainContent, processedRecipe);
            } else {
                warnLogger.log("No main content found for recipe: " + recipeLink);
            }

        } catch (IOException e) {
            errorLogger.log("An IOException occurred processing recipe: " + recipeLink + "!!See error message: " + e);

        }
        return processedRecipe;
    }

    private void populateNotes(Element mainContent, ProcessedRecipe processedRecipe) {
        Element notesElement = mainContent.select("div.e1p7pssy1").first();
        if (notesElement != null) {
            String additionalNotes = notesElement.select("div.eap7u6q0").text();
            processedRecipe.setAdditionalNotes(additionalNotes);
        } else {
            warnLogger.log("No additional notes found for recipe: " + processedRecipe.getRecipeName());
        }
    }

    private void populateInstructions(Element mainContent, ProcessedRecipe processedRecipe) {
        List<String> instructions = new ArrayList<>();
        List<Element> instructionElements = mainContent.select("div.e10q0gy41").first().select("li");
        for (Element instruction : instructionElements) {
            instructions.add(instruction.text());
        }
        processedRecipe.setInstructions(instructions);
    }

    private void populateIngredients(Element mainContent, ProcessedRecipe processedRecipe) {
        HashMap<String, List<String>> ingredientsMap = new HashMap<>();
        String firstKey = "main";
        String secondKey = "extra";
        List<Element> extraIngredientsElements = new ArrayList<>();
        List<Element> mainIngredientsElements;
        List<Element> ingredientDivs;
        List<Element> ingredientTitleElements;
        List<String> ingredientTitles = new ArrayList<>();
        ingredientDivs = mainContent.select("div.e1hdfwc20");
        ingredientTitleElements = mainContent.select("div.e1hdfwc21").select("h3");
        mainIngredientsElements = ingredientDivs.get(0).select("li");
        for (Element h3 : ingredientTitleElements) {
            String text = h3.text();
            if (!text.isEmpty()) {
                ingredientTitles.add(text);
            }
        }
        if (!ingredientTitles.isEmpty()) {
            if (ingredientTitles.size() < ingredientTitleElements.size()) {
                secondKey = ingredientTitles.get(0);
            } else {
                firstKey = ingredientTitles.get(0);
                secondKey = ingredientTitles.get(1);
            }
        }
        if (ingredientDivs.size() > 1) {
            for (int i = 1; i < ingredientDivs.size(); i++) {
                extraIngredientsElements.addAll(ingredientDivs.get(i).select("li"));
            }
        }
        ingredientsMap.put(firstKey, extractTextFromElements(mainIngredientsElements));
        ingredientsMap.put(secondKey, extractTextFromElements(extraIngredientsElements));
        processedRecipe.setIngredients(ingredientsMap);
    }

    private List<String> extractTextFromElements(List<Element> elements) {
        List<String> texts = new ArrayList<>();
        for (Element e : elements) {
            texts.add(e.text());
        }
        return texts;
    }
}
