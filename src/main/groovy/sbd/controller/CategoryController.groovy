package sbd.controller

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import sbd.domain.Category
import sbd.service.CategoryService
import sdb.dto.CategoryDTO

import javax.inject.Inject
import javax.validation.Valid
import javax.validation.constraints.NotNull

/**
 * CRUD full REST controller
 * Created by benoit on 12/08/15.
 */
@RestController
class CategoryController {

    public static final String ID_ERROR_MSG = "the id parameter can't be null"
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
            throw new Exception(ID_ERROR_MSG)
        }
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    Category delete(@PathVariable @NotNull Long id){
        if(id){
            return categoryService.delete(id)
        }else{
            throw new Exception(ID_ERROR_MSG)
        }
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    Category get(@PathVariable @NotNull Long id){
        if(id){
            return categoryService.get(id)
        }else{
            throw new Exception(ID_ERROR_MSG)
        }
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    Page<Category> find(Integer pageNumber,Integer pageSize){
        return categoryService.findAll(pageNumber ?pageNumber-1:0,pageSize ?:3)
    }
}
