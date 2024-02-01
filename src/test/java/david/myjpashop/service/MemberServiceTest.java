package david.myjpashop.service;

import david.myjpashop.domain.Address;
import david.myjpashop.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest
{
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void init()
    {
        memberService.clearAll();
    }

    @Test
    void join()
    {

        Address addressA = new Address("cityA", "streetA", "zipcodeA");
        Member memberA = new Member("memberA", addressA, null);
        memberService.join(memberA);

        Address addressB = new Address("cityB", "streetB", "zipcodeB");
        Member memberB = new Member("memberB", addressB, null);
        memberService.join(memberB);

        Member member = new Member("memberA", null, null);
        assertThrows(IllegalStateException.class, () ->
        {
            memberService.join(member);
        });
    }

    @Test
    void findAll()
    {
        Address addressA = new Address("cityA", "streetA", "zipcodeA");
        Member memberA = new Member("memberA", addressA, null);
        memberService.join(memberA);

        Address addressB = new Address("cityB", "streetB", "zipcodeB");
        Member memberB = new Member("memberB", addressB, null);
        memberService.join(memberB);

        List<Member> list = memberService.findMembers();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void findById()
    {
        Address addressA = new Address("cityA", "streetA", "zipcodeA");
        Member memberA = new Member("memberA", addressA, null);
        memberService.join(memberA);

        Address addressB = new Address("cityB", "streetB", "zipcodeB");
        Member memberB = new Member("memberB", addressB, null);
        memberService.join(memberB);

        Member findMember = memberService.findMember(memberA.getId());
    }
}