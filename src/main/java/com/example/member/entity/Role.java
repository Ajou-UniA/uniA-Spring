package com.example.member.entity;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    Role(String value) {
        this.value = value;
    }

    private String value;
}
