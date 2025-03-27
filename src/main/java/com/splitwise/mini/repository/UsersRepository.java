package com.splitwise.mini.repository;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.splitwise.mini.entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
	Users findByUsername(String username);
	Users findByEmail(String email);
	 List<Users> findByEmailIn(List<String> emails);
	 List<Users> findByUserIdIn(List<Integer> userIds);
}
