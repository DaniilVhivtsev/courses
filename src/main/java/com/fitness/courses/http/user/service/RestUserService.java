package com.fitness.courses.http.user.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.student.model.dto.achievement.UserCourseWithAchievementsInfoDto;
import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.model.dto.EditUserGeneralInfoDto;

public interface RestUserService
{
    GeneralInfo editUserInfo(EditUserGeneralInfoDto editUserGeneralInfoDto);

    GeneralInfo getCurrentUserGeneralInfo();

    GeneralInfo getCurrentInfo(@NotNull Long userId);

    List<UserCourseWithAchievementsInfoDto> getCurrentUserAchievements();
}
