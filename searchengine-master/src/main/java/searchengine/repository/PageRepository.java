package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.model.Page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public interface PageRepository extends JpaRepository <Page, Integer>  {

    @Query ("select p from Page p where p.idSite = :idSite")
    List<Page> findAllPagesByIdSite(int idSite);
    @Query ("select p from Page p where p.id = :listPageId")
    List <Page> findAllPagesById (List<Integer> listPageId);

    }

