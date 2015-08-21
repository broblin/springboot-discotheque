package sbd.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by benoit on 18/08/15.
 */
@Controller
class IhmController {

    @RequestMapping("/ihm/categories-v1.html")
    public String categoriesV1(){
        return "categories-v1"
    }
}
