package helloshop.repository;

import helloshop.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepositoryOld {

    @PersistenceContext // 엔티티 매니저 주입
    EntityManager em;

    public void save(Member member) {
        em.persist(member); // 회원 엔티티 저장
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 회원 번호로 회원 엔티티 조회
    }

    public List<Member> findAll() {
        return em.createQuery("select m from member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList(); // JPQL을 이용하여 이름으로 회원 엔티티 조회
    }

}
