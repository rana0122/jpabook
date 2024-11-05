package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;

    @Embedded
    private  Address address;

//    ORDINAL : 숫자 READY,COMP 가 각각  1,2라면 중간에 어떤 상태가 생길 경우 망함. 절대 사용하지 말것!
//    STRING :
    @Enumerated(EnumType.STRING)
    private  DeliveryStatus status; //READY,COMP
}
