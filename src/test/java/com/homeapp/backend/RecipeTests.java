package com.homeapp.backend;

import com.homeapp.backend.models.ProcessedRecipe;
import com.homeapp.backend.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RecipeTests {

    private static HashMap<String, List<String>> getExpectedIngredients() {
        HashMap<String, List<String>> expectedIngredients = new HashMap<>();
        List<String> mainIngredients = new ArrayList<>();
        mainIngredients.add("2 tbsp olive oil");
        mainIngredients.add("2 tbsp tomato purée");
        List<String> extraIngredients = new ArrayList<>();
        extraIngredients.add("2 tbsp red wine vinegar");
        extraIngredients.add("½ bunch parsley, finely chopped");
        expectedIngredients.put("main", mainIngredients);
        expectedIngredients.put("For the salad", extraIngredients);
        return expectedIngredients;
    }

    private static List<String> getExpectedInstructions() {
        List<String> expectedInstructions = new ArrayList<>();
        expectedInstructions.add("When your pasta is ready, turn your grill to high. Gently toss the pasta and sauce in the pan together. Spoon the crème fraîche mixture over the top and sprinkle with the remaining grated cheese, plus an additional pinch of dried oregano.");
        expectedInstructions.add("Meanwhile, place the red onion, vinegar, sugar, salt, and pepper in a bowl and mix together. Place the remaining ingredients into a large bowl, along with the red onions and toss to coat.");
        expectedInstructions.add("When the mince has started to brown, stir the onions and garlic in with the oregano and cinnamon. Reduce the heat a little and fry for 5 minutes, stirring everything together (add a touch extra olive oil if it looks dry).");
        return expectedInstructions;
    }

    @Test
    public void test_That_a_recipe_link_can_be_processed() {
        RecipeService recipeService = new RecipeService();
        String recipeLink = "https://www.bbc.co.uk/food/recipes/one-pan_pastitsio_74748";
        String expectedRecipeName = "One-pan pastitsio";
        HashMap<String, List<String>> expectedIngredients = getExpectedIngredients();
        ProcessedRecipe processedRecipe = recipeService.processLink(recipeLink);
        assertEquals(expectedRecipeName, processedRecipe.getRecipeName());
        assertEquals(processedRecipe.getIngredients().keySet(), expectedIngredients.keySet());
        assertEquals("You'll need a wide, deep frying pan for this dish. If you don't have one that will fit all the ingredients, make this recipe in a large saucepan instead.", processedRecipe.getAdditionalNotes());
        assertTrue(processedRecipe.getIngredients().get("main").containsAll(expectedIngredients.get("main")));
        assertTrue(processedRecipe.getIngredients().get("For the salad").containsAll(expectedIngredients.get("For the salad")));
        assertTrue(processedRecipe.getInstructions().containsAll(getExpectedInstructions()));
    }

}
