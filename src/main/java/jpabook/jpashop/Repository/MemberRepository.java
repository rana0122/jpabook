package jpabook.jpashop.Repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자 만들어줌. spring data JPA 가 없으면 해당 코드 안됨.
public class MemberRepository {

//    @PersistenceContext //JPA의 EntityManager를 주입하기 위해 사용, EntityManager는 JPA에서 데이터베이스와의 상호작용을 담당.
    private final EntityManager em;
//    @PersistenceUnit //@PersistenceUnit은 EntityManagerFactory를 주입하기 위해 사용.EntityManagerFactory는 여러 EntityManager 인스턴스를 생성하는 데 사용되는 팩토리
//    private EntityManagerFactory emf;
    public void save(Member member) {
        em.persist(member);
    }

//    find 메서드는 해당 엔티티가 존재하지 않으면 null을 반환하고, 데이터베이스에서 해당 엔티티를 찾아 Member 객체로 반환
    public Member findOne(Long id) {
        return em.find(Member.class, id); //첫 번째 매개변수로 조회할 엔티티의 클래스(Member.class), 두 번째 매개변수로 기본 키 값(여기서는 id)을 전달합니다.
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //sql은 table, JPQL은 Entity대상으로 조회
                .getResultList(); //ctrl+alt+N 인라인으로 변경
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
