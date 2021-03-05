package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository
        extends CrudRepository<Recipe, Long>
{

}
