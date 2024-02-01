package david.myjpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@Setter
@Entity
public class Delivery
{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private DeliveryStatus status;

    public Delivery()
    {
    }

    public Delivery(Order order, Address address, DeliveryStatus status)
    {
        this.order = order;
        this.address = address;
        this.status = status;
    }
}
