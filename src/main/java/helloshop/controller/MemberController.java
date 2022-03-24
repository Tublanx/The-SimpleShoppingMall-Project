package helloshop.controller;

import helloshop.dto.MemberForm;
import helloshop.entity.Address;
import helloshop.entity.Member;
import helloshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @RequestMapping(value = "/members/new")
    public String signPage(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @RequestMapping(value = "/members")
    public String memberList(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

    @PostMapping(value = "/members/new")
    public String join(@Validated MemberForm memberForm, BindingResult result) {
        log.info(memberForm.getName());
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();

        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

}
