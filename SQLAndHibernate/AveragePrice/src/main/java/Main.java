import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.TypedQuery;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        TypedQuery<Courses> query = session.createNativeQuery("select * from courses i", Courses.class);
        List<Courses> coursesList = query.getResultList();
        for (Courses course : coursesList) {
            System.out.println(course.getName() + "  " + course.getStudentsCount());
        }
        sessionFactory.close();
    }
}
