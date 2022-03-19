import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@IdClass(Subscription.Id.class)
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @javax.persistence.Id
    @Column(name = "student_id")
    private int studentId;
    @javax.persistence.Id
    @Column(name = "course_id")
    private int courseId;
    @Column(name = "subscription_date")
    private Date subcriptionDate;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Courses course;

    @EqualsAndHashCode
    @ToString
    @Embeddable
    public static class Id implements Serializable {

        @Getter
        @Setter
        @Column(name = "student_id", updatable = false, insertable = false)
        protected int studentId;
        @Getter
        @Setter
        @Column(name = "course_id", updatable = false, insertable = false)
        protected int courseId;

        public Id() {
        }

    }
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentID) {
        this.studentId = studentID;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int coursrID) {
        this.courseId = coursrID;
    }
    public Date getSubcriptionDate() {
        return subcriptionDate;
    }
    public void setSubcriptionDate(Date subcriptionDate) {
        this.subcriptionDate = subcriptionDate;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Courses getCourse() {
        return course;
    }
    public void setCourse(Courses course) {
        this.course = course;
    }
}
