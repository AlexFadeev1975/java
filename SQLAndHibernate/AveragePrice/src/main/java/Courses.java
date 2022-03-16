import javax.persistence.*;

@Entity
@Table(name = "Courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int duration;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private CourseTypes type;
    private String description;
    @Column(name = "teacher_id")
    private int teacherID;
    @Column(name = "students_count")
    private Integer studentsCount;
    private int price;
    @Column(name = "price_per_hour")
    private float pricePerHour;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setType(CourseTypes type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPricePerHour(float pricePerHours) {
        this.pricePerHour = pricePerHours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public CourseTypes getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public int getPrice() {
        return price;
    }

    public float getPricePerHour() {
        return pricePerHour;
    }
}
