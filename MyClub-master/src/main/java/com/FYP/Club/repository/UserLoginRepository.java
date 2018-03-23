package com.FYP.Club.repository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;






import com.FYP.Club.model.UserLogin;


@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long>
{


		UserLogin findByUserNameAndPassword(String userName, String password);

		UserLogin findByUserName(String name);

		ArrayList<UserLogin> findByUserType(String type);
		//newly added below may cause problems
		
//		List<UserLogin> findByUserType2(String type);
}
