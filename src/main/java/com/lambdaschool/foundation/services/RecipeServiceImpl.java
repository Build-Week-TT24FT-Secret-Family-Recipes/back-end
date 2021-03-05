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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

//    @Autowired
//    AuthorRepository authorrepos;

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
//        newRecipe.setCopy(recipe.getCopy());
        if (recipe.getCategory() != null)
        {
            newRecipe.setCategory(categoryService.findCategoryById(recipe.getCategory()
                    .getCategoryid()));
        }

//        newBook.getWrotes()
//                .clear();
//        for (Wrote w : book.getWrotes())
//        {
//            Author addAuthor = authorrepos.findById(w.getAuthor()
//                    .getAuthorid())
//                    .orElseThrow(() -> new ResourceNotFoundException("Author Id " + w.getAuthor()
//                            .getAuthorid() + " Not Found!"));
//            newBook.getWrotes()
//                    .add(new Wrote(addAuthor, newBook));
//        }
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

//        if (recipe.getWrotes()
//                .size() > 0)
//        {
//            currentBook.getWrotes()
//                    .clear();
//            for (Wrote w : book.getWrotes())
//            {
//                Author addAuthor = authorrepos.findById(w.getAuthor()
//                        .getAuthorid())
//                        .orElseThrow(() -> new ResourceNotFoundException("Author Id " + w.getAuthor()
//                                .getAuthorid() + " Not Found!"));
//                currentBook.getWrotes()
//                        .add(new Wrote(addAuthor, currentBook));
//            }
//        }

        return reciperepos.save(currentRecipe);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll()
    {
        reciperepos.deleteAll();
    }
}
