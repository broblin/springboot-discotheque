package sbd.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sbd.domain.Category;
import sbd.exception.EntityAlreadyExistsException;
import sbd.exception.EntityNotFoundException;
import sbd.repository.CategoryRepository;

import javax.inject.Inject;

/**
 * Created by benoit on 12/08/15.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Inject
    public CategoryServiceImpl(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        if(category.getId() != null && repository.findOne(category.getId()) != null) {
            throw new EntityAlreadyExistsException(String.format("There already exists a category with id=%d!", category.getId()));
        }else if(repository.findByName(category.getName()) != null){
            throw new EntityAlreadyExistsException(String.format("There already exists a category with name=%s!",category.getName()));
        }else{
            repository.save(category);
        }
        return category;
    }

    @Override
    public Category update(Long id, String name) {
        Category category = get(id);
        if(repository.findByName(name) != null){
            throw new EntityAlreadyExistsException(String.format("There already exists a category with name=%s!", name));
        }
        category.setName(name);
        repository.save(category);
        return category;
    }

    @Override
    public Category get(Long id) {
        Category category = repository.findOne(id);
        if(category == null) {
            throw new EntityNotFoundException(String.format("No category with id=%d exists!", id));
        }else{
            return category;
        }
    }

    @Override
    public Category delete(Long id) {
        Category category = get(id);
        repository.delete(category);
        return category;
    }

    @Override
    public Page<Category> findAll(Integer pageNumber, Integer pageSize) {
        return repository.findAll(new PageRequest(pageNumber,pageSize));
    }
}
