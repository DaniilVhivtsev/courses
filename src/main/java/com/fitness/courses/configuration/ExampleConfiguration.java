package com.fitness.courses.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fitness.courses.http.coach.course.content.mapper.StageMapper;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.entity.stage.StageEntity;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.AbstractStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ExercisesStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.ImgStageContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.AbstractExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.RepeatExerciseContent;
import com.fitness.courses.http.coach.course.content.model.entity.stage.content.exercise.set.ExerciseRepeatSetContent;
import com.fitness.courses.http.coach.course.content.service.stage.CrudStageEntityService;
import com.fitness.courses.http.objectStorage.service.LocalStorageFileService;

@Configuration
public class ExampleConfiguration
{
    @Bean
    public CommandLineRunner testS3BucketLoader(LocalStorageFileService localStorageFileService,
            CrudStageEntityService crudStageEntityService, StageMapper stageMapper)
    {
        return args ->
        {
            /*final String bucketName = "bucketnamevshivtsev";
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("static/firstImage.png");
            LocalStorageFileEntity fileEntity = localStorageFileService
                    .addFile(bucketName, FileExtensionEnum.PNG, inputStream);
            inputStream.close();
            System.out.println(fileEntity.getUrl());*/

            StageEntity stageEntity = new StageEntity();
            stageEntity.setSerialNumber(1);
//            stageEntity.setTitle("titleExample");

            List<AbstractStageContent> stageContentList = new ArrayList<>();
            stageEntity.setStageContent(stageContentList);

            ImgStageContent imgStageContent = new ImgStageContent();
            imgStageContent.setUuid("12afasda");
            imgStageContent.setAttachmentId(123L);
            stageContentList.add(imgStageContent);

            ExercisesStageContent exercisesStageContent = new ExercisesStageContent();
            exercisesStageContent.setUuid("78436538242");
            stageContentList.add(exercisesStageContent);

            List<AbstractExerciseContent<?>> exercises = new ArrayList<>();
            exercisesStageContent.setExercises(exercises);

            RepeatExerciseContent repeatExerciseContent = new RepeatExerciseContent();
            repeatExerciseContent.setUuid("443");
            repeatExerciseContent.setCardId(435645L);

            ExerciseRepeatSetContent exerciseRepeatSetContent = new ExerciseRepeatSetContent();
            exerciseRepeatSetContent.setRepeatCount(10);
            exerciseRepeatSetContent.setCountOfKilograms(10.0f);
            exerciseRepeatSetContent.setUuid("sdfgsnjfd");
            exerciseRepeatSetContent.setPauseAfter(null);

            ExerciseRepeatSetContent secondExerciseRepeatSetContent = new ExerciseRepeatSetContent();
            secondExerciseRepeatSetContent.setRepeatCount(10);
            secondExerciseRepeatSetContent.setCountOfKilograms(10.0f);
            secondExerciseRepeatSetContent.setUuid("342342");
            secondExerciseRepeatSetContent.setPauseAfter(null);

            // добавить time

            repeatExerciseContent.setSets(
                    List.of(
                            exerciseRepeatSetContent,
                            secondExerciseRepeatSetContent
            ));

            exercises.add(repeatExerciseContent);

            StageEntity stageEntityFromDb = crudStageEntityService.save(stageEntity);
            System.out.println(stageEntityFromDb);
            CourseAuthorStageWithContentInfoDto stageInfoDto = stageMapper.toInfoDtoWithContent(stageEntityFromDb);
            System.out.println(stageInfoDto);
        };
    }
}
