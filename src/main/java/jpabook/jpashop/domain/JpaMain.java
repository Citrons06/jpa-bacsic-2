package jpabook.jpashop.domain;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();  //트랜잭션 시작

        try {
            Member member = new Member();
            member.setName("user");
            member.setHomeAddress(new Address("city", "street", "zipcode"));

            member.getFoods().add("마라엽떡");
            member.getFoods().add("치킨");
            member.getFoods().add("샤브샤브");

            member.getAddressHistory().add(new AddressEntity("old1", "street1", "zipcode1"));
            member.getAddressHistory().add(new AddressEntity("old2", "street2", "zipcode2"));

            em.persist(member);
            
            em.flush();
            em.clear();

            System.out.println("=========== START ===========");
            Member findMember = em.find(Member.class, member.getId());

            //findMember.getHomeAddress().setCity("newCity"); //X
            Address old = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", old.getStreet(), old.getZipcode()));

            findMember.getFoods().remove("치킨");
            findMember.getFoods().add("한식");

            findMember.getAddressHistory().remove(new AddressEntity("old1", "street1", "zipcode1"));
            findMember.getAddressHistory().add(new AddressEntity("new1", "street1", "zipcode1"));

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
