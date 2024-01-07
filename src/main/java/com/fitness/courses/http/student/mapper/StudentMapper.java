package com.fitness.courses.http.student.mapper;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.BadRequestException;
import com.fitness.courses.http.attachment.service.AttachmentService;
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
import com.fitness.courses.http.coach.course.model.entity.CourseEntity;
import com.fitness.courses.http.student.model.dto.stage.StageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.AbstractStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.ExercisesStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.ImgStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.TextStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.VideoStageContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.AbstractExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.DistanceExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.RepeatExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.TimeExerciseContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.AbstractExerciseSetContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.ExerciseDistanceSetContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.ExerciseRepeatSetContentInfoDto;
import com.fitness.courses.http.student.model.dto.stage.content.exercise.set.ExerciseTimeSetContentInfoDto;
import com.fitness.courses.http.user.dto.UserCurrentCourseInfo;

@Service
public class StudentMapper
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(StudentMapper.class);

    private final AttachmentService attachmentService;

    public StudentMapper(AttachmentService attachmentService)
    {
        this.attachmentService = attachmentService;
    }

    private static final DozerBeanMapper MAPPER = new DozerBeanMapper();

    static
    {
        BeanMappingBuilder userCurrentCourseInfoDtoBuilder = new BeanMappingBuilder()
        {
            @Override
            protected void configure()
            {
                mapping(UserCurrentCourseInfo.class, CourseEntity.class)
                        .fields("description", "shortDescription");
            }
        };

        MAPPER.addMapping(userCurrentCourseInfoDtoBuilder);
    }

    public static UserCurrentCourseInfo toUserCurrentCourseInfo(@NotNull CourseEntity entity,
            @NotNull Double coursePercentagePassed)
    {
        UserCurrentCourseInfo dto = MAPPER.map(entity, UserCurrentCourseInfo.class);
        dto.setIconImgUrl(
                entity.getLogo()
                        .getFileEntity()
                        .getUrl()
        );
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorFullName(entity.getAuthor().getFullName());
        dto.setPercentagePassed(coursePercentagePassed);

        return dto;
    }

    public StageContentInfoDto toStageContentInfoDto(@NotNull StageEntity stageEntity,
            @NotNull Set<String> doneStageAndSetUuids)
    {
        StageContentInfoDto dto = new StageContentInfoDto();
        dto.setId(stageEntity.getId());
        dto.setTitle("Example title");
        dto.setCompleted(doneStageAndSetUuids.contains(stageEntity.getId().toString()));
        dto.setStageContent(stageEntity.getStageContent().stream()
                .map(content -> toContentInfoDto(content, doneStageAndSetUuids))
                .toList());

        return dto;
    }

    private AbstractStageContentInfoDto toContentInfoDto(@NotNull AbstractStageContent content,
            @NotNull Set<String> doneStageAndSetUuids)
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
                    exercisesStageContent.getExercises().stream()
                            .map(exercise -> toExerciseContentInfoDto(exercise, doneStageAndSetUuids))
                            .toList()
            );

            return dto;
        }

        final String message = "Can't map content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }

    private AbstractExerciseContentInfoDto<?> toExerciseContentInfoDto(
            @NotNull AbstractExerciseContent<?> exerciseContent, @NotNull Set<String> doneStageAndSetUuids)
    {
        if (exerciseContent instanceof RepeatExerciseContent repeatExerciseContent)
        {
            RepeatExerciseContentInfoDto dto = new RepeatExerciseContentInfoDto();
            dto.setUuid(repeatExerciseContent.getUuid());
            dto.setCardId(repeatExerciseContent.getCardId());
            dto.setSets(repeatExerciseContent.getSets().stream()
                    .map(set -> (ExerciseRepeatSetContentInfoDto)toExerciseSetContentInfoDto(set, doneStageAndSetUuids))
                    .toList());
            dto.setNumberOfSets(dto.getSets().size());
            dto.setNumberOfCompletedSets(
                    dto.getSets().stream()
                            .filter(AbstractExerciseSetContentInfoDto::getIsCompleted)
                            .count()
            );

            return dto;
        }

        if (exerciseContent instanceof TimeExerciseContent timeExerciseContent)
        {
            TimeExerciseContentInfoDto dto = new TimeExerciseContentInfoDto();
            dto.setUuid(timeExerciseContent.getUuid());
            dto.setCardId(timeExerciseContent.getCardId());
            dto.setSets(timeExerciseContent.getSets().stream()
                    .map(set -> (ExerciseTimeSetContentInfoDto)toExerciseSetContentInfoDto(set, doneStageAndSetUuids))
                    .toList());
            dto.setNumberOfSets(dto.getSets().size());
            dto.setNumberOfCompletedSets(
                    dto.getSets().stream()
                            .filter(AbstractExerciseSetContentInfoDto::getIsCompleted)
                            .count()
            );

            return dto;
        }

        if (exerciseContent instanceof DistanceExerciseContent distanceExerciseContent)
        {
            DistanceExerciseContentInfoDto dto = new DistanceExerciseContentInfoDto();
            dto.setUuid(distanceExerciseContent.getUuid());
            dto.setCardId(distanceExerciseContent.getCardId());
            dto.setSets(distanceExerciseContent.getSets().stream()
                    .map(set -> (ExerciseDistanceSetContentInfoDto)toExerciseSetContentInfoDto(set,
                            doneStageAndSetUuids))
                    .toList());
            dto.setNumberOfSets(dto.getSets().size());
            dto.setNumberOfCompletedSets(
                    dto.getSets().stream()
                            .filter(AbstractExerciseSetContentInfoDto::getIsCompleted)
                            .count()
            );

            return dto;
        }

        final String message = "Can't map exercise content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }

    private AbstractExerciseSetContentInfoDto toExerciseSetContentInfoDto(
            @NotNull AbstractExerciseSetContent exerciseSetContent, @NotNull Set<String> doneStageAndSetUuids)
    {
        if (exerciseSetContent instanceof ExerciseRepeatSetContent exerciseRepeatSetContent)
        {
            ExerciseRepeatSetContentInfoDto dto = new ExerciseRepeatSetContentInfoDto();
            dto.setUuid(exerciseRepeatSetContent.getUuid());
            dto.setCountOfKilograms(exerciseRepeatSetContent.getCountOfKilograms());
            dto.setRepeatCount(exerciseRepeatSetContent.getRepeatCount());
            dto.setPauseAfter(exerciseRepeatSetContent.getPauseAfter());
            dto.setCompleted(doneStageAndSetUuids.contains(exerciseRepeatSetContent.getUuid()));

            return dto;
        }

        if (exerciseSetContent instanceof ExerciseTimeSetContent exerciseTimeSetContent)
        {
            ExerciseTimeSetContentInfoDto dto = new ExerciseTimeSetContentInfoDto();
            dto.setUuid(exerciseTimeSetContent.getUuid());
            dto.setCountOfKilograms(exerciseTimeSetContent.getCountOfKilograms());
            dto.setExecutionTime(exerciseTimeSetContent.getExecutionTime());
            dto.setPauseAfter(exerciseTimeSetContent.getPauseAfter());
            dto.setCompleted(doneStageAndSetUuids.contains(exerciseTimeSetContent.getUuid()));

            return dto;
        }

        if (exerciseSetContent instanceof ExerciseDistanceSetContent distanceSetContent)
        {
            ExerciseDistanceSetContentInfoDto dto = new ExerciseDistanceSetContentInfoDto();
            dto.setUuid(distanceSetContent.getUuid());
            dto.setCountOfKilograms(distanceSetContent.getCountOfKilograms());
            dto.setDistanceKilometers(distanceSetContent.getDistanceKilometers());
            dto.setPauseAfter(distanceSetContent.getPauseAfter());
            dto.setCompleted(doneStageAndSetUuids.contains(distanceSetContent.getUuid()));

            return dto;
        }

        final String message = "Can't map exercise set content to dto";
        LOG.error(message);
        throw new BadRequestException(message);
    }
}
