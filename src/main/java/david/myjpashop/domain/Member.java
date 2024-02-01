package david.myjpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@Setter
@Entity
public class Member
{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member()
    {
    }

    public Member(String name, Address address, List<Order> orders)
    {
        this.name = name;
        this.address = address;
        this.orders = orders;
    }
}

