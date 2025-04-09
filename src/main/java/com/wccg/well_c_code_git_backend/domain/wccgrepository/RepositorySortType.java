package com.wccg.well_c_code_git_backend.domain.wccgrepository;

import com.wccg.well_c_code_git_backend.global.exception.exceptions.InvalidSortParameterException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum RepositorySortType {
    GITHUB_CREATED_AT("리포지토리 생성 일자 기준"),
    GITHUB_UPDATED_AT("리포지토리 업데이트 일자 기준"),
    STAR("리포지토리 스타 갯수 기준");

    private final String description;

    public static RepositorySortType from(String input) {
        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(input.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase()))
                .findFirst()
                .orElseThrow(InvalidSortParameterException::new);
    }
}
