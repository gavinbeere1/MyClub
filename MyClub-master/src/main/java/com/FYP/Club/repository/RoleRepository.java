package com.FYP.Club.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FYP.Club.model.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
//		UserLogin findByEmail(String email);
//		UserLogin findByEmailAndPassword(String email, String password);

		Role findByRole(String role);
}
