package jpabook.jpashop.controller;

import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.Service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String craeteForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }
}
