package helloshop.controller;

import helloshop.dto.BookForm;
import helloshop.entity.item.Book;
import helloshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @RequestMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @RequestMapping(value = "/items")
    public String itemList(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }

    @RequestMapping(value = "/items/new", method = RequestMethod.POST)
    public String create(BookForm bookForm) {
        Book book = new Book();

        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @RequestMapping(value = "/items/{itemId}/edit", method = RequestMethod.GET)
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("item", itemService.findOne(itemId));
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("item") Book item) {
        itemService.saveItem(item);
        return "redirect:/items";
    }

}
