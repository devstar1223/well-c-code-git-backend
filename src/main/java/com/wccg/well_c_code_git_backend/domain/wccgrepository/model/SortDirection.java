package com.wccg.well_c_code_git_backend.domain.wccgrepository.model;

import com.wccg.well_c_code_git_backend.global.exception.exceptions.InvalidSortParameterException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SortDirection {
    ASC("오름차순"),
    DESC("내림차순");

    private final String description;

    public static SortDirection from(String input) {
        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(input.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase()))
                .findFirst()
                .orElseThrow(InvalidSortParameterException::new);
    }
}
