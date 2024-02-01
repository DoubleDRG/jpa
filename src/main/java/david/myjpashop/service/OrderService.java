package david.myjpashop.service;

import david.myjpashop.domain.*;
import david.myjpashop.domain.item.Item;
import david.myjpashop.repository.ItemRepository;
import david.myjpashop.repository.MemberRepository;
import david.myjpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService
{
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count)
    {
        Member member = memberRepository.findById(memberId);
        Item item = itemRepository.findById(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId)
    {
        Order order = orderRepository.findById(orderId);
        order.setStatus(OrderStatus.CANCEL);
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch)
    {
        return orderRepository.findAllByString(orderSearch);
    }
}
