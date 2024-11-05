package jpabook.jpashop.domain.item;

import jakarta.persistence.Embeddable;
import lombok.Getter;
//JPA 스펙상 엔티티나 임베디드 타입은 자바 기본 생성자를 protected로 둬야함.
// JPA구현 라이브러리가 객체 생성할대 리플랙션 기술 사용할 수 있도록 지원위해서 기본 생성자 있어야 함.
@Embeddable
@Getter //값 타입은 변경 불가능하게 설계 Setter 안됨.
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
