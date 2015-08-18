package sbd.service;

import org.springframework.data.domain.Page;
import sbd.domain.Category;

/**
 * Created by benoit on 12/08/15.
 */
public interface CategoryService {
    Category create(Category category);

    Category update(Long id,String name);

    Category get(Long id);

    Category delete(Long id);

    Page<Category> findAll(Integer pageNumber, Integer pageSize);
}
