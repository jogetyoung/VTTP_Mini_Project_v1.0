package com.example.Pokemon_TCG_TEST.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class APIDocController {

    @GetMapping("/api/documentation")
    public String getDocumentationPage() {
        return "api-documentation"; // Name of the HTML template for the documentation page
    }
}
