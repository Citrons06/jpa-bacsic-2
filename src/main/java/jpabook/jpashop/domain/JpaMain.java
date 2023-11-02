package jpabook.jpashop.domain;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();  //트랜잭션 시작

        try {

            Member member = new Member();
            member.setName("JPA1");

            

            em.persist(member);

            em.flush();
            em.clear();

            Member referenceMember = em.getReference(Member.class, member.getId());
            System.out.println("referenceMember = " + referenceMember.getClass());  //Proxy

            referenceMember.getName();

            Hibernate.initialize(referenceMember);  //강제 초기화



            referenceMember.getName();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(Member m1, Member m2) {
        System.out.println("m1 == m2: " + (m1 instanceof Member));
    }
}
