package com.metzm.cartprojectmargie2.models.data;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageRepository extends JpaRepository<Page,Integer> {

        Page findBySlug(String slug);



         @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
         Page findBySlug(int id, String slug);

      //  Page findBySlugAndIdNot(String slug, int id);


//    @Override
//    List<Page> findAll();
}
