package david.myjpashop.domain;

import david.myjpashop.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //==생성메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel()
    {
        getItem().addStock(count);
    }
}
