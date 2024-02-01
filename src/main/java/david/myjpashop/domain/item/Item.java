package david.myjpashop.domain.item;

import david.myjpashop.domain.Category;
import david.myjpashop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static jakarta.persistence.InheritanceType.*;
import static lombok.AccessLevel.*;

@Getter
@Setter
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public abstract class Item
{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int stockQuantity)
    {
        this.stockQuantity += stockQuantity;
    }

    public void removeStock(int count)
    {
        int restStock = this.stockQuantity - count;
        if (restStock < 0)
        {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
