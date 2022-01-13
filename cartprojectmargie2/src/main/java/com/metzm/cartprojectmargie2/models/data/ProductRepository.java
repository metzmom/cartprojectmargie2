package com.metzm.cartprojectmargie2.models.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Integer> {


    Product findBySlug(String slug);

    Product findBySlugAndIdNot(String slug, int id);

}
