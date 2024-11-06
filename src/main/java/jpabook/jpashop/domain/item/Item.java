package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.Exception.NotEnoughStockExcepion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//SINGLE_TABLE : 한 테이블에 몰아준다.(ITEM)
//TABLE_PER_CLASS : 구현 클래스당 테이블이 나오도록 한다.
//JOINED : 가장 정규화 스타일
@DiscriminatorColumn(name = "dtype")
@Setter @Getter
public abstract class Item {

    @Id@GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany // 다대다이므로 list로 Category 가짐
    @JoinTable(name="category_item",
                joinColumns = @JoinColumn(name="category_id"),
                inverseJoinColumns = @JoinColumn(name="item_id"))
    private List<Category> categories = new ArrayList<>();


    //==비즈니스 로직==//
    
    //재고증가
    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }
    //재고감소
    public void removeStockQuantity(int quantity) {
        int restStock=this.stockQuantity - quantity;
        if(restStock<0) {
            throw new NotEnoughStockExcepion("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
