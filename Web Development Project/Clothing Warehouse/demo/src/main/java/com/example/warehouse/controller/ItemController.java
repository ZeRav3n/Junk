package com.example.warehouse.controller;

import com.example.warehouse.model.FashionBrand;
import com.example.warehouse.model.Item;
import com.example.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items/add")
    public String showAddItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "add-item";
    }

    @PostMapping("/items/add")
    public String processAddItemForm(@Valid Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-item";
        }
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String listItems(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "id") String sortBy,
                            @RequestParam(required = false) FashionBrand filterBrand) {
        Sort sort = Sort.by(sortBy);
        if (filterBrand != null) {
            model.addAttribute("items", itemRepository.findByBrandAndYear2022(filterBrand));
        } else {
            model.addAttribute("items", itemRepository.findAll(PageRequest.of(page, size, sort)).getContent());
        }
        return "items";
    }
    @RequestMapping("/items/manage")
    public String manageItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "manage-items";
    }

    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/items/manage";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/login?logout";
    }
}
