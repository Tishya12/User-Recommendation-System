package com.example.recommendation.model;

import com.example.recommendation.enums.Domain;
import com.example.recommendation.enums.Skill;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDetails {
    @Id
    private Long id;

    private String name;

    @NotNull(message = "DOJ can not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfJoining;

    private Domain domain;

    @NotNull(message = "Skill can not be null")
    private Skill skill;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid mobile number entered")
    private String mobile;

    public UserDetails(String name, String dateOfJoining, Skill skill, String mobile) {
        this.name = name;
        this.dateOfJoining = LocalDate.parse(dateOfJoining);
        this.skill = skill;
        this.mobile = mobile;
    }
}
