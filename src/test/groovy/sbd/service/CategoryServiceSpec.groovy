package sbd.service

import sbd.domain.Category
import sbd.exception.EntityAlreadyExistsException
import sbd.exception.EntityNotFoundException
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

    def "update a category and generate an exception if this category doesn't exist"(){
        given: "a category"
        String categoryName = "aCategory"
        String newCategoryName = "aNewName"
        Long id = 1L
        Category category = new Category(name: categoryName,id:id)

        when: "we update the category"
        1 * categoryRepository.findOne(id) >> category
        Category categoryUpdated = categoryService.update(id,newCategoryName)

        then : "the category name has been updated"
        categoryUpdated?.name == newCategoryName
        1 * categoryRepository.save(category)

    }

    def "update a category is forbidden if an other category with same name already exists"(){
        given:"2 category"
        Long firstId = 1L
        Long secondId = 2L
        String firstCategoryName = "aCategory"
        String secondCategoryName = "aCategory2"
        Category category = new Category(name: firstCategoryName,id:firstId)
        Category category2 = new Category(name: secondCategoryName,id:secondId)

        when:"we update the second category with the first one name"
        1 * categoryRepository.findOne(secondId) >> category2
        1 * categoryRepository.findByName(firstCategoryName) >> category
        categoryService.update(secondId,firstCategoryName)
        then:"an exception is thrown"
        thrown(EntityAlreadyExistsException)
    }

    def "get a category or generate an exception if it doesn't exist"(){
        given:"a category"
        String categoryName = "aCategory"
        Long id = 1L
        Long unknownId = 2l
        Category category = new Category(name: categoryName,id:id)

        when:"get the category"
        1 * categoryRepository.findOne(id) >> category
        Category categoryFound = categoryService.get(id)

        then:"we found the category"
        categoryFound == category

        when:"try to get a category which doesn't exist"
        categoryService.get(unknownId)

        then:"an exception is thrown"
        thrown(EntityNotFoundException)
    }

    def "delete a category or generate an exception if it doesn't exist"(){
        given:"a category"
        String categoryName = "aCategory"
        Long id = 1L
        Long unknownId = 2l
        Category category = new Category(name: categoryName,id:id)

        when:"get the category"
        1 * categoryRepository.findOne(id) >> category
        Category categoryDeleted = categoryService.delete(id)

        then:"we found the category"
        categoryDeleted == category
        1 * categoryRepository.delete(category)

        when:"try to get a category which doesn't exist"
        categoryService.delete(unknownId)

        then:"an exception is thrown"
        thrown(EntityNotFoundException)
    }
}
