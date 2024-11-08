package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED) //protected Order() {} 와 동일
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    //왼쪽 class to 오른쪽 field
    //양방향 관계는 연관관계 주인을 정해줘야 함. 수정이 있는 경우  FK update할 주체를 찾아야함. FK가 가까운 곳을 주인으로 정함.
    //따라서  Order 의 FK인 Member를  update한다. -> 반대쪽인 Member의 Order는 mappedBy로 미러링 시켜준다.
    //Member의 Order에 값을 변경한다고 해서  FK값이 변경되지 않다.
    //Order의 Member 를 변경할 때 FK가 변경된다.
    @ManyToOne( fetch = LAZY) // 사용자는 주문을 여러개 할 수 있다. 하나의 주문은 여러 사용자가 있을 수 없다.
    @JoinColumn(name="member_id")
    private  Member member;

    //Order는 OrderItem을 여러개 가질 수 있다. OrderItem은 하나의 Order만 가질 수 있다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //order삭제시 FK참조하는 OrderItems 모두 삭제
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) //order삭제시 FK참조하는 Delivery 모두 삭제
    @JoinColumn(name="delivery_id") //Order에  FK가 있으므로 연관 관계의 주인은 Order에 둔다.
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

//    ORDINAL : 숫자 READY,COMP 가 각각  1,2라면 중간에 어떤 상태가 생길 경우 망함. 절대 사용하지 말것!
//    STRING :
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 ORDER, CANCEL

    //==연관관계 편의 메소드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    //==생성 메소드==//
    public static  Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;

    }

    //== 비즈니스 로직==//
    //주문취소
    public  void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    //==조회 로직==//
    //전체 주문 가격 조회
    public int getTotalPrice(){
//        int totalPrice = 0;
//        for(OrderItem orderItem : orderItems) {
//            totalPrice+=orderItem.getTotalPrice();
//        }
//        return totalPrice;
        //alt+enter: 코드변경, ctrl+alt+N : 인라인으로 변경
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
