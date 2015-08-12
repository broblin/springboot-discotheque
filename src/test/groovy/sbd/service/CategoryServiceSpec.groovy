package sbd.service

import sbd.domain.Category
import sbd.exception.EntityAlreadyExistsException
import sbd.repository.CategoryRepository
import spock.lang.Specification

/**
 * Created by benoit on 12/08/15.
 */
class CategoryServiceSpec extends Specification {

    CategoryService categoryService

    CategoryRepository categoryRepository

    def setup(){
        categoryRepository = Mock(CategoryRepository)
        categoryService = new CategoryServiceImpl(categoryRepository)
    }

    def "create a category and generate an exception if it already exists"(){
        given: "a category"
        String categoryName = "aCategory"
        Long id = 1L
        Category category = new Category(name: categoryName,id:id)
        when: "we create the category"
        Category categoryCreated = categoryService.create(category)
        then: "the category exists"
        categoryCreated?.name == categoryName
        1 * categoryRepository.save(category)

        when: "We try to create this category, a second time"
        1 * categoryRepository.findOne(category.getId()) >> category
        categoryService.create(category)

        then: "an exception si thrown"
        thrown(EntityAlreadyExistsException)
    }

    def "create a category and generate an exception if a category with a same name already exists"(){
        given: "a category"
        String categoryName = "aCategory"
        Long id = 1L
        Category category = new Category(name: categoryName,id:id)
        Category category2 = new Category(name: categoryName,id:2L)

        when: "We try to create this category, a second time"
        1 * categoryRepository.findByName(category.name) >> category
        categoryService.create(category2)

        then: "an exception si thrown"
        thrown(EntityAlreadyExistsException)
    }
}
