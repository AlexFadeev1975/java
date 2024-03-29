package searchEngine.repository;

import searchEngine.model.Index;
import searchEngine.model.Lemma;
import searchEngine.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {

    @Query(value = "SELECT i FROM Index i WHERE i.lemma IN :lemmas AND i.page IN :pages")
    List<Index> findByPagesAndLemmas(@Param("lemmas") List<Lemma> lemmaList, @Param("pages") List<Page> pageList);
}
