package sbd.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import sbd.domain.Category
import sbd.service.CategoryService
import sdb.dto.CategoryDTO

import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * Created by benoit on 12/08/15.
 */
@RestController
class CategoryController {

    @Inject
    CategoryService categoryService

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    Category create(@RequestBody @Valid CategoryDTO category){
        return categoryService.create(new Category(name:category.name.toLowerCase()))
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT)
    Category update(@RequestBody @Valid CategoryDTO category){
        if(category.id){
            return categoryService.update(category.id,category.name.toLowerCase())
        }else{
            throw new Exception("the id parameter can't be null")
        }
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    Category delete(@PathVariable @NotNull Long id){
        if(id){
            return categoryService.delete(id)
        }else{
            throw new Exception("the id parameter can't be null")
        }
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    Category get(@PathVariable @NotNull Long id){
        if(id){
            return categoryService.get(id)
        }else{
            throw new Exception("the id parameter can't be null")
        }
    }
}
