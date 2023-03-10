package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import searchengine.model.Page;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, Integer> {

    List<Page> findAllByIdSite(int idSite);

}
