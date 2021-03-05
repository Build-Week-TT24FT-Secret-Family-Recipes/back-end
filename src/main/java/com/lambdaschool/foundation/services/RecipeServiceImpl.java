package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Recipe;
import com.lambdaschool.foundation.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;





@Transactional
@Service("recipeService")
public class RecipeServiceImpl
        implements RecipeService
{
    @Autowired
    UserAuditing userAuditing;

    @Autowired
    RecipeRepository reciperepos;

    @Autowired
    CategoryService categoryService;


    @Override
    public List<Recipe> findAll()
    {
        List<Recipe> list = new ArrayList<>();
        reciperepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Recipe findRecipeById(long id)
    {
        return reciperepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe with id " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (reciperepos.findById(id)
                .isPresent())
        {
            reciperepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Recipe with id " + id + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Recipe save(Recipe recipe)
    {
        Recipe newRecipe = new Recipe();

        if (recipe.getRecipeid() != 0)
        {
            reciperepos.findById(recipe.getRecipeid())
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe id " + recipe.getRecipeid() + " not found!"));
        }

        newRecipe.setTitle(recipe.getTitle());
        newRecipe.setSource(recipe.getSource());
        newRecipe.setIngredients(recipe.getIngredients());
        newRecipe.setInstructions(recipe.getInstructions());

        if (recipe.getCategory() != null)
        {
            newRecipe.setCategory(categoryService.findCategoryById(recipe.getCategory()
                    .getCategoryid()));
        }

        return reciperepos.save(newRecipe);
    }

    @Transactional
    @Override
    public Recipe update(Recipe recipe,
                       long id)
    {
        Recipe currentRecipe = findRecipeById(id);

        if (recipe.getTitle() != null)
        {
            currentRecipe.setTitle(recipe.getTitle());
        }

        if (recipe.getSource() != null)
        {
            currentRecipe.setSource(recipe.getSource());
        }

        if (recipe.getIngredients() != null)
        {
            currentRecipe.setIngredients(recipe.getIngredients());
        }

        if (recipe.getInstructions() != null)
        {
            currentRecipe.setInstructions(recipe.getInstructions());
        }

        if (recipe.getCategory() != null)
        {
            currentRecipe.setCategory(categoryService.findCategoryById(recipe.getCategory()
                    .getCategoryid()));
        }


        return reciperepos.save(currentRecipe);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll()
    {
        reciperepos.deleteAll();
    }
}
