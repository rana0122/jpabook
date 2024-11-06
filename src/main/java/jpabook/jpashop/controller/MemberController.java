package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.domain.item.Address;
import jpabook.jpashop.domain.item.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "/members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member =new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String showAllMembers(Model model){
//        List<Member> members = memberService.findMembers();
//        model.addAttribute("members", members);
//        return "members/memberList";
// ctrl+alt+shift+T : 인라인화
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

}
