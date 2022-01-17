package com.metzm.cartprojectmargie2.models;

import com.metzm.cartprojectmargie2.models.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Integer>{

}
