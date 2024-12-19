package com.example.Pokemon_TCG_TEST.Controller;

import com.example.Pokemon_TCG_TEST.Model.Card;
import com.example.Pokemon_TCG_TEST.Service.CardService;
import com.example.Pokemon_TCG_TEST.Service.FavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FavController {

    @Autowired
    private CardService cardService; // To fetch cards from API

    @Autowired
    private FavService favService; // To handle favorites logic

    // View Favorites Page
    @GetMapping("/favorites")
    public String viewFavorites(Model model) {
        List<Card> favoriteCards = favService.getFavoriteCards();
        model.addAttribute("favorites", favoriteCards);
        return "favorites";  // Thymeleaf template for displaying favorite cards
    }

    // Add a Card to Favorites
    @PostMapping("/favorites/add")
    public String addFavorite(@RequestParam String id, RedirectAttributes redirectAttributes) {
        Card card = cardService.getCardById(id); // Fetch card details by ID
        if (card == null) {
            redirectAttributes.addFlashAttribute("error", "Card not found.");
            return "redirect:/favorites";
        }
        boolean added = favService.addFavoriteCard(card);
        if (added) {
            redirectAttributes.addFlashAttribute("message", "Card added to favorites!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Card is already in favorites!");
        }
        return "redirect:/favorites";
    }

    // Remove a Card from Favorites
    @PostMapping("/favorites/remove")
    public String removeFavorite(@RequestParam String id, RedirectAttributes redirectAttributes) {
        boolean removed = favService.removeFavoriteCard(id);
        if (removed) {
            redirectAttributes.addFlashAttribute("message", "Card removed from favorites!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Card not found in favorites!");
        }
        return "redirect:/favorites";
    }
}