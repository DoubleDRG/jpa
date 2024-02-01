package david.myjpashop.service;

import david.myjpashop.domain.*;
import david.myjpashop.domain.item.Book;
import david.myjpashop.domain.item.Item;
import david.myjpashop.exception.NotEnoughStockException;
import david.myjpashop.repository.MemberRepository;
import david.myjpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest
{
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void 주문()
    {
        Member member = new Member();
        member.setName("david");
        member.setAddress(new Address("서울", "강가", "123-123"));
        entityManager.persist(member);

        Item item = new Book();
        item.setName("book1");
        item.setPrice(10000);
        item.setStockQuantity(10);
        entityManager.persist(item);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order order = orderRepository.findById(orderId);

        assertThat(order.getTotalPrice()).isEqualTo(orderCount * item.getPrice());
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }

    @Test
    void 상품주문_재고수량초과()
    {
        Member member = new Member();
        member.setName("david");
        member.setAddress(new Address("서울", "강가", "123-123"));
        entityManager.persist(member);

        Item item = new Book();
        item.setName("book1");
        item.setPrice(10000);
        item.setStockQuantity(10);
        entityManager.persist(item);

        int orderCount = 20;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    @Test
    void 주문취소()
    {
        Member member = new Member();
        member.setName("david");
        member.setAddress(new Address("서울", "강가", "123-123"));
        entityManager.persist(member);

        Item item = new Book();
        item.setName("book1");
        item.setPrice(10000);
        item.setStockQuantity(10);
        entityManager.persist(item);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order order = orderRepository.findById(orderId);
        Delivery delivery = new Delivery(order, member.getAddress(), DeliveryStatus.READY);
        order.setDelivery(delivery);

        order.cancel();

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);

    }
}