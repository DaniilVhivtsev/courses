package com.fitness.courses.http.coach.course.content.mapper;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.BadRequestException;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.AbstractStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.ExercisesStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.ImgStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.TextStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.VideoStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.AbstractExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.DistanceExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.RepeatExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.TimeExerciseContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.AbstractExerciseSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.ExerciseDistanceSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.ExerciseRepeatSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.exercise.set.ExerciseTimeSetContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.AbstractStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ExercisesStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ImgStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.TextStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.VideoStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.AbstractExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.DistanceExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.RepeatExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.TimeExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.AbstractExerciseSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseDistanceSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseRepeatSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseTimeSetContent;

@Service
public class StageMapper
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(StageMapper.class);

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    private final AttachmentService attachmentService;

    public StageMapper(AttachmentService attachmentService)
    {
        this.attachmentService = attachmentService;
    }

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

    public CourseAuthorStageInfoDto toInfoDto(@NotNull StageEntity entity)
    {
        CourseAuthorStageInfoDto infoDto = new CourseAuthorStageInfoDto();
        infoDto.setId(entity.getId());
        infoDto.setSerialNumber(entity.getSerialNumber());

        return infoDto;
    }

    public CourseAuthorStageWithContentInfoDto toInfoDtoWithContent(@NotNull StageEntity entity)
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
            dto.setSerialNumber(imgStageContent.getSerialNumber());
            dto.setUrl(
                    attachmentService.findById(imgStageContent.getAttachmentId())
                            .getFileEntity()
                            .getUrl()
            );

            return dto;
        }

        if (content instanceof TextStageContent textStageContent)
        {
            TextStageContentInfoDto dto = new TextStageContentInfoDto();
            dto.setUuid(textStageContent.getUuid());
            dto.setSerialNumber(textStageContent.getSerialNumber());
            dto.setTextContent(textStageContent.getTextContent());

            return dto;
        }

        if (content instanceof VideoStageContent videoStageContent)
        {
            VideoStageContentInfoDto dto = new VideoStageContentInfoDto();
            dto.setUuid(videoStageContent.getUuid());
            dto.setSerialNumber(videoStageContent.getSerialNumber());
            dto.setUrl(
                    attachmentService.findById(videoStageContent.getAttachmentId())
                    .getFileEntity()
                    .getUrl()
            );

            return dto;
        }

        if (content instanceof ExercisesStageContent exercisesStageContent)
        {
            ExercisesStageContentInfoDto dto = new ExercisesStageContentInfoDto();
            dto.setUuid(exercisesStageContent.getUuid());
            dto.setSerialNumber(exercisesStageContent.getSerialNumber());
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

        if (exerciseContent instanceof DistanceExerciseContent distanceExerciseContent)
        {
            DistanceExerciseContentInfoDto dto = new DistanceExerciseContentInfoDto();
            dto.setUuid(distanceExerciseContent.getUuid());
            dto.setCardId(distanceExerciseContent.getCardId());
            dto.setSets(distanceExerciseContent.getSets().stream()
                    .map(set -> (ExerciseDistanceSetContentInfoDto)toExerciseSetContentInfoDto(set))
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

        if (exerciseSetContent instanceof ExerciseRepeatSetContent exerciseRepeatSetContent)
        {
            ExerciseRepeatSetContentInfoDto dto = new ExerciseRepeatSetContentInfoDto();
            dto.setUuid(exerciseRepeatSetContent.getUuid());
            dto.setCountOfKilograms(exerciseRepeatSetContent.getCountOfKilograms());
            dto.setRepeatCount(exerciseRepeatSetContent.getRepeatCount());
            dto.setPauseAfter(exerciseRepeatSetContent.getPauseAfter());

            return dto;
        }

        if (exerciseSetContent instanceof ExerciseDistanceSetContent distanceSetContent)
        {
            ExerciseDistanceSetContentInfoDto dto = new ExerciseDistanceSetContentInfoDto();
            dto.setUuid(distanceSetContent.getUuid());
            dto.setCountOfKilograms(distanceSetContent.getCountOfKilograms());
            dto.setDistanceKilometers(distanceSetContent.getDistanceKilometers());
            dto.setPauseAfter(distanceSetContent.getPauseAfter());

            return dto;
        }

        final String message = "Can't map exercise set content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }
}
