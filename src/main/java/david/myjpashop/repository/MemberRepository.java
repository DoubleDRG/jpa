package david.myjpashop.repository;

import david.myjpashop.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository
{
    private final EntityManager entityManager;

    public Member save(Member member)
    {
        entityManager.persist(member);
        return member;
    }

    public List<Member> findAll()
    {
        String query = "select m from Member m";
        return entityManager.createQuery(query, Member.class)
                .getResultList();
    }

    public Member findById(Long id)
    {
        return entityManager.find(Member.class, id);
    }

    public Optional<Member> findByName(String name)
    {
        String query = "select m from Member m where m.name =:name";
        return entityManager.createQuery(query, Member.class)
                .setParameter("name", name)
                .getResultList()
                .stream().findAny();
    }

    @Transactional
    public void clearAll()
    {
        entityManager.clear();
    }
}
