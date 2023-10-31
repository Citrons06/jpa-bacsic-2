package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();  //트랜잭션 시작

        try {

            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setTeam(team);
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            Team newTeam = em.find(Team.class, 3L);
            findMember.setTeam(newTeam);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
