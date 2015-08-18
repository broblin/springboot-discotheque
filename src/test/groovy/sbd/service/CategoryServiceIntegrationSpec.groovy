package sbd.service

import org.junit.runner.RunWith
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.data.domain.Page
import org.springframework.test.context.ContextConfiguration
import sbd.Application
import sbd.domain.Category
import spock.lang.Specification

import javax.inject.Inject
import java.util.stream.Collectors

/**
 * Created by benoit on 18/08/15.
 */
@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [Application.class] )
class CategoryServiceIntegrationSpec extends Specification {

    @Inject
    CategoryService categoryService


    def shouldFindAllCategories(){
        given:
        List<String> names = Arrays.asList("test1", "test2", "test3", "test4", "test5");
        names.eachWithIndex { name,index -> categoryService.create(createCategory(index+1,name)) }

        when:
        Page<Category> paginatedList =  categoryService.findAll(1, 2);

        then:
        paginatedList?.hasContent()
        3 == paginatedList.totalPages
        5 == paginatedList.totalElements
        ["test3", "test4"] == paginatedList.getContent().stream().map{it.name}.collect(Collectors.toList())
    }

    private Category createCategory(int id,String name){
        return new Category(id:id,name: name)
    }
}
