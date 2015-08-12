package sbd.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import sbd.domain.Category
import sbd.service.CategoryService

import javax.inject.Inject
import javax.validation.Valid

/**
 * Created by benoit on 12/08/15.
 */
@RestController
class CategoryController {

    @Inject
    CategoryService categoryService

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    Category create(@RequestBody @Valid Category category){
        return categoryService.create(category)
    }
}
