package com.wccg.well_c_code_git_backend.global.s3;

import com.wccg.well_c_code_git_backend.global.exception.exceptions.S3FileUploadFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public String upload(MultipartFile file,UploadFileType fileType){
        try {
            String extension = getFileExtension(file);

            String fileName = createFileName(fileType, extension);

            uploadToS3(file, fileName);

            return createFileUrl(fileName);
        }
        catch (Exception e){
            log.error("S3 업로드 실패 : ", e);
            throw new S3FileUploadFailedException();
        }
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    private String createFileName(UploadFileType fileType, String extension) {
        return fileType.getDefaultPrefix() + UUID.randomUUID() + extension;
    }

    private void uploadToS3(MultipartFile file, String fileName) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    private String createFileUrl(String fileName) {
        return "https://" + s3Properties.getBucketName() + ".s3.amazonaws.com/" + fileName;
    }
}

