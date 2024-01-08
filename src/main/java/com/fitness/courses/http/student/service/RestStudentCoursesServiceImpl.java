package com.fitness.courses.http.student.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.catalog.model.dto.CatalogBySearchValueCourseInfoDto;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ExercisesStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.AbstractExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.AbstractExerciseSetContent;
import com.fitness.courses.http.coach.course.content.service.stage.StageService;
import com.fitness.courses.http.coach.course.content.service.stage.StageValidator;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseService;
import com.fitness.courses.http.coach.course.service.CourseValidator;
import com.fitness.courses.http.student.mapper.StudentMapper;
import com.fitness.courses.http.student.model.dto.stage.StageContentInfoDto;
import com.fitness.courses.http.student.model.entity.AdmissionToCourseBidEntity;
import com.fitness.courses.http.student.model.entity.StudentEntity;
import com.fitness.courses.http.student.service.admissionToCourseBid.AdmissionToCourseBidService;
import com.fitness.courses.http.student.service.student.StudentService;
import com.fitness.courses.http.student.service.student.StudentValidator;
import com.fitness.courses.http.user.dto.UserCurrentCourseInfo;
import com.fitness.courses.http.user.model.User;

@Service
public class RestStudentCoursesServiceImpl implements RestStudentCoursesService
{
    private final AuthService authService;
    private final CourseValidator courseValidator;
    private final StudentValidator studentValidator;
    private final AdmissionToCourseBidService admissionToCourseBidService;
    private final StudentService studentService;
    private final CourseService courseService;
    private final StageValidator stageValidator;
    private final StageService stageService;
    private final StudentMapper studentMapper;

    @Autowired
    public RestStudentCoursesServiceImpl(
            AuthService authService,
            CourseValidator courseValidator,
            StudentValidator studentValidator,
            AdmissionToCourseBidService admissionToCourseBidService,
            StudentService studentService, CourseService courseService,
            StageValidator stageValidator,
            StageService stageService,
            StudentMapper studentMapper)
    {
        this.authService = authService;
        this.courseValidator = courseValidator;
        this.studentValidator = studentValidator;
        this.admissionToCourseBidService = admissionToCourseBidService;
        this.studentService = studentService;
        this.courseService = courseService;
        this.stageValidator = stageValidator;
        this.stageService = stageService;
        this.studentMapper = studentMapper;
    }

    @Override
    public void completeSet(@NotNull Long courseId, @NotNull Long stageId, @NotNull String setId)
    {
        courseValidator.validateCourseExist(courseId);
        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToCourse(courseId, stageId);

        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        studentValidator.validateStudentWithUserAndCourseExist(currentUser, course);

        final StageEntity stage = stageService.getOrThrow(stageId);
        final StudentEntity student = studentService.getByUserAndCourseOrThrow(currentUser, course);
        final Set<String> doneStageAndSetUuids = new HashSet<>(student.getDoneStageAndSetUuids());
        doneStageAndSetUuids.add(setId);

        final Set<String> stageSetsUuids = stage.getStageContent().stream()
                .filter(ExercisesStageContent.class::isInstance)
                .map(ExercisesStageContent.class::cast)
                .flatMap(content -> content.getExercises().stream()
                        .map(AbstractExerciseContent::getSets))
                .flatMap(Collection::stream)
                .map(AbstractExerciseSetContent::getUuid).collect(Collectors.toSet());
        stageSetsUuids.removeAll(doneStageAndSetUuids);

        if (stageSetsUuids.size() == 0)
        {
            doneStageAndSetUuids.add(stage.getId().toString());
        }

        student.setDoneStageAndSetUuids(doneStageAndSetUuids);

        studentService.update(student);
    }

    @Override
    public void completeStage(Long courseId, Long stageId)
    {
        courseValidator.validateCourseExist(courseId);
        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToCourse(courseId, stageId);

        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        studentValidator.validateStudentWithUserAndCourseExist(currentUser, course);

        // TODO validate that stage doesn't have exercises with sets

        final StudentEntity student = studentService.getByUserAndCourseOrThrow(currentUser, course);
        final Set<String> doneStageAndSetUuids = new HashSet<>(student.getDoneStageAndSetUuids());
        doneStageAndSetUuids.add(stageId.toString());

        student.setDoneStageAndSetUuids(doneStageAndSetUuids);
        studentService.update(student);
    }

    @Override
    public void createBidRegistrationForTheCourse(@NotNull Long courseId)
    {
        courseValidator.validateCourseExist(courseId);

        // Проверить, что студент с текущим пользователем и переданным курсом отсутствует.
        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        studentValidator.validateStudentWithUserAndCourseNotExist(currentUser, course);

        // Создать заявку на регистрацию на курс.
        final AdmissionToCourseBidEntity admissionToCourseBidEntity =
                admissionToCourseBidService.create(currentUser, course);

        // Создать студента.
        studentService.create(admissionToCourseBidEntity);
    }

    @Override
    public StageContentInfoDto getStageContent(@NotNull Long courseId, @NotNull Long stageId)
    {
        courseValidator.validateCourseExist(courseId);
        stageValidator.validateExist(stageId);
        stageValidator.validateStageBelongsToCourse(courseId, stageId);

        final User currentUser = authService.getCurrentUserOrThrow();
        final CourseEntity course = courseService.getCourseOrThrow(courseId);
        studentValidator.validateStudentWithUserAndCourseExist(currentUser, course);

        return studentMapper.toStageContentInfoDto(
                stageService.getOrThrow(stageId),
                new HashSet<>(studentService.getByUserAndCourseOrThrow(currentUser, course).getDoneStageAndSetUuids())
        );
    }

    @Override
    public List<UserCurrentCourseInfo> getStudentCurrentCoursesInfo()
    {
        final User currentUser = authService.getCurrentUserOrThrow();

        return studentService.getUserCoursesOrderById(currentUser).stream()
                .map(course -> StudentMapper.toUserCurrentCourseInfo(course,
                        studentService.getCoursePercentagePassed(currentUser, course)))
                .toList();
    }
}
