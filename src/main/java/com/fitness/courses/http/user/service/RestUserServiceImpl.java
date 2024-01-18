package com.fitness.courses.http.user.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.student.model.dto.achievement.UserCourseAchievementInfoDto;
import com.fitness.courses.http.student.model.dto.achievement.UserCourseWithAchievementsInfoDto;
import com.fitness.courses.http.student.service.student.StudentService;
import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.mapper.UserMapper;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.model.dto.EditUserGeneralInfoDto;

@Service
public class RestUserServiceImpl implements RestUserService
{
    private final UserService userService;
    private final AuthService authService;
    private final StudentService studentService;
    private final AttachmentService attachmentService;

    // TODO Убрать
    private static final String ACHIEVEMENT_IMG_URL =
            "https://cdn4.iconfinder.com/data/icons/modern-education-1/128/24-1024.png";

    @Autowired
    public RestUserServiceImpl(
            UserService userService,
            AuthService authService,
            StudentService studentService,
            AttachmentService attachmentService)
    {
        this.userService = userService;
        this.authService = authService;
        this.studentService = studentService;
        this.attachmentService = attachmentService;
    }

    @Override
    public GeneralInfo editUserInfo(EditUserGeneralInfoDto editUserGeneralInfoDto)
    {
        final User currentUser = authService.getCurrentUserOrThrow();

        currentUser.setFirstName(editUserGeneralInfoDto.getName());
        currentUser.setLastName(editUserGeneralInfoDto.getSurname());
        currentUser.setBiography(editUserGeneralInfoDto.getBiography());
        currentUser.setAbout(editUserGeneralInfoDto.getAbout());

        if (editUserGeneralInfoDto.getIcon() != null)
        {
            currentUser.setLogo(
                    attachmentService.add(
                            new MultipartFileWithExtension(
                                    FileExtensionEnum.getEnum(
                                            FilenameUtils.getExtension(
                                                    editUserGeneralInfoDto.getIcon().getOriginalFilename())),
                                    editUserGeneralInfoDto.getIcon()
                            )
                    )
            );
        }

        return UserMapper.toGeneralInfo(userService.update(currentUser));
    }

    @Override
    public GeneralInfo getCurrentUserGeneralInfo()
    {
        return UserMapper.toGeneralInfo(authService.getCurrentUserOrThrow());
    }

    @Override
    public GeneralInfo getCurrentInfo(@NotNull Long userId)
    {
        return UserMapper.toGeneralInfo(userService.findById(userId).orElseThrow());
    }

    @Override
    public List<UserCourseWithAchievementsInfoDto> getCurrentUserAchievements()
    {
        List<UserCourseAchievementInfoDto> achievements = List.of(
                new UserCourseAchievementInfoDto()
                        .setId(1L)
                        .setTitle("3 дня подряд выполняешь упражнения.")
                        .setDescription("Описание достижения.")
                        .setImgUrl(ACHIEVEMENT_IMG_URL),
                new UserCourseAchievementInfoDto()
                        .setId(2L)
                        .setTitle("Выполнил 5 упражнений.")
                        .setDescription("Описание достижения.")
                        .setImgUrl(ACHIEVEMENT_IMG_URL),
                new UserCourseAchievementInfoDto()
                        .setId(3L)
                        .setTitle("Прошел половину курса.")
                        .setDescription("Описание достижения.")
                        .setImgUrl(ACHIEVEMENT_IMG_URL)
        );

        return studentService.getUserCoursesOrderById(authService.getCurrentUserOrThrow()).stream()
                .map(course ->
                        {
                            UserCourseWithAchievementsInfoDto dto = new UserCourseWithAchievementsInfoDto()
                                    .setId(course.getId())
                                    .setTitle(course.getTitle());
                            if (course.getLogo() != null)
                            {
                                dto.setImgUrl(course.getLogo().getFileEntity().getUrl());
                            }

                            dto.setUserAchievements(achievements);

                            return dto;
                        }
                )
                .toList();
    }
}
