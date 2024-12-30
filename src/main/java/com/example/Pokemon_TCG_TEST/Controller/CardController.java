package com.example.Pokemon_TCG_TEST.Controller;

import com.example.Pokemon_TCG_TEST.Model.Card;
import com.example.Pokemon_TCG_TEST.Service.CardService;
import com.example.Pokemon_TCG_TEST.Utilities.SetResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    // Display the search form
    @GetMapping("/search")
    public String showSearchPage(Model model) {

//        //check if user is logged in
//        if (session.getAttribute("user") == null) {
//            return "redirect:/";
//        }

        List<SetResponse.SetDetails> sets = cardService.getAvailableSets();
        List<String> types = cardService.getAvailableTypes();
        List<String> rarities = cardService.getAvailableRarities();

        //sorts the filters alphabetically
        sets.sort(Comparator.comparing(SetResponse.SetDetails::getName));

        Collections.sort(types);

        Collections.sort(rarities);

        //add available sets, types, rarities to the model, which is used in the search form
        model.addAttribute("sets", sets);
        model.addAttribute("types", types);
        model.addAttribute("rarities", rarities);
        return "search";  // Name of the Thymeleaf template
    }

    // Handle the search request
    @GetMapping("/cards/search")
    public String searchCards(
            @RequestParam(required = false) String name, //card name
            @RequestParam(required = false) String set, //which set the card is from
            @RequestParam(required = false) String type, //card type
            @RequestParam(required = false) String rarity, //card rarity
            @RequestParam(defaultValue = "1") int page, //current page number
            @RequestParam(defaultValue = "12") int pageSize, //number of results per page
            Model model
    ) {

        //fetch the cards based on params
        List<Card> cards = cardService.searchCards(name, set, type, rarity, page, pageSize);

        // Calculate total pages based on amount of cards and page size
        int totalCards = cardService.getTotalCardCount(name, set, type, rarity);
        int totalPages = (int) Math.ceil((double) totalCards / pageSize);
        //math.ceil() rounds the result of the math to the next whole number (2.5 will become 3)
        //helps with creating an extra page to display the remainder cards

        // add card and page details to model
        model.addAttribute("cards", cards);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // pass search params back to the view to maintain the search context
        model.addAttribute("name", name);
        model.addAttribute("set", set);
        model.addAttribute("type", type);
        model.addAttribute("rarity", rarity);
        model.addAttribute("pageSize", pageSize);

        return "searchResults";
    }

    //display card's details
    @GetMapping("/cards/{id}")
    public String getCardDetails(
            @PathVariable String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String set,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String rarity,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int pageSize,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            Model model
    ) {

        //check if user is logged in, if not then redirect to login page to either login or register
        if (session.getAttribute("user") == null) {
            redirectAttributes.addFlashAttribute("error", "You need to be logged in to view full card details.");
            return "redirect:/";
        }



        //fetch card details by it's id
        Card card = cardService.getCardById(id);

        if (card == null) {
            // error page if the card is not found
            model.addAttribute("errorMessage", "Card not found: " + id);
            return "error";
        }

        // add card and search parameters to the model
        model.addAttribute("card", card);
        model.addAttribute("name", name != null ? name : "");
        model.addAttribute("set", set != null ? set : "");
        model.addAttribute("type", type != null ? type : "");
        model.addAttribute("rarity", rarity != null ? rarity : "");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);

        return "cardDetails";
    }
}