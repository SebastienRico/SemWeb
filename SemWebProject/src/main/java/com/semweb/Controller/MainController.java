package com.semweb.Controller;

import com.semweb.Model.Town;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/towns")
    public String goToTownsPage() {
        return "/towns.html";
    }
}
