package jpabook.jpashop.Service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.Exception.NotEnoughStockExcepion;
import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(SpringExtension.class) //또는 @SpringBootTest
@SpringBootTest //Spring 띄운 상태에서 TEST 하기 때문에 해당 어노테이션이 없으면 @Autowired 사용 못함.
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager entityManager; // rollback하지만 DB쿼리 날리는거 보고 싶다면 사용한다.

    @Test
    void addStockQuantity() {
        // given
        Book book = new Book();
        book.setStockQuantity(10);

        // when
        book.addStockQuantity(5);

        // then
        entityManager.flush();
        assertEquals(15, book.getStockQuantity());
    }

    @Test
    void removeStockQuantity_success() {
        // given
        Book book = new Book();
        book.setStockQuantity(10);

        // when
        book.removeStockQuantity(3);

        // then
        entityManager.flush();
        assertEquals(7, book.getStockQuantity());
    }

    @Test
    void removeStockQuantity_fail() {
        // given
        Book book = new Book();
        book.setStockQuantity(5);

        // when & then
        entityManager.flush();
        assertThrows(NotEnoughStockExcepion.class, () -> book.removeStockQuantity(10));
    }
}