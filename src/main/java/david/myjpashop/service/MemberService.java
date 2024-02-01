package david.myjpashop.service;

import david.myjpashop.domain.Member;
import david.myjpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService
{
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member)
    {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }

    public Member findMember(Long id)
    {
        return memberRepository.findById(id);
    }

    private void validateDuplicateMember(Member member)
    {
        Optional<Member> findMember = memberRepository.findByName(member.getName());
        if (findMember.isPresent())
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void clearAll()
    {
        memberRepository.clearAll();
    }
}
