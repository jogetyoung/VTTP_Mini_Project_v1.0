package com.example.Pokemon_TCG_TEST.RestController;

import com.example.Pokemon_TCG_TEST.Model.Card;
import com.example.Pokemon_TCG_TEST.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardRestController {

    @Autowired
    private CardService cardService;

    //display all cards w same name
    //e.g. http://localhost:8080/api/search?name=alakazam will display all Alakazam cards in JSON
    @GetMapping("/search")
    public List<Card> searchCardsByName(
            @RequestParam(required = false) String name
    ) {
        return cardService.searchCardsByName(name);
    }

    //retrieves card by its unique ID
    //e.g. http://localhost:8080/api/base1-1 will show Alakazam from base set
    @GetMapping("/{id}")
    public Card getCardById(@PathVariable String id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/raw")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

}
