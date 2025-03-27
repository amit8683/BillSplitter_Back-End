package com.splitwise.mini.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Integer teamId;
    private String teamName;
    private Integer createdBy;
    private List<String> memberEmails;
}

