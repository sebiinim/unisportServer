package com.example.unisportserver.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class InstructorVerificationDto {

    private String name;

    private String major;

    private Integer grade;

    @Override
    public String toString() {
        return "name : " + name + ", major : " + major + ", grade : " + grade;
    }
}
