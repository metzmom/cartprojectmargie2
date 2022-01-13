package com.metzm.cartprojectmargie2.models.data;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PageRepository extends JpaRepository<Page,Integer> {

    /*  Page findBySlug(String slug);


        //iIF SLUG eEXISTS, BUT NOT FOR PAGE BEING EDITED
         //@Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
         Page findBySlug(String slug,int id); // NOTED BELOW IS CLEANER WAY TO DO THIS QUERY  but you will have to change
         // the Admin PostMapping  method needs to be updated  AdminPage:  slugExists = pageRepo.findBySlugAndIDNot(page.getId(), slug);
         // if slug exists but not for this page

          //  Page findBySlugAndIdNot(String slug, int id);
          List<Page> findAllByOrderBySortingAsc();*/

 Page findBySlug(String slug);

    // @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
    // Page findBySlug(int id, String slug);

    Page findBySlugAndIdNot(String slug, int id);

    List<Page> findAllByOrderBySortingAsc();
}