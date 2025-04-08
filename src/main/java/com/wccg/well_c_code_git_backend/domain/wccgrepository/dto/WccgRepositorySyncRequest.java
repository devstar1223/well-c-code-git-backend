package com.wccg.well_c_code_git_backend.domain.wccgrepository.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WccgRepositorySyncRequest {

    private final Long userId;

    @JsonCreator
    public WccgRepositorySyncRequest(@JsonProperty("userId") Long userId) {
        this.userId = userId;
    }
}