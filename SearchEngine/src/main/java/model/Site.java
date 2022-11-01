package model;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "site")
public class Site implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "status")
    private StatusSite status;

    @Column (name = "status_time")
    private Date statusTime;

    @Column (name = "last_error")
    private String lastError;

    @Column (name = "url")
    private String url;

     @Column (name = "name")
    private String name;

    enum StatusSite {
        INDEXING,
        INDEXED,
        FAILED }
    }
