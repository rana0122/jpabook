package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.item.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class) //또는 @SpringBootTest
@SpringBootTest //Spring 띄운 상태에서 TEST 하기 때문에 해당 어노테이션이 없으면 @Autowired 사용 못함.
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
//    @Autowired
//    EntityManager entityManager; // rollback하지만 DB쿼리 날리는거 보고 싶다면 사용한다.

    @Test
//    @Commit
    public void join() throws Exception {
        //given
        Member member = new Member();
        member.setName("John");

        //when
        Long savedId = memberService.join(member);

        //then
//        entityManager.flush(); // rollback하지만 DB쿼리 날리는거 보고 싶다면 사용한다.
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test
    public void validateDuplicateMember() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("John");

        Member member2 = new Member();
        member2.setName("John");

        //when
        memberService.join(member1);
        try{
            memberService.join(member2);  // 같은 이름으로 insert하기 때문에 예외 발생!!
        }catch (Exception e){
            return;
        }
        //then
         fail("에외발생");
    }

}