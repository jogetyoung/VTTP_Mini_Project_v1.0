package com.example.Pokemon_TCG_TEST.Controller;

import com.example.Pokemon_TCG_TEST.Model.Card;
import com.example.Pokemon_TCG_TEST.Model.User;
import com.example.Pokemon_TCG_TEST.Service.CardService;
import com.example.Pokemon_TCG_TEST.Service.FavService;
import com.example.Pokemon_TCG_TEST.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

//    @Autowired
//    private FavService favService; // To handle favorites logic (redundant)

    @Autowired
    private UserService userService;

    // View Favorites Page
//    @GetMapping("/favorites")
//    public String viewFavorites(Model model) {
//        List<Card> favoriteCards = favService.getFavoriteCards();
//        model.addAttribute("favorites", favoriteCards);
//        return "favorites";  // Thymeleaf template for displaying favorite cards
//    }
    @GetMapping("/favorites")
    public String viewFavorites(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to view favorites.");
            return "redirect:/";
        }
        List<String> favorites = userService.getAllFavourites(user.getEmail());
        List<Card> favoriteCards = cardService.getCardsByIds(favorites); // Fetch cards by IDs
        model.addAttribute("favorites", favoriteCards);
        return "favorites";
    }

    // Add a Card to Favorites (before i implemented login and user accounts)
//    @PostMapping("/favorites/add")
//    public String addFavorite(@RequestParam String id, RedirectAttributes redirectAttributes) {
//        Card card = cardService.getCardById(id); // Fetch card details by ID
//        if (card == null) {
//            redirectAttributes.addFlashAttribute("error", "Card not found.");
//            return "redirect:/favorites";
//        }
//        boolean added = favService.addFavoriteCard(card);
//        if (added) {
//            redirectAttributes.addFlashAttribute("message", "Card added to favorites!");
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Card is already in favorites!");
//        }
//        return "redirect:/favorites";
//    }

//    @PostMapping("/favorites/add")
//    public String addFavorite(@RequestParam String cardId, HttpSession session, RedirectAttributes redirectAttributes) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            return "redirect:/";
//        }
//
//        // Validate the card ID
//        Card card = cardService.getCardById(cardId);
//        if (card == null) {
//            redirectAttributes.addFlashAttribute("error", "Invalid card ID.");
//            return "redirect:/favorites";
//        }
//
//        // Add to favorites
//        boolean added = userService.addFavourite(user.getEmail(), cardId);
//        if (added) {
//            redirectAttributes.addFlashAttribute("message", "Card added to favorites!");
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Card is already in favorites!");
//        }
//        return "redirect:/favorites";
//    }

    @PostMapping("/favorites/add")
    public String addFavorite(
            @RequestParam String cardId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You need to be logged in to add favorites.");
            return "redirect:/"; // Redirect to login if not logged in
        }

        // Validate the card ID
        Card card = cardService.getCardById(cardId);
        if (card == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid card ID.");
        } else {
            // Add to favorites
            boolean added = userService.addFavourite(user.getEmail(), cardId);
            if (added) {
                redirectAttributes.addFlashAttribute("message", "Card added to favorites!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Card is already in favorites!");
            }
        }

        // Get the Referer header to return to the same page
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/"); // Default to home if referer is null
    }


    @PostMapping("/favorites/remove")
    public String removeFavorite(@RequestParam String cardId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        userService.removeFavourite(user.getEmail(), cardId);
        model.addAttribute("message", "Card removed from favorites!");
        return "redirect:/favorites";
    }
}