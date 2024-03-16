package com.fitness.courses.http.coach.variable.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingCourseVariableDto;
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.coach.course.service.CourseService;
import com.fitness.courses.http.coach.variable.model.entity.CourseVariableEntity;
import com.fitness.courses.http.coach.variable.model.entity.VariableTypeEnum;
import com.fitness.courses.http.student.variable.service.StudentVariableService;

@Service
public class CourseVariableServiceImpl implements CourseVariableService
{
    private final CrudCourseVariableServiceImpl crudCourseVariableService;
    private final CourseService courseService;
    private final StudentVariableService studentVariableService;

    @Autowired
    public CourseVariableServiceImpl(
            CrudCourseVariableServiceImpl crudCourseVariableService,
            CourseService courseService,
            StudentVariableService studentVariableService)
    {
        this.crudCourseVariableService = crudCourseVariableService;
        this.courseService = courseService;
        this.studentVariableService = studentVariableService;
    }

    @Override
    public CourseVariableEntity findByIdOrThrow(Long courseVariableId)
    {
        return crudCourseVariableService.findByIdOrThrow(courseVariableId);
    }

    @Override
    public List<CourseVariableEntity> findAllByCourseId(Long courseId)
    {
        return crudCourseVariableService.findAllByCourseId(courseId);
    }

    @Override
    public Set<Long> findAllStageUuidsWithVariableInCourse(@NotNull Long courseId, @NotNull String variableCode)
    {
        return courseService.findAllStageUuidsWithVariableInCourse(courseId, variableCode);
    }

    @Override
    public void update(@NotNull Long courseId, @NotNull List<GreetingCourseVariableDto> updatedVariableDtoList)
    {
        // Получить текущие переменные в них обновить тип, title. Если обновляем тип, то нужно удалить введенные
        // значения у пользователей в эту переменную.

        // Также нужно удалить, которые отсутсвуют в обновленном списке, и добавить новые, которые в списке из DB
        // отсутсвуют.

        final CourseEntity courseEntity = courseService.getCourseOrThrow(courseId);

        final List<CourseVariableEntity> courseVariablesFromDB = findAllByCourseId(courseId);

        final Map<String, CourseVariableEntity> variableCodeWithVariableEntityMap = courseVariablesFromDB.stream()
                .collect(Collectors.toMap(CourseVariableEntity::getCode, e -> e));

        updatedVariableDtoList.forEach(updatedVariable ->
        {
            final String updatedVariableCode = updatedVariable.getCode();
            if (variableCodeWithVariableEntityMap.containsKey(updatedVariableCode))
            {
                final CourseVariableEntity variableFromDB =
                        variableCodeWithVariableEntityMap.get(updatedVariableCode);
                variableFromDB.setTitle(updatedVariable.getTitle());

                if (!variableFromDB.getType().name().equals(updatedVariable.getType().name()))
                {
                    variableFromDB.setType(VariableTypeEnum.valueOf(updatedVariable.getType().name()));
                    studentVariableService.removeAllByCourseVariableId(variableFromDB.getId());
                }

                crudCourseVariableService.update(variableFromDB);

                variableCodeWithVariableEntityMap.remove(updatedVariableCode);
                return;
            }

            CourseVariableEntity newCourseVariable = new CourseVariableEntity();
            newCourseVariable.setTitle(updatedVariable.getTitle());
            newCourseVariable.setCode(updatedVariable.getCode());
            newCourseVariable.setType(VariableTypeEnum.valueOf(updatedVariable.getType().name()));
            newCourseVariable.setCourse(courseEntity);

            crudCourseVariableService.save(newCourseVariable);
        });

        // Удаляем лишние
        variableCodeWithVariableEntityMap.values().forEach(crudCourseVariableService::delete);
    }
}
