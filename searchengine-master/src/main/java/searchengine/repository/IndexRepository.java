package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import searchengine.model.Index;
import searchengine.model.Lemma;

import java.util.List;

public interface IndexRepository extends JpaRepository<Index, Integer> {

    @Query (value = "select distinct page_id, lemma_id, `rank` from `index` WHERE  lemma_id in (?) ;", nativeQuery = true)
    public List<Index> getPageLemmaIdFromListLemmas(String stringQuery);

}
