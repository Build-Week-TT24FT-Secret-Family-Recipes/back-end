package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.models.Recipe;
import com.lambdaschool.foundation.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/recipes")
public class RecipeController
{
    @Autowired
    RecipeService recipeService;


    @GetMapping(value = "/recipes",
            produces = {"application/json"})
    public ResponseEntity<?> listAllRecipes(HttpServletRequest request)
    {
        List<Recipe> myRecipes = recipeService.findAll();
        return new ResponseEntity<>(myRecipes,
                HttpStatus.OK);
    }

    // http://localhost:2019/books/book/{bookId}
    @GetMapping(value = "/recipe/{recipeid}",
            produces = {"application/json"})
    public ResponseEntity<?> getRecipeById(HttpServletRequest request,
                                         @PathVariable
                                                 Long recipeId)
    {
        Recipe s = recipeService.findRecipeById(recipeId);
        return new ResponseEntity<>(s,
                HttpStatus.OK);
    }

    // POST http://localhost:2019/books/book
    @PostMapping(value = "/recipe", consumes = "application/json")
    public ResponseEntity<?> addNewRecipe(@Valid @RequestBody Recipe newRecipe) throws
            URISyntaxException
    {
        newRecipe.setRecipeid(0);
        newRecipe = recipeService.save(newRecipe);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRecipeURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{recipeid}")
                .buildAndExpand(newRecipe.getRecipeid())
                .toUri();
        responseHeaders.setLocation(newRecipeURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    // PUT http://localhost:2019/books/book/1
    @PutMapping(value = "/recipe/{recipeid}",
            consumes = "application/json")
    public ResponseEntity<?> updateFullRecipe(
            @Valid
            @RequestBody
                    Recipe updateRecipe,
            @PathVariable
                    long recipeid)
    {
        updateRecipe.setRecipeid(recipeid);
        recipeService.save(updateRecipe);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE http://localhost:2019/books/book/1
    @DeleteMapping(value = "/recipe/{id}")
    public ResponseEntity<?> deleteRecipeById(
            @PathVariable
                    long id)
    {
        recipeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
