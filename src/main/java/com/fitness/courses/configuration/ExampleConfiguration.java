package com.fitness.courses.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.catalina.connector.Request;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.io.IOUtils;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitness.courses.controller.AuthController;
import com.fitness.courses.controller.CatalogController;
import com.fitness.courses.controller.CoachCourseController;
import com.fitness.courses.controller.StudentCourseController;
import com.fitness.courses.http.auth.dto.RegistrationUserInfoDto;
import com.fitness.courses.http.coach.card.model.dto.CardInfoDto;
import com.fitness.courses.http.coach.card.model.dto.NewCardDto;
import com.fitness.courses.http.coach.course.content.mapper.StageMapper;
import com.fitness.courses.http.coach.course.content.model.dto.lesson.NewCourseAuthorLessonDto;
import com.fitness.courses.http.coach.course.content.model.dto.module.NewCourseAuthorModuleDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.AddCourseAuthorStageContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.CourseAuthorStageWithContentInfoDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.get.StageContentType;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateExercisesStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateImgStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateTextStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.UpdateVideoStageContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateDistanceExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateRepeatExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.UpdateTimeExerciseContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseDistanceSetContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseRepeatSetContentDto;
import com.fitness.courses.http.coach.course.content.model.dto.stage.content.update.exercise.set.UpdateExerciseTimeSetContentDto;
import com.fitness.courses.http.coach.course.content.service.stage.CrudStageEntityService;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.objectStorage.service.LocalStorageFileService;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.service.UserService;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Discriminator;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ExampleConfiguration
{
    private final AuthController authController;
    private final CoachCourseController coachCourseController;
    private final CatalogController catalogController;
    private final StudentCourseController studentCourseController;

    private Long firstCardId;
    private Long secondCardId;

    public ExampleConfiguration(
            AuthController authController,
            CoachCourseController coachCourseController,
            CatalogController catalogController,
            StudentCourseController studentCourseController)
    {
        this.authController = authController;
        this.coachCourseController = coachCourseController;
        this.catalogController = catalogController;
        this.studentCourseController = studentCourseController;
    }

    @Bean
    public OpenApiCustomizer myCustomiser()
    {

        Map<String, String> classTypeMapping = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>("ExercisesStageContentInfoDto",
                        "#/components/schemas/ExercisesStageContentInfoDto"),
                new AbstractMap.SimpleEntry<String, String>("ImgStageContentInfoDto",
                        "#/components/schemas/ImgStageContentInfoDto")
        );

        Discriminator classTypeDiscriminator = new Discriminator().propertyName("classType")
                .mapping(classTypeMapping);

        return openApi -> openApi.getComponents().getSchemas().values()
                .stream()
                .filter(schema -> "ContainerClass".equals(schema.getName()))
                .map(schema -> schema.getProperties().get("elements"))
                .forEach(arraySchema -> ((ArraySchema)arraySchema).getItems().discriminator(classTypeDiscriminator));
    }

    @Bean
    public CommandLineRunner testS3BucketLoader(LocalStorageFileService localStorageFileService,
            CrudStageEntityService crudStageEntityService, StageMapper stageMapper, UserService userService)
    {
        return args ->
        {/*
         *//*final String bucketName = "bucketnamevshivtsev";
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("static/firstImage.png");
            LocalStorageFileEntity fileEntity = localStorageFileService
                    .addFile(bucketName, FileExtensionEnum.PNG, inputStream);
            inputStream.close();
            System.out.println(fileEntity.getUrl());*//*

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

//            StageEntity stageEntityFromDb = crudStageEntityService.save(stageEntity);
//            System.out.println(stageEntityFromDb);
//            CourseAuthorStageWithContentInfoDto stageInfoDto = stageMapper.toInfoDtoWithContent(stageEntityFromDb);
//            System.out.println(stageInfoDto);*/

//            if (true)
//            {
//                return;
//            }

            if (userService.findByEmail("danya.vshivtsev@gmail.com").isPresent())
            {
                return;
            }

            HttpStatusCode httpStatusCode = authController.registration(
                    new RegistrationUserInfoDto()
                            .setEmail("danya.vshivtsev@gmail.com")
                            .setFirstName("Daniil")
                            .setLastName("Vshivtsev")
                            .setPassword("12345")
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            User currentUser = userService.findByEmail("danya.vshivtsev@gmail.com").orElseThrow();
            userService.update(currentUser.setConfirmed(true));

            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    currentUser,
                    null,
                    currentUser.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create course

            Long courseId = (Long)coachCourseController.createCourse(
                            new NewCourseDto()
                                    .setTitle("Новый курс от Вшивцева Даниила"))
                    .getBody();

            this.getClass().getClassLoader().getResourceAsStream("templates/VerificationEmail.html");
            firstCardId = (Long)coachCourseController.createCard(
                    new NewCardDto()
                            .setTitle("Жим штанги лежа")
                            .setDescription(
                                    "Иcходное положение: Лежа на скамье, лопатки соединены, руки со штангой "
                                            + "расположены на уровне грудины, хват – прямой, закрытый, шире плеч. На "
                                            + "вдохе опускаем штангу до груди (но не кладем ее), на выдохе - "
                                            + "возвращаем в и. п. Обратите внимание! Ягодицы плотно прижаты к скамье."
                                            + " Руки в локтевых суставах полностью не выпрямляются (не блокируем "
                                            + "суставы).")
                            .setInventoryDescription("Нужна штанга и скамья.")
                            .setMuscleGroupsDescription("Проработка мышц груди, переднего пучка дельтовидных мышц и "
                                    + "трехглавых мышц плеча (трицепсов)")
                            .setImages(Stream.of("content/benchPress/first.png", "content/benchPress/second.png",
                                    "content/benchPress/third.png").map(this::getFile).toList())
                            .setVideo(getFile("content/benchPress/video.mp4"))
            ).getBody();

            CardInfoDto firstCard = (CardInfoDto)coachCourseController.getCard(firstCardId).getBody();

            secondCardId = (Long)coachCourseController.createCard(
                    new NewCardDto()
                            .setTitle("Жим ногами в тренажере")
                            .setDescription(
                                    "Иcходное положение: Сидя в тренажере, таз и лопатки плотно прижаты к опоре, ноги"
                                            + " на ширине плеч, стопы стоят на платформе и немного развернуты в "
                                            + "стороны, при выполнении упражнения вес тела переносим в пятки. Снимаем"
                                            + " за специальные ручки вес с упора. На вдохе сгибаем ноги к себе до "
                                            + "угла 90 градусов в коленных суставах, на выдохе выжимаем платформу. "
                                            + "Обратите внимание! Не переносите вес тела на носки. При выполнении "
                                            + "упражнения ноги в коленных суставах полностью не выпрямляются (не "
                                            + "блокируются).")
                            .setInventoryDescription("Тренажер для жима ног.")
                            .setMuscleGroupsDescription("Проработки мышц бедер и больших ягодичных мышц")
                            .setImages(Stream.of("content/legPress/first.png", "content/legPress/second.png",
                                    "content/legPress/third.png").map(this::getFile).toList())
                            .setVideo(getFile("content/legPress/video.mp4"))
            ).getBody();

            CardInfoDto secondCard = (CardInfoDto)coachCourseController.getCard(secondCardId).getBody();

            httpStatusCode = coachCourseController.editAuthorCourseGeneralInfo(
                    courseId,
                    new EditCourseAuthorGeneralInfo()
                            .setTitle("Новый курс от Вшивцева Даниила")
                            .setAbout(
                                    "С другой стороны начало повседневной работы по формированию позиции позволяет "
                                            + "оценить значение соответствующий условий активизации. Идейные "
                                            + "соображения высшего порядка, а также дальнейшее развитие различных "
                                            + "форм деятельности позволяет оценить значение систем массового участия. ")
                            .setShortDescription(
                                    "С другой стороны начало повседневной работы по формированию позиции позволяет "
                                            + "оценить значение соответствующий условий активизации.")
                            .setCourseFor(
                                    "По своей сути рыбатекст является альтернативой традиционному lorem ipsum, "
                                            + "который вызывает у некторых людей недоумение при попытках прочитать "
                                            + "рыбу текст.")
                            .setRequirements("По своей сути рыбатекст является альтернативой традиционному.")
                            .setLogo(getFile("content/courseLogo.png"))
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            catalogController.getPopularCourses(0, 10);

            // Добавляем содержание

            addModule(courseId, "первого");
            addModule(courseId, "второго");
//            addModule(courseId, "третьего");
//            var q = studentCourseController.createBidRegistrationForTheCourse(courseId);
//            var qwe = studentCourseController.getCourseStageContent(courseId, 1L);


            System.out.println("End");
        };
    }

    private MultipartFile getFile(String path)
    {
//        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);

        try
        {
            assert inputStream != null;
            return new MockMultipartFile("file",
                    new File(path).getName(), "text/plain", IOUtils.toByteArray(inputStream));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void checkHttpStatusCode(HttpStatusCode httpStatusCode)
    {
        if (!httpStatusCode.is2xxSuccessful())
        {
            throw new RuntimeException("Http status code is not 2xx");
        }
    }

    private void addModule(Long courseId, String prefix)
    {
        Long firstModuleId = (Long)coachCourseController.addModuleToAuthorCourseContent(
                courseId,
                new NewCourseAuthorModuleDto()
                        .setTitle("Название " + prefix + " модуля.")
                        .setDescription("Описание " + prefix + " модуля.")
        ).getBody();
        addLesson(courseId, firstModuleId, prefix);
        addLesson(courseId, firstModuleId, prefix);
        addLesson(courseId, firstModuleId, prefix);
    }

    private void addLesson(Long courseId, Long moduleId, String prefix)
    {
        Long firstLessonId = (Long)coachCourseController.addLessonToAuthorCourseContent(
                courseId,
                moduleId,
                new NewCourseAuthorLessonDto()
                        .setTitle("Название " + prefix + " занятия.")
        ).getBody();
        addStageWithAll(courseId, firstLessonId, prefix);
        addStageWithStaticContent(courseId, firstLessonId, prefix);
        addStageWithExercise(courseId, firstLessonId, prefix);
    }

    private void addStageWithAll(Long courseId, Long lessonId, String prefix)
    {
        Long firstStageId = (Long)coachCourseController
                .addStageToAuthorCourseContent(courseId, lessonId, "Название " + prefix + " этапа.")
                .getBody();
        addContentWithAll(courseId, lessonId, firstStageId, prefix);
    }

    private void addStageWithStaticContent(Long courseId, Long lessonId, String prefix)
    {
        Long firstStageId = (Long)coachCourseController
                .addStageToAuthorCourseContent(courseId, lessonId, "Название " + prefix + " этапа.")
                .getBody();
        addContentWithStaticContent(courseId, lessonId, firstStageId, prefix);
    }

    private void addStageWithExercise(Long courseId, Long lessonId, String prefix)
    {
        Long firstStageId = (Long)coachCourseController
                .addStageToAuthorCourseContent(courseId, lessonId, "Название " + prefix + " этапа.")
                .getBody();
        addContentWithExercise(courseId, lessonId, firstStageId, prefix);
    }

    private void addContentWithExercise(Long courseId, Long lessonId, Long stageId, String prefix)
    {
        // EXERCISES
        String thirdContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.EXERCISES))
                .getBody();
        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();

        UpdateExerciseDistanceSetContentDto firstDistanceSet = new UpdateExerciseDistanceSetContentDto();
        firstDistanceSet.setCountOfKilograms(0F);
        firstDistanceSet.setDistanceKilometers(10.2F);
        firstDistanceSet.setPauseAfter(LocalTime.of(0, 5, 10));

        UpdateDistanceExerciseContentDto distanceExercise = new UpdateDistanceExerciseContentDto();
        distanceExercise.setCardId(firstCardId);
        //        distanceExercise.setSets(List.of(firstDistanceSet));

        UpdateExercisesStageContentDto updateExercisesStageContentDto = new UpdateExercisesStageContentDto();
        updateExercisesStageContentDto.setUuid(thirdContentId);
        updateExercisesStageContentDto.setSerialNumber(0);
        updateExercisesStageContentDto.setType(StageContentType.EXERCISES);
        //        updateExercisesStageContentDto.setExercises(List.of(distanceExercise));

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        updateExercisesStageContentDto.setExercises(List.of(distanceExercise));
        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        distanceExercise.setSets(List.of(firstDistanceSet));
        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        // EXERCISES
        String fourthContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.EXERCISES))
                .getBody();

        distanceExercise = new UpdateDistanceExerciseContentDto();
        distanceExercise.setCardId(firstCardId);

        updateExercisesStageContentDto = new UpdateExercisesStageContentDto();
        updateExercisesStageContentDto.setUuid(fourthContentId);
        updateExercisesStageContentDto.setSerialNumber(1);
        updateExercisesStageContentDto.setType(StageContentType.EXERCISES);
        updateExercisesStageContentDto.setExercises(List.of(distanceExercise));

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        // EXERCISES
        String fifthContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.EXERCISES))
                .getBody();

        updateExercisesStageContentDto = new UpdateExercisesStageContentDto();
        updateExercisesStageContentDto.setUuid(fifthContentId);
        updateExercisesStageContentDto.setSerialNumber(2);
        updateExercisesStageContentDto.setType(StageContentType.EXERCISES);
        updateExercisesStageContentDto.setExercises(List.of());

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        CourseAuthorStageWithContentInfoDto stageContent =
                (CourseAuthorStageWithContentInfoDto)coachCourseController.getStageWithContentToAuthorCourseContent(
                        courseId, lessonId, stageId).getBody();
        System.out.println();
    }

    private void addContentWithStaticContent(Long courseId, Long lessonId, Long stageId, String prefix)
    {
        // IMG
        String firstContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.IMG))
                .getBody();

        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        MultiValueMap<String, MultipartFile> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("image", getFile("content/stage/img.png"));

        UpdateImgStageContentDto updateImgStageContentDto = new UpdateImgStageContentDto();
        updateImgStageContentDto.setUuid(firstContentId);
        updateImgStageContentDto.setSerialNumber(0);
        updateImgStageContentDto.setType(StageContentType.IMG);

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.IMG, convertToMultiValueMap(updateImgStageContentDto),
                getAbstractMultipartHttpServletRequest(request, multiValueMap));

        // TEXT
        String secondContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.TEXT))
                .getBody();

        request = new MockMultipartHttpServletRequest();
        UpdateTextStageContentDto updateTextStageContentDto = new UpdateTextStageContentDto();
        updateTextStageContentDto.setUuid(secondContentId);
        updateTextStageContentDto.setSerialNumber(1);
        updateTextStageContentDto.setType(StageContentType.TEXT);
        updateTextStageContentDto.setTextContent(
                "Значимость этих проблем настолько очевидна, что реализация намеченных плановых заданий представляет "
                        + "собой интересный эксперимент проверки дальнейших направлений развития. С другой стороны "
                        + "новая модель организационной деятельности в значительной степени обуславливает создание "
                        + "новых предложений. Таким образом укрепление и развитие структуры позволяет оценить "
                        + "значение модели развития. С другой стороны новая модель организационной деятельности "
                        + "требуют определения и уточнения направлений прогрессивного развития.");

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.TEXT, convertToMultiValueMap(updateTextStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        CourseAuthorStageWithContentInfoDto stageContent =
                (CourseAuthorStageWithContentInfoDto)coachCourseController.getStageWithContentToAuthorCourseContent(
                        courseId, lessonId, stageId).getBody();
        System.out.println();
    }

    private void addContentWithAll(Long courseId, Long lessonId, Long stageId, String prefix)
    {
        // IMG
        String firstContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.IMG))
                .getBody();

        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        MultiValueMap<String, MultipartFile> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("image", getFile("content/stage/img.png"));

        UpdateImgStageContentDto updateImgStageContentDto = new UpdateImgStageContentDto();
        updateImgStageContentDto.setUuid(firstContentId);
        updateImgStageContentDto.setSerialNumber(0);
        updateImgStageContentDto.setType(StageContentType.IMG);

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.IMG, convertToMultiValueMap(updateImgStageContentDto),
                getAbstractMultipartHttpServletRequest(request, multiValueMap));

        // TEXT
        String secondContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.TEXT))
                .getBody();

        request = new MockMultipartHttpServletRequest();
        UpdateTextStageContentDto updateTextStageContentDto = new UpdateTextStageContentDto();
        updateTextStageContentDto.setUuid(secondContentId);
        updateTextStageContentDto.setSerialNumber(1);
        updateTextStageContentDto.setType(StageContentType.TEXT);
        updateTextStageContentDto.setTextContent(
                "Значимость этих проблем настолько очевидна, что реализация намеченных плановых заданий представляет "
                        + "собой интересный эксперимент проверки дальнейших направлений развития. С другой стороны "
                        + "новая модель организационной деятельности в значительной степени обуславливает создание "
                        + "новых предложений. Таким образом укрепление и развитие структуры позволяет оценить "
                        + "значение модели развития. С другой стороны новая модель организационной деятельности "
                        + "требуют определения и уточнения направлений прогрессивного развития.");

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.TEXT, convertToMultiValueMap(updateTextStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        // EXERCISES
        String thirdContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.EXERCISES))
                .getBody();
        request = new MockMultipartHttpServletRequest();

        UpdateExerciseDistanceSetContentDto firstDistanceSet = new UpdateExerciseDistanceSetContentDto();
        firstDistanceSet.setCountOfKilograms(0F);
        firstDistanceSet.setDistanceKilometers(10.2F);
        firstDistanceSet.setPauseAfter(LocalTime.of(0, 5, 10));

        UpdateExerciseDistanceSetContentDto secondDistanceSet = new UpdateExerciseDistanceSetContentDto();
        secondDistanceSet.setCountOfKilograms(0F);
        secondDistanceSet.setDistanceKilometers(2.5F);
        secondDistanceSet.setPauseAfter(LocalTime.of(0, 2, 15));

        UpdateExerciseDistanceSetContentDto thirdDistanceSet = new UpdateExerciseDistanceSetContentDto();
        thirdDistanceSet.setCountOfKilograms(0F);
        thirdDistanceSet.setDistanceKilometers(3F);
        thirdDistanceSet.setPauseAfter(LocalTime.of(0, 10, 5));

        UpdateDistanceExerciseContentDto distanceExercise = new UpdateDistanceExerciseContentDto();
        distanceExercise.setCardId(firstCardId);
        distanceExercise.setSets(List.of(firstDistanceSet, secondDistanceSet, thirdDistanceSet));

        UpdateExerciseRepeatSetContentDto firstRepeatSet = new UpdateExerciseRepeatSetContentDto();
        firstRepeatSet.setCountOfKilograms(20.5F);
        firstRepeatSet.setRepeatCount(10);
        firstRepeatSet.setPauseAfter(LocalTime.of(0, 1, 59));

        UpdateExerciseRepeatSetContentDto secondRepeatSet = new UpdateExerciseRepeatSetContentDto();
        secondRepeatSet.setCountOfKilograms(25F);
        secondRepeatSet.setRepeatCount(12);
        secondRepeatSet.setPauseAfter(LocalTime.of(0, 0, 59));

        UpdateExerciseRepeatSetContentDto thirdRepeatSet = new UpdateExerciseRepeatSetContentDto();
        thirdRepeatSet.setCountOfKilograms(18.5F);
        thirdRepeatSet.setRepeatCount(8);
        thirdRepeatSet.setPauseAfter(LocalTime.of(0, 0, 5));

        UpdateRepeatExerciseContentDto repeatExercise = new UpdateRepeatExerciseContentDto();
        repeatExercise.setCardId(secondCardId);
        repeatExercise.setSets(List.of(firstRepeatSet, secondRepeatSet, thirdRepeatSet));

        UpdateExerciseTimeSetContentDto firstTimeSet = new UpdateExerciseTimeSetContentDto();
        firstTimeSet.setCountOfKilograms(12F);
        firstTimeSet.setExecutionTime(LocalTime.of(0, 5, 0));
        firstTimeSet.setPauseAfter(LocalTime.of(0, 2, 59));

        UpdateExerciseTimeSetContentDto secondTimeSet = new UpdateExerciseTimeSetContentDto();
        secondTimeSet.setCountOfKilograms(18F);
        secondTimeSet.setExecutionTime(LocalTime.of(0, 7, 30));
        secondTimeSet.setPauseAfter(LocalTime.of(0, 1, 0));

        UpdateExerciseTimeSetContentDto thirdTimeSet = new UpdateExerciseTimeSetContentDto();
        thirdTimeSet.setCountOfKilograms(20F);
        thirdTimeSet.setExecutionTime(LocalTime.of(0, 0, 45));
        thirdTimeSet.setPauseAfter(LocalTime.of(0, 0, 30));

        UpdateTimeExerciseContentDto timeExercise = new UpdateTimeExerciseContentDto();
        timeExercise.setCardId(firstCardId);
        timeExercise.setSets(List.of(firstTimeSet, secondTimeSet, thirdTimeSet));

        UpdateExercisesStageContentDto updateExercisesStageContentDto = new UpdateExercisesStageContentDto();
        updateExercisesStageContentDto.setUuid(thirdContentId);
        updateExercisesStageContentDto.setSerialNumber(2);
        updateExercisesStageContentDto.setType(StageContentType.EXERCISES);
        updateExercisesStageContentDto.setExercises(List.of(distanceExercise, repeatExercise, timeExercise));

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        // VIDEO
        String fourthContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.VIDEO))
                .getBody();

        request = new MockMultipartHttpServletRequest();
        multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("video", getFile("content/stage/videoWithSound.mp4"));

        UpdateVideoStageContentDto videoStageContentDto = new UpdateVideoStageContentDto();
        videoStageContentDto.setUuid(fourthContentId);
        videoStageContentDto.setType(StageContentType.VIDEO);
        videoStageContentDto.setSerialNumber(3);
        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, stageId,
                StageContentType.VIDEO, convertToMultiValueMap(videoStageContentDto),
                getAbstractMultipartHttpServletRequest(request, multiValueMap));

        CourseAuthorStageWithContentInfoDto stageContent =
                (CourseAuthorStageWithContentInfoDto)coachCourseController.getStageWithContentToAuthorCourseContent(
                courseId, lessonId, stageId).getBody();
        System.out.println();
    }

    public MultiValueMap<String, Object> convertToMultiValueMap(Object dto)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        // Преобразование объекта в Map с помощью Jackson
        Map<String, Object> objectAsMap = objectMapper.convertValue(dto, Map.class);

        // Добавление каждого значения в MultiValueMap
        for (Map.Entry<String, Object> entry : objectAsMap.entrySet())
        {
            map.add(entry.getKey(), entry.getValue());
        }

        return map;
    }

    private StandardMultipartHttpServletRequest getAbstractMultipartHttpServletRequest(HttpServletRequest request,
            MultiValueMap<String, MultipartFile> multiValueMap)
    {
        StandardMultipartHttpServletRequest standardMultipartHttpServletRequest =
                new StandardMultipartHttpServletRequest(request)
                {
                    @Override
                    public MultiValueMap<String, MultipartFile> getMultiFileMap()
                    {
                        return multiValueMap;
                    }
                };
        /*AbstractMultipartHttpServletRequest abstractMultipartHttpServletRequest =
                new AbstractMultipartHttpServletRequest(request)
                {
                    @Override
                    public String getMultipartContentType(String paramOrFileName)
                    {
                        return null;
                    }

                    @Override
                    public HttpHeaders getMultipartHeaders(String paramOrFileName)
                    {
                        return null;
                    }

                    @Override
                    public MultiValueMap<String, MultipartFile> getMultiFileMap()
                    {
                        return multiValueMap;
                    }
                };*/

        return standardMultipartHttpServletRequest;
    }
}
