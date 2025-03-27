package com.splitwise.mini.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberId implements java.io.Serializable {
    private Integer team;
    private Integer user;
}