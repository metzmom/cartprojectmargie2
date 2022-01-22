package com.metzm.cartprojectmargie2.models;


import com.metzm.cartprojectmargie2.models.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);
}
