package com.fitness.courses.http.student.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fitness.courses.http.student.model.dto.stage.StageContentInfoDto;
import com.fitness.courses.http.user.dto.UserCurrentCourseInfo;

public interface RestStudentCoursesService
{
    void completeSet(@NotNull Long courseId, @NotNull Long stageId, @NotNull String setId);

    void completeStage(@NotNull Long courseId, @NotNull Long stageId);

    void createBidRegistrationForTheCourse(@NotNull Long courseId);

    StageContentInfoDto getStageContent(@NotNull Long courseId, @NotNull Long stageId);

    List<UserCurrentCourseInfo> getStudentCurrentCoursesInfo();
}
