package com.example.Pokemon_TCG_TEST.Controller;

import com.example.Pokemon_TCG_TEST.Model.Card;
import com.example.Pokemon_TCG_TEST.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    // Display the search form
    @GetMapping("/search")
    public String showSearchPage(Model model) {
        model.addAttribute("sets", cardService.getAvailableSets());
        model.addAttribute("types", cardService.getAvailableTypes());
        model.addAttribute("rarities", cardService.getAvailableRarities());
        return "search";  // Name of the Thymeleaf template
    }

    // Handle the search request
    @GetMapping("/cards/search")
    public String searchCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String set,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String rarity,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            Model model
    ) {
        List<Card> cards = cardService.searchCards(name, set, type, rarity, page, pageSize);

        // Calculate total pages
        int totalCards = cardService.getTotalCardCount(name, set, type, rarity);
        int totalPages = (int) Math.ceil((double) totalCards / pageSize);

        // Add attributes to the model
        model.addAttribute("cards", cards);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // Add query parameters to the model to pass to Thymeleaf
        model.addAttribute("name", name);
        model.addAttribute("set", set);
        model.addAttribute("type", type);
        model.addAttribute("rarity", rarity);
        model.addAttribute("pageSize", pageSize);

        return "searchResults";
    }

    @GetMapping("/cards/{id}")
    public String getCardDetails(@PathVariable String id, Model model) {
        Card card = cardService.getCardById(id);

        if (card == null) {
            throw new RuntimeException("Card not found: " + id);
        }

        model.addAttribute("card", card);
        return "cardDetails";  // Ensure this matches the HTML file name
    }
}