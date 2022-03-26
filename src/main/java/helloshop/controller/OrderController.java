package helloshop.controller;

import helloshop.entity.Member;
import helloshop.entity.OrderSearch;
import helloshop.entity.item.Item;
import helloshop.service.ItemService;
import helloshop.service.MemberService;
import helloshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createOrderForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "/orders/createOrderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId, @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String createOrderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        model.addAttribute("orders", orderService.findOrders(orderSearch));
        return "/orders/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orders(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}
