//package org.acme.repository;
//
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import org.acme.model.Faq;
//
//import java.util.List;
//
//@ApplicationScoped
//public class FaqRepository implements PanacheRepository<Faq> {
//
//    public List<Faq> findbyFaqId(Long faqId) {
//        return list("id", faqId);
//    }
//
//    public List<Faq> findbyAdminId(Long adminId) {
//        return list("admin.id", adminId);
//    }
//
//    public List<Faq> findbyStatus(String status) {
//        return list("status", status);
//    }
//
//}
