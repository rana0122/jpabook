package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //왼쪽 class to 오른쪽 field
    //양방향 관계는 연관관계 주인을 정해줘야 함. 수정이 있는 경우  FK update할 주체를 찾아야함. FK가 가까운 곳을 주인으로 정함.
    //따라서  Order 의 FK인 Member를  update한다. -> 반대쪽인 Member의 Order는 mappedBy로 미러링 시켜준다.
    //이쪽에 값을 변경한다고 해서  FK값이 변경되지 않다.
    //왼쪽 class to 오른쪽 field
    //컬랙션은 의존성 주입하지말고 필드에서 초기화,hibernate가 추적을 위해 타입을 변경하는데 생성자로 다시 초기화 시키면 추적이 안됨.
    @OneToMany(mappedBy = "member") //사용자는 주문을 여러개 할 수 있다. 주문하나에 여러 사용자가 있을 수 없다.
    private List<Order> orders = new ArrayList<>();

}
