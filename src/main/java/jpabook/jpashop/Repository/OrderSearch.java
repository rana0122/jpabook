package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.item.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memebrName; // 회원 이름
    private OrderStatus orderStatus; // 주문 상태[ORDER,CANCEL]
}
