package com.wccg.well_c_code_git_backend.global.s3;

import com.wccg.well_c_code_git_backend.global.exception.exceptions.S3FileUploadFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public String upload(MultipartFile file,UploadFileType fileType){
        try {
            String fileName = fileType.getDefaultPrefix() + UUID.randomUUID();

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return "https://" + s3Properties.getBucketName() + ".s3.amazonaws.com/" + fileName;
        }
        catch (Exception e){
            log.error("S3 업로드 실패 : ", e);
            throw new S3FileUploadFailedException();
        }
    }
}

