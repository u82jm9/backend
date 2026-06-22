package com.homeapp.backend;

import com.homeapp.backend.models.ProcessedRecipe;
import com.homeapp.backend.models.logger.ErrorLogger;
import com.homeapp.backend.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeTests {

    @Autowired
    RecipeService recipeService;

    @Autowired
    ErrorLogger errorLogger;

    private static HashMap<String, List<String>> getExpectedIngredients() {
        HashMap<String, List<String>> expectedIngredients = new HashMap<>();
        List<String> mainIngredients = new ArrayList<>();
        mainIngredients.add("2 tbsp olive oil");
        mainIngredients.add("2 tbsp tomato purée");
        List<String> extraIngredients = new ArrayList<>();
        extraIngredients.add("2 tbsp red wine vinegar");
        extraIngredients.add("½ bunch parsley, finely chopped");
        expectedIngredients.put("Main", mainIngredients);
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

    private static List<String> getBBCGoodFoodInstructions() {
        List<String> expectedInstructions = new ArrayList<>();
        expectedInstructions.add("To make the white sauce, melt the butter in a saucepan, stir in the plain flour, then cook for 2 mins. Slowly whisk in the milk, then bring to the boil, stirring. Turn down the heat, then cook until the sauce starts to thicken and coats the back of a wooden spoon. Will keep, cooled, in the fridge for up to three days or frozen for three months.");
        expectedInstructions.add("Spoon the remaining white sauce over the pasta, making sure the whole surface is covered, then scatter over the mozzarella and cherry tomatoes. Bake for 45 mins until bubbling and golden.");
        return expectedInstructions;
    }

    private static HashMap<String, List<String>> getBBCGoodFoodIngredientsHashMap() {
        HashMap<String, List<String>> expectedIngredients = new HashMap<>();
        List<String> mainIngredients1 = new ArrayList<>();
        mainIngredients1.add("2 aubergines cut into ½ cm thick slices");
        mainIngredients1.add("8 tbsp olive oil plus extra for the dish");
        expectedIngredients.put("Main", mainIngredients1);
        List<String> mainIngredients2 = new ArrayList<>();
        mainIngredients2.add("2 garlic cloves sliced");
        mainIngredients2.add("2 onions finely chopped");
        expectedIngredients.put("For the tomato sauce", mainIngredients2);
        List<String> mainIngredients3 = new ArrayList<>();
        mainIngredients3.add("85g butter");
        mainIngredients3.add("750ml milk");
        expectedIngredients.put("For the white sauce", mainIngredients3);
        return expectedIngredients;
    }

    @Test
    public void test_That_a_BBC_recipe_link_can_be_processed() {
        String recipeLink = "https://www.bbc.co.uk/food/recipes/one-pan_pastitsio_74748";
        String expectedRecipeName = "One-pan pastitsio";
        HashMap<String, List<String>> expectedIngredients = getExpectedIngredients();
        ProcessedRecipe processedRecipe = recipeService.processLink(recipeLink);
        assertEquals(expectedRecipeName, processedRecipe.getRecipeName());
        assertEquals(processedRecipe.getIngredients().keySet(), expectedIngredients.keySet());
        assertEquals("You'll need a wide, deep frying pan for this dish. If you don't have one that will fit all the ingredients, make this recipe in a large saucepan instead.", processedRecipe.getAdditionalNotes());
        assertTrue(processedRecipe.getIngredients().get("Main").containsAll(expectedIngredients.get("Main")));
        assertTrue(processedRecipe.getIngredients().get("For the salad").containsAll(expectedIngredients.get("For the salad")));
        assertTrue(processedRecipe.getInstructions().containsAll(getExpectedInstructions()));
    }

    @Test
    public void test_That_an_allrecipes_recipe_link_fails() {
        String recipeLink = "https://www.allrecipes.com/one-pot-turmeric-chicken-and-rice-recipe-8716130";
        ProcessedRecipe processedRecipe = recipeService.processLink(recipeLink);
        TreeSet<String> errors = errorLogger.getLogs();
        assertFalse(errors.isEmpty());
        assertTrue(errors.stream().anyMatch(log -> log.contains("HTTP Error 403 for recipe")));
        assertNull(processedRecipe.getRecipeName());
        assertNull(processedRecipe.getIngredients());
        assertNull(processedRecipe.getInstructions());
        assertNull(processedRecipe.getAdditionalNotes());
    }

    @Test
    public void test_That_a_BBC_recipe_link_chicken_and_chips_can_be_processed() {
        String recipeLink = "https://www.bbc.co.uk/food/recipes/barbecue_pulled_chicken_47216";
        String expectedRecipeName = "Barbecue pulled chicken with sweet potato wedges";
        HashMap<String, List<String>> expectedIngredients = new HashMap<>();
        List<String> mainIngredients = new ArrayList<>();
        mainIngredients.add("1.8kg/4lb chicken thighs, boneless and skin removed");
        mainIngredients.add("2 tsp olive oil");
        expectedIngredients.put("For the batch-cooked chicken thighs", mainIngredients);
        List<String> burgerIngredients = new ArrayList<>();
        burgerIngredients.add("⅓ of the batch-cooked chicken thighs, about 400g/14oz cooked weight (see above)");
        burgerIngredients.add("4 bread rolls or burger buns");
        expectedIngredients.put("For the barbecue pulled chicken", burgerIngredients);
        List<String> wedgesIngredients = new ArrayList<>();
        wedgesIngredients.add("1kg/2lb 4oz sweet potatoes, scrubbed and cut into wedges");
        wedgesIngredients.add("2 tsp paprika");
        expectedIngredients.put("For the sweet potato wedges", wedgesIngredients);
        List<String> expectedInstructions = new ArrayList<>();
        expectedInstructions.add("For the sweet potato wedges, preheat the oven to 240C/220C Fan/Gas 9. Put the sweet potato wedges on a baking tray and toss with the vegetable oil and paprika (using clean hands is the easiest way to coat them). Roast for 40–50 minutes or until cooked through and turning brown.");
        expectedInstructions.add("While the chicken is warming, toast the rolls or buns. Pile the hot chicken into the buns and serve with the sweet potato wedges.");
        ProcessedRecipe processedRecipe = recipeService.processLink(recipeLink);
        assertEquals(expectedRecipeName, processedRecipe.getRecipeName());
        assertEquals(processedRecipe.getIngredients().keySet(), expectedIngredients.keySet());
        assertEquals(processedRecipe.getIngredients().size(), 3);
        assertTrue(processedRecipe.getIngredients().get("For the batch-cooked chicken thighs").containsAll(expectedIngredients.get("For the batch-cooked chicken thighs")));
        assertTrue(processedRecipe.getIngredients().get("For the barbecue pulled chicken").containsAll(expectedIngredients.get("For the barbecue pulled chicken")));
        assertTrue(processedRecipe.getInstructions().containsAll(expectedInstructions));
    }

    @Test
    public void test_That_a_BBC_Good_Food_recipe_link_can_be_processed() {
        String recipeLink = "https://www.bbcgoodfood.com/recipes/easy-vegetable-lasagne";
        String expectedRecipeName = "Vegetarian lasagne";
        HashMap<String, List<String>> expectedIngredients = getBBCGoodFoodIngredientsHashMap();
        List<String> expectedInstructions = getBBCGoodFoodInstructions();
        ProcessedRecipe processedRecipe = recipeService.processLink(recipeLink);
        assertEquals(expectedRecipeName, processedRecipe.getRecipeName());
        assertEquals(processedRecipe.getIngredients().keySet(), expectedIngredients.keySet());
        assertNull(processedRecipe.getAdditionalNotes());
        assertTrue(processedRecipe.getIngredients().get("Main").containsAll(expectedIngredients.get("Main")));
        assertTrue(processedRecipe.getInstructions().containsAll(expectedInstructions));
    }

    @Test
    public void test_That_a_BBC_Good_Food_banana_bread_can_be_processed() {
        String recipeLink = "https://www.bbcgoodfood.com/recipes/brilliant-banana-loaf";
        String expectedRecipeName = "Banana bread";
        HashMap<String, List<String>> expectedIngredients = new HashMap<>();
        List<String> mainIngredients = new ArrayList<>();
        mainIngredients.add("140g self-raising flour");
        mainIngredients.add("2 very ripe bananas mashed");
        expectedIngredients.put("Main", mainIngredients);
        List<String> expectedInstructions = new ArrayList<>();
        expectedInstructions.add("Pour the mixture into the prepared tin and bake for about 50 mins, or until cooked through. Check the loaf at 5-min intervals from around 30-40 mins in the oven by testing it with a skewer (it should be able to be inserted and removed cleanly), as the time may vary depending on the shape of your loaf tin.");
        expectedInstructions.add("Cream 140g softened butter and 140g caster sugar until light and fluffy, then slowly add 2 beaten large eggs with a little of the 140g flour.");
        expectedInstructions.add("Drizzle the icing across the top of the cake and decorate with a handful of banana chips.");
        ProcessedRecipe processedRecipe = recipeService.processLink(recipeLink);
        assertEquals(expectedRecipeName, processedRecipe.getRecipeName());
        assertEquals(processedRecipe.getIngredients().keySet(), expectedIngredients.keySet());
        assertNull(processedRecipe.getAdditionalNotes());
        assertTrue(processedRecipe.getIngredients().get("Main").containsAll(expectedIngredients.get("Main")));
        assertTrue(processedRecipe.getInstructions().containsAll(expectedInstructions));
    }

}
