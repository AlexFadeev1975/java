import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@IdClass(LinkedPurchaseList.Id.class)
@Entity
@Table(name = "linkedpurchaselist")
public class LinkedPurchaseList {
    @javax.persistence.Id
    @Column(name = "student_id")
    private int studentId;
    @javax.persistence.Id
    @Column(name = "course_id")
    private int courseId;

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


    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
