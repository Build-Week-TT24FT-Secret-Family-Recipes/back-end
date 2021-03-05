package com.lambdaschool.foundation.services;


        import com.lambdaschool.foundation.models.Category;

        import java.util.List;

public interface CategoryService
{
    List<Category> findAll();

    Category findCategoryById(long id);

    void delete(long id);

    Category save(Category role);

    Category update(Category role,
                   long id);

    void deleteAll();
}