import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();


        List<Student> students = session.createQuery("select i from Student i ", Student.class).getResultList();
        for (int i = 0; i < students.size(); i++) {

            Integer studentId = students.get(i).getId();
            Set<Courses> courses = students.get(i).getCoursesSet();

            for (Courses course : courses) {
                Transaction transaction = session.beginTransaction();
                LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
                linkedPurchaseList.setStudentId(studentId);
                linkedPurchaseList.setCourseId(course.getId());
                session.saveOrUpdate(linkedPurchaseList);
                transaction.commit();
            }
        }
        sessionFactory.close();
    }
}

