package model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table (name = "page")
public class Page implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "path")
    private String path;

    @Column(name = "code")
    private int code;

    @Column(name = "content", length = 2000000)
    private String content;




}