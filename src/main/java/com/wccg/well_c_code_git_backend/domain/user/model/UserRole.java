package com.wccg.well_c_code_git_backend.domain.user.model;

public enum UserRole {
    USER("사용자"),
    ADMIN("관리자");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }
}
