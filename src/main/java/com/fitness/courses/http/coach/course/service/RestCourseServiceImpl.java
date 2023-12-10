package com.fitness.courses.http.coach.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.courses.http.coach.course.model.dto.CourseAuthorGeneralInfoDto;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.ListCourseInfoDto;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.mapper.CourseMapper;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.user.dto.UserGeneralInfoDto;
import com.fitness.courses.http.user.mapper.UserMapper;

@Service
public class RestCourseServiceImpl implements RestCourseService
{
    private final CourseService courseService;
    private final CourseValidator courseValidator;

    @Autowired
    public RestCourseServiceImpl(
            CourseService courseService,
            CourseValidator courseValidator)
    {
        this.courseService = courseService;
        this.courseValidator = courseValidator;
    }

    @Override
    public void createCourse(@NonNull NewCourseDto newCourseDto)
    {
        courseValidator.validateCourseTitle(newCourseDto.getTitle());

        courseService.createCourse(CourseMapper.toEntity(newCourseDto));
    }

    @Override
    public List<ListCourseInfoDto> getAuthorCourses()
    {
        return courseService.getAllCoursesWhereCurrentUserIsAuthor().stream()
                .map(CourseMapper::toListCourseInfoDto)
                .toList();
    }

    @Override
    public CourseAuthorGeneralInfoDto getAuthorCourseGeneralInfo(Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        return CourseMapper.toCourseAuthorInfoDto(courseService.getCourseOrThrow(courseId));
    }

    @Override
    public CourseAuthorGeneralInfoDto editAuthCourseGeneralInfo(Long courseId, EditCourseAuthorGeneralInfo editCourseAuthorGeneralInfo)
    {
        courseValidator.validateCourseTitle(editCourseAuthorGeneralInfo.getTitle());
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        MultipartFile logo = editCourseAuthorGeneralInfo.getLogo();
        CourseEntity editCourseInfo = CourseMapper.toEntity(editCourseAuthorGeneralInfo);

        return CourseMapper.toCourseAuthorInfoDto(courseService.editCourseGeneralInfo(courseId, editCourseInfo, logo));
    }

    @Override
    public List<UserGeneralInfoDto> getAuthorCourseStudents(Long courseId)
    {
        courseValidator.validateCourseExist(courseId);
        courseValidator.validateCurrentUserHasPermission(courseId);

        return courseService.getCourseOrThrow(courseId).getStudents().stream()
                .map(UserMapper::toUserGeneralInfoDto)
                .toList();
    }
}
