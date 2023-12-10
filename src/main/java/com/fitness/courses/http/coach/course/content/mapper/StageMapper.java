package com.fitness.courses.http.coach.course.content.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.BadRequestException;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.AbstractStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.ExercisesStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.ImgStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.TextStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.VideoStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.AbstractExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.RepeatExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.TimeExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set.AbstractExerciseSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set.ExerciseRepeatSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.exercise.set.ExerciseTimeSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.AbstractStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ExercisesStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ImgStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.TextStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.VideoStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.AbstractExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.RepeatExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.TimeExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.AbstractExerciseSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseRepeatSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseTimeSetContent;

@Service
public class StageMapper
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(StageMapper.class);

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        /*BeanMappingBuilder courseAuthorLessonInfoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(CourseAuthorLessonInfo.class, LessonEntity.class);
            }
        };


        MAPPER.addMapping(courseAuthorLessonInfoBuilder);*/
    }

    public CourseAuthorStageWithContentInfoDto toInfoDto(@NotNull StageEntity entity)
    {
        CourseAuthorStageWithContentInfoDto infoDto = new CourseAuthorStageWithContentInfoDto();
        infoDto.setId(entity.getId());
        infoDto.setSerialNumber(entity.getSerialNumber());
        infoDto.setStageContent(entity.getStageContent().stream().map(this::toContentInfoDto).toList());

        return infoDto;
    }

    private AbstractStageContentInfoDto toContentInfoDto(AbstractStageContent content)
    {
        if (content instanceof ImgStageContent imgStageContent)
        {
            ImgStageContentInfoDto dto = new ImgStageContentInfoDto();
            dto.setUuid(imgStageContent.getUuid());
            dto.setUrl("");// TODO generate url

            return dto;
        }

        if (content instanceof TextStageContent textStageContent)
        {
            TextStageContentInfoDto dto = new TextStageContentInfoDto();
            dto.setUuid(textStageContent.getUuid());
            dto.setTextContent(textStageContent.getTextContent());

            return dto;
        }

        if (content instanceof VideoStageContent videoStageContent)
        {
            VideoStageContentInfoDto dto = new VideoStageContentInfoDto();
            dto.setUuid(videoStageContent.getUuid());
            dto.setUrl("");// TODO generate url

            return dto;
        }

        if (content instanceof ExercisesStageContent exercisesStageContent)
        {
            ExercisesStageContentInfoDto dto = new ExercisesStageContentInfoDto();
            dto.setUuid(exercisesStageContent.getUuid());
            dto.setExercises(
                    exercisesStageContent.getExercises().stream().map(this::toExerciseContentInfoDto).toList());

            return dto;
        }

        final String message = "Can't map content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }

    private AbstractExerciseContentInfoDto<?> toExerciseContentInfoDto(AbstractExerciseContent<?> exerciseContent)
    {
        if (exerciseContent instanceof RepeatExerciseContent repeatExerciseContent)
        {
            RepeatExerciseContentInfoDto dto = new RepeatExerciseContentInfoDto();
            dto.setUuid(repeatExerciseContent.getUuid());
            dto.setCardId(repeatExerciseContent.getCardId());
            dto.setSets(repeatExerciseContent.getSets().stream()
                    .map(set -> (ExerciseRepeatSetContentInfoDto)toExerciseSetContentInfoDto(set))
                    .toList());

            return dto;
        }

        if (exerciseContent instanceof TimeExerciseContent timeExerciseContent)
        {
            TimeExerciseContentInfoDto dto = new TimeExerciseContentInfoDto();
            dto.setUuid(timeExerciseContent.getUuid());
            dto.setCardId(timeExerciseContent.getCardId());
            dto.setSets(timeExerciseContent.getSets().stream()
                    .map(set -> (ExerciseTimeSetContentInfoDto)toExerciseSetContentInfoDto(set))
                    .toList());

            return dto;
        }

        final String message = "Can't map exercise content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }

    private AbstractExerciseSetContentInfoDto toExerciseSetContentInfoDto(AbstractExerciseSetContent exerciseSetContent)
    {
        if (exerciseSetContent instanceof ExerciseRepeatSetContent exerciseRepeatSetContent)
        {
            ExerciseRepeatSetContentInfoDto dto = new ExerciseRepeatSetContentInfoDto();
            dto.setUuid(exerciseRepeatSetContent.getUuid());
            dto.setCountOfKilograms(exerciseRepeatSetContent.getCountOfKilograms());
            dto.setRepeatCount(exerciseRepeatSetContent.getRepeatCount());
            dto.setPauseAfter(exerciseRepeatSetContent.getPauseAfter());

            return dto;
        }

        if (exerciseSetContent instanceof ExerciseTimeSetContent exerciseTimeSetContent)
        {
            ExerciseTimeSetContentInfoDto dto = new ExerciseTimeSetContentInfoDto();
            dto.setUuid(exerciseTimeSetContent.getUuid());
            dto.setCountOfKilograms(exerciseTimeSetContent.getCountOfKilograms());
            dto.setExecutionTime(exerciseTimeSetContent.getExecutionTime());
            dto.setPauseAfter(exerciseTimeSetContent.getPauseAfter());

            return dto;
        }

        final String message = "Can't map exercise set content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }
}
