package david.myjpashop.repository;

import david.myjpashop.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ItemRepository
{
    private final EntityManager entityManager;

    public void save(Item item)
    {
        if (item.getId() == null)
        {
            entityManager.persist(item);
        } else
        {
            entityManager.merge(item);
        }
    }

    public List<Item> findAll()
    {
        String query = "select i from Item i";
        return entityManager.createQuery(query, Item.class).getResultList();
    }

    public Item findById(Long id)
    {
        return entityManager.find(Item.class, id);
    }
}
