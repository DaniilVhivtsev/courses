package com.fitness.courses.http.coach.course.content.service.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;
import com.fitness.courses.global.utils.UUIDGenerator;
import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.coach.course.content.model.dto.stage.AddCourseAuthorStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.UpdateCourseAuthorStageDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateAbstractStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateExercisesStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateImgStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateTextStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateVideoStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateAbstractExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateDistanceExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateRepeatExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateTimeExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseDistanceSetContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseRepeatSetContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseTimeSetContentDto;
import com.fitness.courses.http.coach.course.content.model.entity.LessonEntity;
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
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseDistanceSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseRepeatSetContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseTimeSetContent;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;

@Service
public class StageServiceImpl implements StageService
{
    private final CrudStageEntityService crudStageEntityService;
    private final AttachmentService attachmentService;

    @Autowired
    public StageServiceImpl(
            CrudStageEntityService crudStageEntityService,
            AttachmentService attachmentService)
    {
        this.crudStageEntityService = crudStageEntityService;
        this.attachmentService = attachmentService;
    }

    @Override
    public void add(@NotNull LessonEntity lesson)
    {
        StageEntity newStageEntity = new StageEntity();
        newStageEntity.setStageContent(new ArrayList<>());
        newStageEntity.setSerialNumber(getNextSerialNumber(lesson));
        newStageEntity.setLesson(lesson);

        crudStageEntityService.save(newStageEntity);
    }

    @Override
    public void addContent(@NotNull Long stageId, @NotNull AddCourseAuthorStageContentInfoDto addContentDto)
    {
        final StageEntity stageEntity = getOrThrow(stageId);
        AbstractStageContent newStageContent = createStageContent(addContentDto);
        newStageContent.setUuid(UUIDGenerator.nestUuidInString());
        newStageContent.setSerialNumber(getNextSerialNumber(stageEntity));
    }

    private AbstractStageContent createStageContent(AddCourseAuthorStageContentInfoDto addContentDto)
    {
        return switch (addContentDto.getType())
                {
                    case VIDEO -> new VideoStageContent();
                    case IMG -> new ImgStageContent();
                    case TEXT -> new TextStageContent()
                            .setTextContent("");
                    case EXERCISES -> new ExercisesStageContent()
                            .setExercises(new ArrayList<>());
                };
    }

    private int getNextSerialNumber(@NotNull StageEntity stageEntity)
    {
        List<AbstractStageContent> stageContentList = stageEntity.getStageContent();
        Optional<AbstractStageContent> lastBySerialStageContentOptional =
                stageContentList == null || stageContentList.size() == 0
                        ? Optional.empty()
                        : Optional.of(stageContentList.get(stageContentList.size() - 1));
        if (lastBySerialStageContentOptional.isEmpty())
        {
            return 0;
        }

        return lastBySerialStageContentOptional.get().getSerialNumber() + 1;
    }

    @Override
    public void deleteAllByLesson(LessonEntity lesson)
    {
        crudStageEntityService.deleteByLessonEntity(lesson);
    }

    @Override
    public List<StageEntity> findAllByLessonAndSortAscBySerialNumber(@NotNull LessonEntity lesson)
    {
        return crudStageEntityService.findAllByLessonIdAndSortAscBySerialNumber(lesson.getId());
    }

    private int getNextSerialNumber(@NotNull LessonEntity lesson)
    {
        List<StageEntity> lessonStages = findAllByLessonAndSortAscBySerialNumber(lesson);
        if (lessonStages.size() == 0)
        {
            return 0;
        }

        return lessonStages.get(lessonStages.size() - 1).getSerialNumber() + 1;
    }

    @Override
    public Optional<StageEntity> getOptional(@NotNull Long id)
    {
        return crudStageEntityService.findById(id);
    }

    @Override
    public StageEntity getOrThrow(@NotNull Long id)
    {
        return crudStageEntityService.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find stage with id %s".formatted(id)));
    }

    @Override
    public void update(@NotNull LessonEntity lessonEntityFromDb, @NotNull Long stageId,
            @NotNull UpdateCourseAuthorStageDto updateStageDto)
    {
        StageEntity stageEntityFromDb = getOrThrow(stageId);
        if (stageEntityFromDb.getSerialNumber() != updateStageDto.getSerialNumber())
        {
            List<StageEntity> lessonStages = findAllByLessonAndSortAscBySerialNumber(lessonEntityFromDb);
            StageEntity stageEntityFromList = lessonStages.stream()
                    .filter(stage -> stage.getId().equals(stageId))
                    .findFirst()
                    .orElseThrow();

            int newSerialNumber = updateStageDto.getSerialNumber();

            for (StageEntity stage : lessonStages)
            {
                if (stage != stageEntityFromList)
                {
                    int currentSerialNumber = stage.getSerialNumber();
                    if (currentSerialNumber >= newSerialNumber
                            && currentSerialNumber < lessonEntityFromDb.getSerialNumber())
                    {
                        stage.setSerialNumber(currentSerialNumber + 1);
                    }
                    else if (currentSerialNumber > lessonEntityFromDb.getSerialNumber()
                            && currentSerialNumber <= newSerialNumber)
                    {
                        stage.setSerialNumber(currentSerialNumber - 1);
                    }
                }
            }

            stageEntityFromList.setSerialNumber(updateStageDto.getSerialNumber());

            lessonStages.forEach(crudStageEntityService::update);
        }
    }

    @Override
    public void delete(@NotNull LessonEntity lessonEntityFromDb, @NotNull Long stageId)
    {
        List<StageEntity> lessonStages = findAllByLessonAndSortAscBySerialNumber(lessonEntityFromDb);
        StageEntity stageEntityFromList = lessonStages.stream()
                .filter(stage -> stage.getId().equals(stageId))
                .findFirst()
                .orElseThrow();

        lessonStages.remove(stageEntityFromList);

        int removedSerialNumber = stageEntityFromList.getSerialNumber();
        for (StageEntity stage : lessonStages)
        {
            if (stage.getSerialNumber() > removedSerialNumber)
            {
                stage.setSerialNumber(stage.getSerialNumber() - 1);
                crudStageEntityService.update(stage);
            }
        }

        crudStageEntityService.deleteById(stageId);
    }

    @Override
    public void updateStageContent(Long stageId, UpdateAbstractStageContentDto updateAbstractStageContentDto)
    {
        final StageEntity stageEntity = getOrThrow(stageId);
        final String stageContentUuid = updateAbstractStageContentDto.getUuid();
        final AbstractStageContent stageContentFromDB = stageEntity.getStageContent().stream()
                .filter(stageContent -> stageContent.getUuid().equals(stageContentUuid))
                .findFirst()
                .orElseThrow();

        updatedStageContent(stageContentFromDB, updateAbstractStageContentDto);

        crudStageEntityService.update(stageEntity);
    }

    private void updatedStageContent(@NotNull AbstractStageContent stageContentFromDB,
            @NotNull UpdateAbstractStageContentDto updateAbstractStageContentDto)
    {
        if (updateAbstractStageContentDto instanceof UpdateTextStageContentDto updatedTextStageContentDto)
        {
            if (!(stageContentFromDB instanceof TextStageContent))
            {
                throw new RuntimeException();
            }

            updateTextStageContent((TextStageContent)stageContentFromDB, updatedTextStageContentDto);
        }
        else if (updateAbstractStageContentDto instanceof UpdateImgStageContentDto updatedImgStageContentDto)
        {
            if (!(stageContentFromDB instanceof ImgStageContent))
            {
                throw new RuntimeException();
            }

            updateImgStageContent((ImgStageContent)stageContentFromDB, updatedImgStageContentDto);
        }
        else if (updateAbstractStageContentDto instanceof UpdateVideoStageContentDto updatedVideoStageContentDto)
        {
            if (!(stageContentFromDB instanceof VideoStageContent))
            {
                throw new RuntimeException();
            }

            updateVideoStageContent((VideoStageContent)stageContentFromDB, updatedVideoStageContentDto);
        }
        else if (updateAbstractStageContentDto instanceof UpdateExercisesStageContentDto updatedExerciseStageContentDto)
        {
            if (!(stageContentFromDB instanceof ExercisesStageContent))
            {
                throw new RuntimeException();
            }

            updateExercisesStageContent((ExercisesStageContent)stageContentFromDB, updatedExerciseStageContentDto);
        }
    }

    private void updateExercisesStageContent(ExercisesStageContent stageContentFromDB,
            UpdateExercisesStageContentDto updatedExerciseStageContentDto)
    {
        stageContentFromDB.setExercises(
                new ArrayList<>(
                        updatedExerciseStageContentDto.getExercises().stream()
                                .map(this::getAbstractExerciseContent)
                                .toList()
                )
        );
    }

    private AbstractExerciseContent<?> getAbstractExerciseContent(UpdateAbstractExerciseContentDto<?> updateAbstractExerciseContentDto)
    {
        if (updateAbstractExerciseContentDto instanceof UpdateDistanceExerciseContentDto updateDistanceExerciseContentDto)
        {
            DistanceExerciseContent distanceExerciseContent = new DistanceExerciseContent();
            distanceExerciseContent.setUuid(updateDistanceExerciseContentDto.getUuid());
            distanceExerciseContent.setCardId(updateDistanceExerciseContentDto.getCardId());
            distanceExerciseContent.setSets(updateDistanceExerciseContentDto.getSets().stream().map(this::getExerciseDistanceSetContent).toList());

            return distanceExerciseContent;
        }
        else if (updateAbstractExerciseContentDto instanceof UpdateRepeatExerciseContentDto updateRepeatExerciseContentDto)
        {
            RepeatExerciseContent repeatExerciseContent = new RepeatExerciseContent();
            repeatExerciseContent.setUuid(updateRepeatExerciseContentDto.getUuid());
            repeatExerciseContent.setCardId(updateRepeatExerciseContentDto.getCardId());
            repeatExerciseContent.setSets(updateRepeatExerciseContentDto.getSets().stream().map(this::getExerciseRepeatSetContent).toList());

            return repeatExerciseContent;
        }
        else if (updateAbstractExerciseContentDto instanceof UpdateTimeExerciseContentDto updateTimeExerciseContentDto)
        {
            TimeExerciseContent timeExerciseContent = new TimeExerciseContent();
            timeExerciseContent.setUuid(updateTimeExerciseContentDto.getUuid());
            timeExerciseContent.setCardId(updateTimeExerciseContentDto.getCardId());
            timeExerciseContent.setSets(updateTimeExerciseContentDto.getSets().stream().map(this::getExerciseTimeSetContent).toList());

            return timeExerciseContent;
        }

        throw new RuntimeException();
    }

    private ExerciseDistanceSetContent getExerciseDistanceSetContent(UpdateExerciseDistanceSetContentDto updateExerciseDistanceSetContentDto)
    {
        ExerciseDistanceSetContent exerciseDistanceSetContent = new ExerciseDistanceSetContent();
        exerciseDistanceSetContent.setUuid(updateExerciseDistanceSetContentDto.getUuid());
        exerciseDistanceSetContent.setDistanceKilometers(updateExerciseDistanceSetContentDto.getDistanceKilometers());
        exerciseDistanceSetContent.setCountOfKilograms(updateExerciseDistanceSetContentDto.getCountOfKilograms());
        exerciseDistanceSetContent.setPauseAfter(updateExerciseDistanceSetContentDto.getPauseAfter());

        return exerciseDistanceSetContent;
    }

    private ExerciseRepeatSetContent getExerciseRepeatSetContent(UpdateExerciseRepeatSetContentDto updateExerciseRepeatSetContentDto)
    {
        ExerciseRepeatSetContent exerciseRepeatSetContent = new ExerciseRepeatSetContent();
        exerciseRepeatSetContent.setUuid(updateExerciseRepeatSetContentDto.getUuid());
        exerciseRepeatSetContent.setRepeatCount(updateExerciseRepeatSetContentDto.getRepeatCount());
        exerciseRepeatSetContent.setCountOfKilograms(updateExerciseRepeatSetContentDto.getCountOfKilograms());
        exerciseRepeatSetContent.setPauseAfter(updateExerciseRepeatSetContentDto.getPauseAfter());

        return exerciseRepeatSetContent;
    }

    private ExerciseTimeSetContent getExerciseTimeSetContent(UpdateExerciseTimeSetContentDto updateExerciseTimeSetContentDto)
    {
        ExerciseTimeSetContent exerciseTimeSetContent = new ExerciseTimeSetContent();
        exerciseTimeSetContent.setUuid(updateExerciseTimeSetContentDto.getUuid());
        exerciseTimeSetContent.setExecutionTime(updateExerciseTimeSetContentDto.getExecutionTime());
        exerciseTimeSetContent.setCountOfKilograms(updateExerciseTimeSetContentDto.getCountOfKilograms());
        exerciseTimeSetContent.setPauseAfter(updateExerciseTimeSetContentDto.getPauseAfter());

        return exerciseTimeSetContent;
    }

    @Transactional
    void updateVideoStageContent(VideoStageContent stageContentFromDB,
            UpdateVideoStageContentDto updatedVideoStageContentDto)
    {
        if (stageContentFromDB.getAttachmentId() != null)
        {
            attachmentService.delete(stageContentFromDB.getAttachmentId());
        }

        stageContentFromDB.setAttachmentId(
                attachmentService.add(
                        new MultipartFileWithExtension(
                                FileExtensionEnum.getEnum(
                                        FilenameUtils.getExtension(
                                                updatedVideoStageContentDto.getVideo().getOriginalFilename()
                                        )
                                ),
                                updatedVideoStageContentDto.getVideo()
                        )
                ).getId()
        );
    }

    @Transactional
    void updateImgStageContent(ImgStageContent stageContentFromDB,
            UpdateImgStageContentDto updatedImgStageContentDto)
    {
        if (stageContentFromDB.getAttachmentId() != null)
        {
            attachmentService.delete(stageContentFromDB.getAttachmentId());
        }

        stageContentFromDB.setAttachmentId(
                attachmentService.add(
                        new MultipartFileWithExtension(
                                FileExtensionEnum.getEnum(
                                        FilenameUtils.getExtension(
                                                updatedImgStageContentDto.getImage().getOriginalFilename()
                                        )
                                ),
                                updatedImgStageContentDto.getImage()
                        )
                ).getId()
        );
    }

    private void updateTextStageContent(TextStageContent stageContentFromDB,
            UpdateTextStageContentDto updatedTextStageContentDto)
    {
        stageContentFromDB.setTextContent(updatedTextStageContentDto.getTextContent());
        // TODO научиться изменять serial number
    }
}
