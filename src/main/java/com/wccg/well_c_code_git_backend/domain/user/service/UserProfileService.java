package com.wccg.well_c_code_git_backend.domain.user.service;

import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.ServiceNicknameAvailableCheckRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.request.ServiceUpdateProfileRequest;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceNicknameAvailableCheckResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceReadProfileResponse;
import com.wccg.well_c_code_git_backend.domain.user.dto.service.response.ServiceUpdateProfileResponse;
import com.wccg.well_c_code_git_backend.domain.user.mapper.UserDtoMapper;
import com.wccg.well_c_code_git_backend.domain.user.model.User;
import com.wccg.well_c_code_git_backend.domain.user.repository.UserRepository;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.ImageTooLarge;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.InvalidImageDimensions;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.InvalidImageExtension;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.file.S3FileUploadFailedException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.user.IntroduceTooLongException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.user.NicknameConflictException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.user.NicknameLengthInvalidException;
import com.wccg.well_c_code_git_backend.global.exception.exceptions.user.UserNotFoundException;
import com.wccg.well_c_code_git_backend.global.s3.S3Uploader;
import com.wccg.well_c_code_git_backend.global.s3.UploadFileType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

import static com.wccg.well_c_code_git_backend.domain.user.mapper.UserDtoMapper.toServiceNicknameAvailableCheckResponse;
import static com.wccg.well_c_code_git_backend.domain.user.mapper.UserDtoMapper.toServiceUpdateProfileResponse;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;

    public ServiceNicknameAvailableCheckResponse nicknameAvailableCheck(ServiceNicknameAvailableCheckRequest request) {
        String nickname = request.getNickname();
        nicknameValidate(nickname);
        return toServiceNicknameAvailableCheckResponse(true,nickname);
    }

    private void nicknameValidate(String nickname) {
        nicknameLengthValidate(nickname);
        nicknameConflictValidate(nickname);
    }

    private void nicknameConflictValidate(String nickname) {
        Optional<User> optionalUser = userRepository.findByNickname(nickname);
        if(optionalUser.isPresent()){
            throw new NicknameConflictException();
        }
    }

    private void nicknameLengthValidate(String nickname) {
        if(nickname.length() < 2 || nickname.length() > 12){
            throw new NicknameLengthInvalidException();
        }
    }

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

    @Transactional
    public ServiceUpdateProfileResponse updateProfile(User user, ServiceUpdateProfileRequest serviceUpdateProfileRequest) {
        String nickname = serviceUpdateProfileRequest.getNickname();
        nicknameValidate(nickname);

        String introduce = serviceUpdateProfileRequest.getIntroduce();
        introduceLengthValidate(introduce);

        String imageURL = serviceUpdateProfileRequest.getProfileImageUrl();

        user.updateProfile(nickname,introduce,imageURL);

        userRepository.save(user);

        return toServiceUpdateProfileResponse(user);
    }

    private void introduceLengthValidate(String introduce) {
        if(introduce.length() > 200){
            throw new IntroduceTooLongException();
        }
    }

    public ServiceReadProfileResponse readProfile(Long userId) {
        return userRepository.findById(userId)
                .map(UserDtoMapper::toServiceReadProfileResponse)
                .orElseThrow(UserNotFoundException::new);
    }
}
