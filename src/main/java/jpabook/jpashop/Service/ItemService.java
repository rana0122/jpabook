package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //서비스에 반드시 필요하다.
@RequiredArgsConstructor //final 필드에 대하여 생성자 자동으로 만듬.
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // default : (readOnly = false)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }


}
