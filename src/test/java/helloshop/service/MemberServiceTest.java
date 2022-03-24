package helloshop.service;

import helloshop.entity.Member;
import helloshop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepositoryOld memberRepository;

    @Test
    void join() throws Exception {
        Member member = new Member();
        member.setName("lgh");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    void 중복회원예외() throws Exception {
        Member member1 = new Member();
        member1.setName("lgh");

        Member member2 = new Member();
        member2.setName("lgh");

        memberService.join(member1);
        memberService.join(member2);

        fail("예외 발생");
    }
}