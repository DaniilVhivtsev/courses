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
import com.fitness.courses.controller.CurrentUserController;
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
import com.fitness.courses.http.coach.course.content.service.lesson.LessonService;
import com.fitness.courses.http.coach.course.content.service.stage.CrudStageEntityService;
import com.fitness.courses.http.coach.course.model.dto.CourseVariableTypeEnum;
import com.fitness.courses.http.coach.course.model.dto.EditCourseAuthorGeneralInfo;
import com.fitness.courses.http.coach.course.model.dto.NewCourseDto;
import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingCourseVariableDto;
import com.fitness.courses.http.coach.course.model.dto.greeting.GreetingUpdateDto;
import com.fitness.courses.http.greeting.model.dto.StudentGreetingContent;
import com.fitness.courses.http.objectStorage.service.LocalStorageFileService;
import com.fitness.courses.http.student.variable.model.dto.StudentVariableUpdatedValue;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.model.dto.EditUserGeneralInfoDto;
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
    private final CurrentUserController currentUserController;
    private final LessonService lessonService;

    private Long firstCardId;
    private Long secondCardId;
    private Long thirdCardId;
    private Long fourthCardId;

    public ExampleConfiguration(
            AuthController authController,
            CoachCourseController coachCourseController,
            CatalogController catalogController,
            StudentCourseController studentCourseController,
            CurrentUserController currentUserController,
            LessonService lessonService)
    {
        this.authController = authController;
        this.coachCourseController = coachCourseController;
        this.catalogController = catalogController;
        this.studentCourseController = studentCourseController;
        this.currentUserController = currentUserController;
        this.lessonService = lessonService;
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

            httpStatusCode = currentUserController.editAuthorCourseGeneralInfo(
                            new EditUserGeneralInfoDto()
                                    .setName("Вячеслав")
                                    .setSurname("Кравцов")
                                    .setBiography("Более 10 лет опыта")
                                    .setAbout("Более 10 лет опыта")
                                    .setIcon(getFile("content/forDanil/img_5.png")))
                    .getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            // Create courses
            Long firstCourseId = (Long)coachCourseController.createCourse(
                            new NewCourseDto()
                                    .setTitle("Растяжка 30 дней"))
                    .getBody();
            httpStatusCode = coachCourseController.editAuthorCourseGeneralInfo(
                    firstCourseId,
                    new EditCourseAuthorGeneralInfo()
                            .setTitle("Растяжка 30 дней")
                            .setAbout(
                                    "Поддержания мышц в тонусе")
                            .setShortDescription(
                                    "Поддержания мышц в тонусе")
                            .setCourseFor(
                                    "")
                            .setRequirements("")
                            .setLogo(getFile("content/forDanil/img.png"))
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            Long secondCourseId = (Long)coachCourseController.createCourse(
                            new NewCourseDto()
                                    .setTitle("Медитация"))
                    .getBody();
            httpStatusCode = coachCourseController.editAuthorCourseGeneralInfo(
                    secondCourseId,
                    new EditCourseAuthorGeneralInfo()
                            .setTitle("Медитация")
                            .setAbout(
                                    "Практики от стресса")
                            .setShortDescription(
                                    "Практики от стресса")
                            .setCourseFor(
                                    "")
                            .setRequirements("")
                            .setLogo(getFile("content/forDanil/img_3.png"))
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            Long thirdCourseId = (Long)coachCourseController.createCourse(
                            new NewCourseDto()
                                    .setTitle("Кардио"))
                    .getBody();
            httpStatusCode = coachCourseController.editAuthorCourseGeneralInfo(
                    thirdCourseId,
                    new EditCourseAuthorGeneralInfo()
                            .setTitle("Кардио")
                            .setAbout(
                                    "20 тренировок")
                            .setShortDescription(
                                    "20 тренировок")
                            .setCourseFor(
                                    "")
                            .setRequirements("")
                            .setLogo(getFile("content/forDanil/img_1.png"))
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            Long fourthCourseId = (Long)coachCourseController.createCourse(
                            new NewCourseDto()
                                    .setTitle("Отжимания 30 дней"))
                    .getBody();
            httpStatusCode = coachCourseController.editAuthorCourseGeneralInfo(
                    fourthCourseId,
                    new EditCourseAuthorGeneralInfo()
                            .setTitle("Отжимания 30 дней")
                            .setAbout(
                                    "Программа базового курса по функциональным тренировкам предназначена для людей, "
                                            + "которые ищут альтернативу традиционному тренажёрному залу. Упражнения "
                                            + "с собственным весом отлично подходят для этого. Вам не нужно ничего, "
                                            + "кроме собственного тела, вы можете выбрать любое место для тренировки "
                                            + "и тренироваться, когда захотите и где захотите!")
                            .setShortDescription(
                                    "Программа базового курса по функциональным тренировкам предназначена для людей, "
                                            + "которые ищут альтернативу традиционному тренажёрному залу. Упражнения "
                                            + "с собственным весом отлично подходят для этого. Вам не нужно ничего, "
                                            + "кроме собственного тела, вы можете выбрать любое место для тренировки "
                                            + "и тренироваться, когда захотите и где захотите!")
                            .setCourseFor(
                                    "")
                            .setRequirements("")
                            .setLogo(getFile("content/forDanil/img_2.png"))
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            Long fifthCourseId = (Long)coachCourseController.createCourse(
                            new NewCourseDto()
                                    .setTitle("Фитнес для начинающих"))
                    .getBody();
            httpStatusCode = coachCourseController.editAuthorCourseGeneralInfo(
                    fifthCourseId,
                    new EditCourseAuthorGeneralInfo()
                            .setTitle("Фитнес для начинающих")
                            .setAbout(
                                    "Двухмесячный курс для поддержания мышц тела в тонусе")
                            .setShortDescription(
                                    "Двухмесячный курс для поддержания мышц тела в тонусе")
                            .setCourseFor(
                                    "")
                            .setRequirements("")
                            .setLogo(getFile("content/forDanil/img_4.png"))
            ).getStatusCode();
            checkHttpStatusCode(httpStatusCode);

            firstCardId = (Long)coachCourseController.createCard(
                    new NewCardDto()
                            .setTitle("Наклоны")
                            .setDescription("Наклоны")
                            .setInventoryDescription("")
                            .setMuscleGroupsDescription("")
                            .setImages(Stream.of("content/forDanil/img_6.png").map(this::getFile).toList())
                            .setVideo(getFile("content/benchPress/video.mp4"))
            ).getBody();

            secondCardId = (Long)coachCourseController.createCard(
                    new NewCardDto()
                            .setTitle("Приседания")
                            .setDescription("Приседания")
                            .setInventoryDescription("")
                            .setMuscleGroupsDescription("")
                            .setImages(Stream.of("content/forDanil/img_7.png").map(this::getFile).toList())
                            .setVideo(getFile("content/legPress/video.mp4"))
            ).getBody();

            thirdCardId = (Long)coachCourseController.createCard(
                    new NewCardDto()
                            .setTitle("Планка")
                            .setDescription("Планка")
                            .setInventoryDescription("")
                            .setMuscleGroupsDescription("")
                            .setImages(Stream.of("content/forDanil/img_8.png").map(this::getFile).toList())
                            .setVideo(getFile("content/legPress/video.mp4"))
            ).getBody();

            fourthCardId = (Long)coachCourseController.createCard(
                    new NewCardDto()
                            .setTitle("Ходьба")
                            .setDescription("Ходьба")
                            .setInventoryDescription("")
                            .setMuscleGroupsDescription("")
                            .setImages(Stream.of("content/forDanil/img_9.png").map(this::getFile).toList())
                            .setVideo(getFile("content/legPress/video.mp4"))
            ).getBody();

            GreetingUpdateDto greetingUpdateDto = new GreetingUpdateDto()
                    .setTitle("Добро пожаловать на новый курс \"Фитнес для начинающих\"")
                    .setVariableDtoList(
                            List.of(
                                    new GreetingCourseVariableDto()
                                            .setTitle("Максимальное число подтягиваний")
                                            .setCode("maxNumPullUps")
                                            .setType(CourseVariableTypeEnum.INTEGER),
                                    new GreetingCourseVariableDto()
                                            .setTitle("Время (секунды) преодоления 100 метров")
                                            .setCode("oneHundrMetTime")
                                            .setType(CourseVariableTypeEnum.TIME),
                                    new GreetingCourseVariableDto()
                                            .setTitle("Максимальный вес грифа при жиме от груди")
                                            .setCode("maxWeightBarForChestPress")
                                            .setType(CourseVariableTypeEnum.FLOAT)
                            )
                    );

            httpStatusCode = coachCourseController.updateGreeting(fifthCourseId, greetingUpdateDto).getStatusCode();
            studentCourseController.createBidRegistrationForTheCourse(fifthCourseId);
            StudentGreetingContent studentGreeting =
                    (StudentGreetingContent) studentCourseController.getGreetingContent(fifthCourseId).getBody();
            checkHttpStatusCode(httpStatusCode);

            addModule(fifthCourseId, "1");

            var qwe = catalogController.getCourseInfo(fifthCourseId);

            /*studentCourseController.updateStudentVariableValues(
                    fifthCourseId, List.of(
                            new StudentVariableUpdatedValue(studentGreeting.get)
                    ))*/
            System.out.println("End");
        };
    }

    private MultipartFile getFile(String path)
    {
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
                        .setTitle("Фитнес для начинающих")
                        .setDescription("Фитнес для начинающих")
        ).getBody();

        Long firstLessonId = (Long)coachCourseController.addLessonToAuthorCourseContent(
                courseId,
                firstModuleId,
                new NewCourseAuthorLessonDto()
                        .setTitle(
                                "День 1")
        ).getBody();
        lessonService.addIcon(firstLessonId, getFile("content/forDanil/img_11.png"));
        addStageExerciseInTheMorning(courseId, firstModuleId, firstLessonId);
        addStageWithoutExercises(courseId, firstModuleId, firstLessonId);

        Long secondLessonId = (Long)coachCourseController.addLessonToAuthorCourseContent(
                courseId,
                firstModuleId,
                new NewCourseAuthorLessonDto()
                        .setTitle(
                                "День 2")
        ).getBody();
        lessonService.addIcon(secondLessonId, getFile("content/forDanil/img_12.png"));
        addStageExerciseInTheMorning(courseId, firstModuleId, secondLessonId);
        addStageWithoutExercises(courseId, firstModuleId, secondLessonId);

        Long thirdLessonId = (Long)coachCourseController.addLessonToAuthorCourseContent(
                courseId,
                firstModuleId,
                new NewCourseAuthorLessonDto()
                        .setTitle(
                                "День 3")
        ).getBody();
        lessonService.addIcon(thirdLessonId, getFile("content/forDanil/img_13.png"));
        addStageExerciseInTheMorning(courseId, firstModuleId, thirdLessonId);
        addStageWithoutExercises(courseId, firstModuleId, thirdLessonId);

        Long fourthLessonId = (Long)coachCourseController.addLessonToAuthorCourseContent(
                courseId,
                firstModuleId,
                new NewCourseAuthorLessonDto()
                        .setTitle(
                                "День 4")
        ).getBody();
        lessonService.addIcon(fourthLessonId, getFile("content/forDanil/img_14.png"));
        addStageExerciseInTheMorning(courseId, firstModuleId, fourthLessonId);
        addStageWithoutExercises(courseId, firstModuleId, fourthLessonId);

        /*Long firstModuleId = (Long)coachCourseController.addModuleToAuthorCourseContent(
                courseId,
                new NewCourseAuthorModuleDto()
                        .setTitle("Название " + prefix + " модуля.")
                        .setDescription("Описание " + prefix + " модуля.")
        ).getBody();
        addLesson(courseId, firstModuleId, prefix);
        addLesson(courseId, firstModuleId, prefix);
        addLesson(courseId, firstModuleId, prefix);*/
    }

    private void addStageWithoutExercises(Long courseId, Long moduleId, Long lessonId)
    {
        Long secondStageId = (Long)coachCourseController
                .addStageToAuthorCourseContent(courseId, lessonId, "Тренировка")
                .getBody();

        // IMG
        String firstContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, secondStageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.IMG))
                .getBody();

        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        MultiValueMap<String, MultipartFile> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("image", getFile("content/forDanil/img_10.png"));

        UpdateImgStageContentDto updateImgStageContentDto = new UpdateImgStageContentDto();
        updateImgStageContentDto.setUuid(firstContentId);
        updateImgStageContentDto.setSerialNumber(0);
        updateImgStageContentDto.setType(StageContentType.IMG);

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, secondStageId,
                StageContentType.IMG, convertToMultiValueMap(updateImgStageContentDto),
                getAbstractMultipartHttpServletRequest(request, multiValueMap));

        // TEXT
        String secondContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, secondStageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.TEXT))
                .getBody();

        request = new MockMultipartHttpServletRequest();
        UpdateTextStageContentDto updateTextStageContentDto = new UpdateTextStageContentDto();
        updateTextStageContentDto.setUuid(secondContentId);
        updateTextStageContentDto.setSerialNumber(1);
        updateTextStageContentDto.setType(StageContentType.TEXT);
        updateTextStageContentDto.setTextContent(
                "Утренняя физическая нагрузка до завтрака сжигает больше калорий, чем в другое время суток. Это "
                        + "лучшее средство, чтобы снять сонливость, усталость, зарядиться позитивом и настроить свой "
                        + "организм на самую продуктивную деятельность в течение дня.");

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, secondStageId,
                StageContentType.TEXT, convertToMultiValueMap(updateTextStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        HttpStatusCode httpStatusCode = catalogController.getCourseInfo(courseId).getStatusCode();
        checkHttpStatusCode(httpStatusCode);

    }

    private void addStageExerciseInTheMorning(Long courseId, Long moduleId, Long lessonId)
    {
        Long firstStageId = (Long)coachCourseController
                .addStageToAuthorCourseContent(courseId, lessonId, "Зарядка")
                .getBody();

        // IMG
        String firstContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, firstStageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.IMG))
                .getBody();

        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
        MultiValueMap<String, MultipartFile> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("image", getFile("content/forDanil/img_10.png"));

        UpdateImgStageContentDto updateImgStageContentDto = new UpdateImgStageContentDto();
        updateImgStageContentDto.setUuid(firstContentId);
        updateImgStageContentDto.setSerialNumber(0);
        updateImgStageContentDto.setType(StageContentType.IMG);

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, firstStageId,
                StageContentType.IMG, convertToMultiValueMap(updateImgStageContentDto),
                getAbstractMultipartHttpServletRequest(request, multiValueMap));

        // TEXT
        String secondContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, firstStageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.TEXT))
                .getBody();

        request = new MockMultipartHttpServletRequest();
        UpdateTextStageContentDto updateTextStageContentDto = new UpdateTextStageContentDto();
        updateTextStageContentDto.setUuid(secondContentId);
        updateTextStageContentDto.setSerialNumber(1);
        updateTextStageContentDto.setType(StageContentType.TEXT);
        updateTextStageContentDto.setTextContent(
                "Утренняя физическая нагрузка до завтрака сжигает больше калорий, чем в другое время суток. Это "
                        + "лучшее средство, чтобы снять сонливость, усталость, зарядиться позитивом и настроить свой "
                        + "организм на самую продуктивную деятельность в течение дня.");

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, firstStageId,
                StageContentType.TEXT, convertToMultiValueMap(updateTextStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        // EXERCISES
        String thirdContentId = (String)coachCourseController
                .addContentToStageToAuthorCourseContent(courseId, lessonId, firstStageId,
                        new AddCourseAuthorStageContentInfoDto()
                                .setType(StageContentType.EXERCISES))
                .getBody();
        request = new MockMultipartHttpServletRequest();

        UpdateExerciseRepeatSetContentDto firstTiltsRepeatSet = new UpdateExerciseRepeatSetContentDto();
        firstTiltsRepeatSet.setCountOfKilograms("0.0");
        firstTiltsRepeatSet.setRepeatCount("10");
        firstTiltsRepeatSet.setPauseAfter(LocalTime.of(0, 0, 30));

//        maxNumPullUps oneHundrMetTime maxWeightBarForChestPress
        UpdateExerciseRepeatSetContentDto secondTiltsRepeatSet = new UpdateExerciseRepeatSetContentDto();
        secondTiltsRepeatSet.setCountOfKilograms("0.0");
        secondTiltsRepeatSet.setRepeatCount("12");
        secondTiltsRepeatSet.setPauseAfter(LocalTime.of(0, 0, 30));

        UpdateRepeatExerciseContentDto tiltsExercise = new UpdateRepeatExerciseContentDto();
        tiltsExercise.setCardId(firstCardId);
        tiltsExercise.setSets(List.of(firstTiltsRepeatSet, secondTiltsRepeatSet));

        UpdateExerciseTimeSetContentDto firstBarTimeSet = new UpdateExerciseTimeSetContentDto();
        firstBarTimeSet.setCountOfKilograms("maxNumPullUps + maxWeightBarForChestPress / 10 - 5");
        firstBarTimeSet.setExecutionTime("(maxNumPullUps + maxWeightBarForChestPress) * 0.813");
        firstBarTimeSet.setPauseAfter(LocalTime.of(0, 0, 30));

        UpdateTimeExerciseContentDto barExercise = new UpdateTimeExerciseContentDto();
        barExercise.setCardId(thirdCardId);
        barExercise.setSets(List.of(firstBarTimeSet));

        UpdateExerciseDistanceSetContentDto firstStepsDistanceSet = new UpdateExerciseDistanceSetContentDto();
        firstStepsDistanceSet.setCountOfKilograms("0");
        firstStepsDistanceSet.setDistanceKilometers("oneHundrMetTime * 0.075");
        firstStepsDistanceSet.setPauseAfter(LocalTime.of(0, 0, 20));

        UpdateExerciseDistanceSetContentDto secondStepsDistanceSet = new UpdateExerciseDistanceSetContentDto();
        secondStepsDistanceSet.setCountOfKilograms("oneHundrMetTime / 10");
        secondStepsDistanceSet.setDistanceKilometers("(oneHundrMetTime / 10) + 0.5");
        secondStepsDistanceSet.setPauseAfter(LocalTime.of(0, 0, 15));

        UpdateDistanceExerciseContentDto stepsExercise = new UpdateDistanceExerciseContentDto();
        stepsExercise.setCardId(fourthCardId);
        stepsExercise.setSets(List.of(firstStepsDistanceSet, secondStepsDistanceSet));

        UpdateExercisesStageContentDto updateExercisesStageContentDto = new UpdateExercisesStageContentDto();
        updateExercisesStageContentDto.setUuid(thirdContentId);
        updateExercisesStageContentDto.setSerialNumber(2);
        updateExercisesStageContentDto.setType(StageContentType.EXERCISES);
        updateExercisesStageContentDto.setExercises(List.of(tiltsExercise, barExercise, stepsExercise));

        coachCourseController.updateContentToStageToAuthorCourseContent(courseId, lessonId, firstStageId,
                StageContentType.EXERCISES, convertToMultiValueMap(updateExercisesStageContentDto),
                new StandardMultipartHttpServletRequest(request));

        CourseAuthorStageWithContentInfoDto stageContent =
                (CourseAuthorStageWithContentInfoDto)coachCourseController.getStageWithContentToAuthorCourseContent(
                        courseId, lessonId, firstStageId).getBody();
        System.out.println();

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
        firstDistanceSet.setCountOfKilograms("0");
        firstDistanceSet.setDistanceKilometers("oneHundrMetTime * 100");
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
        /*// IMG
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
        System.out.println();*/
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
