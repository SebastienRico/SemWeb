package com.semweb.Controller;

import com.semweb.Model.Town;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/towns")
    public String goToTownsPage(Model m) {
        List<String> names = new ArrayList<>();
        names.add("Lyon");
        names.add("Saint-Etienne");
        names.add("Paris");
        names.add("Toulouse");
        
        List<Town> towns = new ArrayList<>();
        
        for(Integer i=0; i<4; i++){
            Town t = new Town();
            t.setId(i.toString());
            t.setName(names.get(i));
            towns.add(t);
        }
        
        m.addAttribute("towns", towns);
            
        return "/towns.html";
    }
}
