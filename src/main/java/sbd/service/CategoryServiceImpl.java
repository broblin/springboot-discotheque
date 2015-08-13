package sbd.service;

import org.springframework.stereotype.Service;
import sbd.domain.Category;
import sbd.exception.EntityAlreadyExistsException;
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
            throw new EntityAlreadyExistsException(String.format("Une catégorie avec cet id:%d existe déjà!", category.getId()));
        }else if(repository.findByName(category.getName()) != null){
            throw new EntityAlreadyExistsException(String.format("Une catégorie avec cet intitulé:%s existe déjà!",category.getName()));
        }else{
            repository.save(category);
        }
        return category;
    }

    @Override
    public Category update(Long id, String name) {
        return null;
    }

    @Override
    public Category get(Long id) {
        return null;
    }

    @Override
    public Category delete(Long id) {
        return null;
    }
}
