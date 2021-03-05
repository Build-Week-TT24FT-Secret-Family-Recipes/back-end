package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceFoundException;
import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Category;
import com.lambdaschool.foundation.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;





@Transactional
@Service("categoryService")
public class CategoryServiceImpl
        implements CategoryService
{
    @Autowired
    CategoryRepository categoryrepos;

    @Override
    public List<Category> findAll()
    {
        List<Category> list = new ArrayList<>();
        categoryrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;

    }

    @Override
    public Category findCategoryById(long id)
    {
        return categoryrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        Category goodbyeCategory = findCategoryById(id);
        if (goodbyeCategory != null)
        {
            if (goodbyeCategory.getRecipes()
                    .size() > 0)
            {
                throw new ResourceFoundException("category containing recipes cannot be deleted. Move the books to a new section first");
            } else
            {
                categoryrepos.deleteById(id);
            }
        } else
        {
            throw new ResourceNotFoundException("Category with id " + id + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Category save(Category category)
    {
        if (category.getRecipes()
                .size() > 0)
        {
            throw new ResourceFoundException("Recipes are not added through category.");
        }

        Category newCategory = new Category();

        newCategory.setName(category.getName());

        return categoryrepos.save(newCategory);
    }

    @Transactional
    @Override
    public Category update(Category category,
                          long id)
    {
        Category currentCategory = findCategoryById(id);

        if (category.getRecipes()
                .size() > 0)
        {
            throw new ResourceFoundException("Recipes are not updated through categoies.");
        }

        if (category.getName() != null)
        {
            currentCategory.setName(category.getName());
        }

        return categoryrepos.save(currentCategory);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll()
    {
        categoryrepos.deleteAll();
    }
}
