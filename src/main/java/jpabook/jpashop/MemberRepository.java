package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
//    @Autowired 대신 @PersistenceContext 사용 이유
//    @PersistenceContext는 트랜잭션 관리를 고려한 EntityManager 주입을 보장하기 때문에,
//    JPA의 표준 스펙에 맞게 EntityManager를 주입하려면 @Autowired 대신 @PersistenceContext를 사용해야 합니다.
//    Spring Data JPA는 @PersistenceContext를 사용하여 트랜잭션 내에서 안전하게 EntityManager를 관리합니다.
    @PersistenceContext
    private EntityManager em;

    public Long save(final Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find( Long id) {
        return  em.find(Member.class, id);
    }
}
