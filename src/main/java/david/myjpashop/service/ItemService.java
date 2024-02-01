package david.myjpashop.service;

import david.myjpashop.domain.item.Item;
import david.myjpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService
{
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item)
    {
        itemRepository.save(item);
    }

    public List<Item> findItems()
    {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId)
    {
        return itemRepository.findById(itemId);
    }
}
