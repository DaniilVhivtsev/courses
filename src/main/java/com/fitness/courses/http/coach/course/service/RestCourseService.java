package com.fitness.courses.http.coach.course.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.UpdateCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.UpdateCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.AddCourseAuthorStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.StageContentType;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateAbstractStageContentDto;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorContentInfo;
import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;

public interface RestCourseService
{
    void createCourse(@NonNull NewCourseDto newCourseDto);

    void deleteCourse(@NonNull Long courseId);

    List<ListCourseInfoDto> getAuthorCourses();

    CourseAuthorGeneralInfoDto getAuthorCourseGeneralInfo(Long courseId);

    CourseAuthorGeneralInfoDto editAuthCourseGeneralInfo(Long courseId,
            EditCourseAuthorGeneralInfo editCourseAuthorGeneralInfo);

    List<UserGeneralInfoDto> getAuthorCourseStudents(Long courseId);

    CourseAuthorContentInfo getAuthorCourseContent(@NotNull Long courseId);

    void addModule(@NotNull Long courseId, @NotNull NewCourseAuthorModuleDto newModuleDto);

    void editModule(@NotNull Long courseId, @NotNull Long moduleId,
            @NotNull UpdateCourseAuthorModuleDto updateModuleDto);

    void deleteModule(@NotNull Long courseId, @NotNull Long moduleId);

    void addLesson(@NotNull Long courseId, @NotNull Long moduleId, @NotNull NewCourseAuthorLessonDto newLessonDto);

    void editLesson(@NotNull Long courseId, @NotNull Long moduleId, @NotNull Long lessonId,
            @NotNull UpdateCourseAuthorLessonDto updateLessonDto);

    void deleteLesson(@NotNull Long courseId, @NotNull Long moduleId, @NotNull Long lessonId);

    Long addStage(@NotNull Long courseId, @NotNull Long lessonId, @NotNull String stageTitle);

    List<CourseAuthorStageInfoDto> getStages(@NotNull Long courseId, @NotNull Long lessonId);

    List<CourseAuthorStageWithContentInfoDto> getStagesWithContent(@NotNull Long courseId, @NotNull Long lessonId);

    CourseAuthorStageWithContentInfoDto getStage(@NotNull Long courseId, @NotNull Long lessonId,
            @NotNull Long stageId);

    void editStage(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId,
            @NotNull UpdateCourseAuthorStageDto updateStageDto);

    void deleteStage(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId);

    String addStageContent(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId,
            @NotNull AddCourseAuthorStageContentInfoDto addContentDto);

    void editStageContent(@NotNull Long courseId, @NotNull Long lessonId, @NotNull Long stageId,
            @NotNull StageContentType type, @NotNull MultiValueMap<String, Object> formData,
            @Nullable MultipartFile multipartFile);
}
