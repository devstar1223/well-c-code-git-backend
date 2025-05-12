package com.wccg.well_c_code_git_backend.domain.user.service;

import com.wccg.well_c_code_git_backend.global.exception.exceptions.ImageTooLarge;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.InvalidImageDimensions;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.InvalidImageExtension;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.S3FileUploadFailedException;
import com.wccg.well_c_code_git_backend.global.s3.S3Uploader;
import com.wccg.well_c_code_git_backend.global.s3.UploadFileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final S3Uploader s3Uploader;

    public String profilePhotoUpload(MultipartFile profilePhotoFile) {

        imageExtensionValidate(profilePhotoFile);

        imageSizeValidate(profilePhotoFile);

        imageDimensionsValidate(profilePhotoFile);

        return s3Uploader.upload(profilePhotoFile, UploadFileType.USER_PROFILE_PHOTO);
    }

    private void imageDimensionsValidate(MultipartFile profilePhotoFile) {
        try {
            BufferedImage image = ImageIO.read(profilePhotoFile.getInputStream());
            if (image.getWidth() != 100 || image.getHeight() != 100) {
                throw new InvalidImageDimensions();
            }
        } catch (IOException e) {
            throw new S3FileUploadFailedException();
        }
    }

    private void imageSizeValidate(MultipartFile profilePhotoFile) {
        if (profilePhotoFile.getSize() > 15 * 1024 * 1024) {
            throw new ImageTooLarge();
        }
    }

    private void imageExtensionValidate(MultipartFile profilePhotoFile) {
        String originalFilename = profilePhotoFile.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches("(?i)^.+\\.(jpg|png|webp)$")) {
            throw new InvalidImageExtension();
        }
    }
}
