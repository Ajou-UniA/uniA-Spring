package com.example.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Setter @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "time_table")
public class TimeTable {
    @Id @GeneratedValue
    private Long id;//number

    private String College;
    private String Major;
    private String CourseName;
    private String Professor;
    private String Code;
    private String Courseschedul;
    private String Credits;

}
