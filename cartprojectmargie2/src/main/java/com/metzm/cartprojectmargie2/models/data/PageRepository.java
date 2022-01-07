package com.metzm.cartprojectmargie2.models.data;

//import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page,Integer> {

        Page findBySlug(String slug);


//    @Override
//    List<Page> findAll();
}
