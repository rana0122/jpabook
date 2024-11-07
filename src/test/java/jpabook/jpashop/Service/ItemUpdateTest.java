package jpabook.jpashop.Service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class) //또는 @SpringBootTest
@SpringBootTest //Spring 띄운 상태에서 TEST 하기 때문에 해당 어노테이션이 없으면 @Autowired 사용 못함.
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("Book 1");
        //변경감지 ==dirty checking
        //TX commit

    }
}
