package model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table (name = "\"index\"")
public class Index implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "page_id", insertable = false, updatable = false)
    private int pageId;

    @Column(name = "lemma_id", insertable = false, updatable = false)
    private int lemmaId;

    @Column(name = "\"rank\"")
    private float rank;

   public Index(int pageId, int lemmaId, long rank) {
        this.pageId = pageId;
        this.lemmaId = lemmaId;
        this.rank = rank;
    }

    public Index() {

    }
}