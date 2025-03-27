package com.splitwise.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.splitwise.mini.entity.Team;
import com.splitwise.mini.entity.Users;


@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {
	List<Team> findByCreatedBy_UserId(Integer userId);
    List<Team> findByTeamIdIn(List<Integer> teamIds);
}
