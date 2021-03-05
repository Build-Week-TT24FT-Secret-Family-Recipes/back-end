package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository
    extends CrudRepository<Category, Long>
{

}
