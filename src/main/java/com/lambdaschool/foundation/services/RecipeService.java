package com.lambdaschool.foundation.services;


import com.lambdaschool.foundation.models.Recipe;

import java.util.List;

public interface RecipeService
{
    List<Recipe> findAll();

    Recipe findRecipeById(long id);

    void delete(long id);

    Recipe save(Recipe role);

    Recipe update(Recipe role,
                long id);

    void deleteAll();
}