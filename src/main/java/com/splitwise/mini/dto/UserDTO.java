package com.splitwise.mini.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer userId; // Changed to Long for consistency with entity
    private String username;
    private String email;
}
