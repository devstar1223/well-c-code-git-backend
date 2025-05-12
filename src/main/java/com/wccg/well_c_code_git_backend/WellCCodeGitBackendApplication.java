package com.wccg.well_c_code_git_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(
		exclude = {
				io.awspring.cloud.autoconfigure.s3.S3AutoConfiguration.class
		}
)
public class WellCCodeGitBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellCCodeGitBackendApplication.class, args);
	}

}
