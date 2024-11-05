package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.item.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자를 만들어줌.
public class MemberService {


    private final MemberRepository memberRepository;

    //회원가입
    @Transactional // default : (readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findmembers= memberRepository.findByName(member.getName());
        if(!findmembers.isEmpty()) {
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public  List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
