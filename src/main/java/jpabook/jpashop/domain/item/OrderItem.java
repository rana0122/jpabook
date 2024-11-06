package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED) //protected OrderItem() {} 와 동일
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne( fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne( fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int  orderPrice; //주문가격
    private int count; //주문 수량

//    protected OrderItem() {}
    //==생성메소드==//
     public  static OrderItem createOrderItem(Item item, int orderPrice, int count) {
         OrderItem orderItem = new OrderItem();
         orderItem.setItem(item);
         orderItem.setOrderPrice(orderPrice);
         orderItem.setCount(count);

         item.removeStockQuantity(count);
         return orderItem;
     }
    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStockQuantity(count);
    }

    //==조회 로직==//
    // 주문상품 전체 가격조회
    public int getTotalPrice() {
        return  getOrderPrice() * getCount();
    }
}
