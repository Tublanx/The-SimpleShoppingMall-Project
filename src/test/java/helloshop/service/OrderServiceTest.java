package helloshop.service;

import helloshop.entity.Address;
import helloshop.entity.Member;
import helloshop.entity.Order;
import helloshop.entity.OrderStatus;
import helloshop.entity.item.Book;
import helloshop.entity.item.Item;
import helloshop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void order() throws Exception {
        Member member = createMember();
        Book book = createBook("시골JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        assertEquals(1, getOrder.getOrderItems().size());
        assertEquals(10000 * 2, getOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    @Test
    void over() throws Exception {
        Member member = createMember();
        Book book = createBook("시골JPA", 10000, 10);

        int orderCount = 11;

        orderService.order(member.getId(), book.getId(), orderCount);

        fail("재고 수량 초과 예외가 발생");
    }

    private Member createMember() {
        Member member = new Member();
        Address address = new Address("서울", "강가", "123-123");

        member.setName("회원1");
        member.setAddress(address);

        em.persist(member);

        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();

        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);

        em.persist(book);

        return book;
    }
}