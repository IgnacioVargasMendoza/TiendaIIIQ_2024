
package com.tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ignac
 */
@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String page(Model model) {
        
        //model.addAttribute("attribute", "value");
        
        //se colocar el nombre del folder y el nombre del archivo
        //como es en template solo debemos de colocar el nombre despues del punto si 
        //hay mas folders debajo de template. En este caso, index esta dentro de template
        //por lo tanto no hay que colocar template antes del nombre del archvo.
        return "/index";
    }
    
}
