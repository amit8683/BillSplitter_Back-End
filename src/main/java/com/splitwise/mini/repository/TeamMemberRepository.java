package com.splitwise.mini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.splitwise.mini.entity.TeamMember;
import com.splitwise.mini.entity.TeamMemberId;

@Repository
public interface TeamMemberRepository  extends JpaRepository<TeamMember, TeamMemberId> {
	@Query("SELECT tm.team.teamId FROM TeamMember tm WHERE tm.user.userId = :userId")
    List<Integer> findTeamIdsByUserId(@Param("userId") Integer userId);
	
	 List<TeamMember> findByTeam_TeamId(Integer teamId);

}
