package searchengine.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Index;

import java.util.List;

public interface IndexRepository extends JpaRepository<Index, Long> {


     List<Index> findByPageIdIn(List<Integer> pageId);

    @Transactional
    @Modifying
    @Query(value = "select i.page_id, sum(i.rank) from search_engine.index i where page_id in " +
            "(select page_id from search_engine.index where lemma_id in (?1) group by page_id having count(page_id) = ?2) group by i.page_id", nativeQuery = true)
    List<Object[]> findPageIdAndRankFromLemmaIdWithCountLemmaId(List <Integer> listLemmas, long countLemmas);
}
