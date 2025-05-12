package com.wccg.well_c_code_git_backend.global.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "s3")
public class S3Properties {
    private String accessKey;
    private String secretKey;
    private String regionStatic;
    private String bucketName;
}
