package jpabook.jpashop.Service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.domain.item.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(SpringExtension.class) //또는 @SpringBootTest
@SpringBootTest //Spring 띄운 상태에서 TEST 하기 때문에 해당 어노테이션이 없으면 @Autowired 사용 못함.
@Transactional
class OrderServiceTest {

    @Autowired EntityManager entityManager;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void OrderItem(){
        //given
        Member member = createMember();
        Book book = createBook("Book1234",10000,10);

        int orderCount=2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order order = orderRepository.findOne(orderId);

        assertEquals(" 상품 주문시 상태는 ORDER",OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.",1,order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",10000*orderCount, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8,book.getStockQuantity());
    }



    @Test
    public void OrderItem_OverFlowStock() throws Exception{
        //given
        Member member = createMember();
        Item book = createBook("Book1234",10000,10);

        int orderCount=20;
        //when
        try{
            orderService.order(member.getId(), book.getId(), orderCount);
        } catch (Exception e) {
            return;
        }
        //then
        fail("재고수량 부족 예외가 발행해야 한다.");

    }

    @Test
    public void OrderCancle(){
        //given
        Member member = createMember();
        Book book = createBook("NewBook",10000,10);

        int orderCount=2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancleOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL이다.",OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(" 주문이 취소된 상품은 그만큼 재고가 증가해야 한다.",10,book.getStockQuantity());
    }

    private Book createBook(String name, int Price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(Price);
        book.setStockQuantity(stockQuantity);
        entityManager.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("John");
        member.setAddress(new Address("suwon","mangporo","12345"));
        entityManager.persist(member);
        return member;
    }
}