package com.fitness.courses.http.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.courses.http.attachment.model.info.MultipartFileWithExtension;
import com.fitness.courses.http.attachment.service.AttachmentService;
import com.fitness.courses.http.auth.service.AuthService;
import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.student.model.dto.achievement.UserCourseAchievementInfoDto;
import com.fitness.courses.http.student.model.dto.achievement.UserCourseWithAchievementsInfoDto;
import com.fitness.courses.http.student.service.RestStudentCoursesService;
import com.fitness.courses.http.user.dto.GeneralInfo;
import com.fitness.courses.http.user.mapper.UserMapper;
import com.fitness.courses.http.user.model.User;
import com.fitness.courses.http.user.model.dto.EditUserGeneralInfoDto;

@Service
public class RestUserServiceImpl implements RestUserService
{
    private final UserService userService;
    private final AuthService authService;
    private final AttachmentService attachmentService;
    private final RestStudentCoursesService restStudentCoursesService;

    // TODO Убрать
    private static final String QUARTER_COMPLETED_ACHIEVEMENT_IMG_URL =
            "https://www.flaticon.com/ru/download/icon/6811131?icon_id=6811131&author=267&team=267&keyword=%D0%93%D0"
                    + "%BE%D0%B4%D0%BE%D0%B2%D1%89%D0%B8%D0%BD%D0%B0&pack=6811033&style=Flat&style_id=6&format=png"
                    + "&color=%23000000&colored=2&size=512&selection=1&type=standard&search=25+%D0%BB%D0%B5%D1%82"
                    + "&search=25+%D0%BB%D0%B5%D1%82";

    private static final String HALF_COMPLETED_ACHIEVEMENT_IMG_URL =
            "https://www.flaticon.com/ru/download/icon/6811136?icon_id=6811136&author=267&team=267&keyword=%D0%93%D0"
                    + "%BE%D0%B4%D0%BE%D0%B2%D1%89%D0%B8%D0%BD%D0%B0&pack=6811033&style=Flat&style_id=6&format=png"
                    + "&color=%23000000&colored=2&size=512&selection=1&type=standard&token"
                    + "=03AFcWeA5mHOoKQkDTnU8_u409I9GHzz34deRTlASa8Qiaq85CJNWS_u0ZMlowispK"
                    +
                    "-LWSNG5ufc9kgn_OdVm9tRiAhydvx7e0oJgJmtNi1bN6z0hKYuiCJW9h4Mf9eSCRdrp0keJBPhAEYHr55hHZBtKke5aiZEoRzHxCUytl6w62czBur7azFYGBUgWG7PIyXGk08TcDrkPZ0FMNGbyFz23jAHhsdhEuH1Vy6pvepxTamVe1UXJ9tZMjfWuj3TUz5Ygj_prnInMOK2ZQhKzBumRAKO39WX4IS0dZxvLwLk-L7pBELgCzHgeinefK6jMHm7fnEQj8leyHISNURfMC-4TNp7RPNOwKTe_WQQz6-Ni1-nNfG-JTSoCNHzikLHJM--f0hbyLRWqz_cn2dhY5lmws80fMJpKpYGL1zgcaBxcESUuRmmnQj2AlDU-i-iknCeHD8V0FTCy1OZ287i1bDrjtiLTRGpZZeSTSIDiyur_u_Ztfn4lW2yaqaF4B53W0-fzSwux7o9znh8e6QkEUfOgCJHPpN7gy4eO5Mk9OW6agXClOBaavOGR7ORW8mkOrLYs0aDt0nt670fC59f5n1cAOtBtfywQrw7CLtMa-464qCLVyKDJ6hmI2iYYZRO24T0AHSndRVFRKToV0M7dYEgJmBBfWNh8faCQ78vNHb0F-mvq0uiw7e1QpWJR_QWWb7_AQT2Fv23O9Cf-PSovVi-TmBGAHplxbyqmPL5__kalDAA42lPU77wbbXDJGBZJ2ip4xu0DodLIz6ggFOZfLY9nWtwKBHoLW7C2VEqSEDhk--KLADzq5grdyKYvouJk7pxT4GVljYja7e6NxHautb5z4wl7almnj-jBHEzH5T8WT1xWeojtTBQPWVTcWB-23_o1gA0ATV_rQtr8eLCm-01UIF8TGb1HpR7T5as6txtUbMsSdcQoBJbraCTa4oFFEbo4BRB2QSZrSVy7rIRJUWlL-JrxGFqfQ6kpJu7aJvvuy_Qdkee6zjIqzsR0NARA18BeSiMBrZCktwnw3VkQGMMKaRpsgSEBlx7KSW6pAmmHV4J9OMhazXprVpPjQ8GnZeT550WKU0mpb17NkrW2umdTGrbLZtLfCZnrFZIyerIgZBV8_r1JHJ8enDad7bBynhdoaa5D1QPwM0ydSH5gNIMkM1bwLE4CDDF5uH7X2RG5XMh9NkVjXpWM8SiAMcxRgbhp-5FqCfajIcSrccpiQYt6OyS-Q85NBPM1BmHx6Du-NPYLokm4OW8UEu6XBJZzo7zLrIyQiOXFK_klKBRwWlU0PUzpH6IOb94x-JWlxkIhm6oOB47lltYtJxRS1ZTfqfioBO0MEJObIudgpGWvhV18R8eLF69hw-uN1XFX30RmpPP-6tisa45YvEdsUjzTC-fheE8BkE1JzaMG6ZaR1raM1suD8XRR_g87vxZyk3f7CGoxl8bwSrbL72bhbcYt6Z9XlbM346J5VaEyfLRJ1TPlSpIUPLcnkNCF2N-QeuH2hurmihnEnW0ElVok-WuyMIep4BYze2CZ2NA7AnwJrUxEbxjcI1V7VdA&search=50+%D0%BB%D0%B5%D1%82&search=50+%D0%BB%D0%B5%D1%82";

    private static final String COMPLETELY_PASSED_ACHIEVEMENT_IMG_URL =
            "https://www.flaticon.com/ru/download/icon/6198562?icon_id=6198562&author=227&team=227&keyword=%D0%93%D0"
                    + "%BE%D0%B4%D0%BE%D0%B2%D1%89%D0%B8%D0%BD%D0%B0&pack=6198198&style=1&style_id=3&format=png&color"
                    + "=%23000000&colored=2&size=512&selection=1&type=standard&search=100&search=100";

    @Autowired
    public RestUserServiceImpl(
            UserService userService,
            AuthService authService,
            AttachmentService attachmentService,
            RestStudentCoursesService restStudentCoursesService)
    {
        this.userService = userService;
        this.authService = authService;
        this.attachmentService = attachmentService;
        this.restStudentCoursesService = restStudentCoursesService;
    }

    @Override
    public GeneralInfo editUserInfo(EditUserGeneralInfoDto editUserGeneralInfoDto)
    {
        final User currentUser = authService.getCurrentUserOrThrow();

        currentUser.setFirstName(editUserGeneralInfoDto.getName());
        currentUser.setLastName(editUserGeneralInfoDto.getSurname());
        currentUser.setBiography(editUserGeneralInfoDto.getBiography());
        currentUser.setAbout(editUserGeneralInfoDto.getAbout());

        if (editUserGeneralInfoDto.getIcon() != null)
        {
            currentUser.setLogo(
                    attachmentService.add(
                            new MultipartFileWithExtension(
                                    FileExtensionEnum.getEnum(
                                            FilenameUtils.getExtension(
                                                    editUserGeneralInfoDto.getIcon().getOriginalFilename())),
                                    editUserGeneralInfoDto.getIcon()
                            )
                    )
            );
        }

        return UserMapper.toGeneralInfo(userService.update(currentUser));
    }

    @Override
    public GeneralInfo getCurrentUserGeneralInfo()
    {
        return UserMapper.toGeneralInfo(authService.getCurrentUserOrThrow());
    }

    @Override
    public GeneralInfo getCurrentInfo(@NotNull Long userId)
    {
        return UserMapper.toGeneralInfo(userService.findById(userId).orElseThrow());
    }

    @Override
    public List<UserCourseWithAchievementsInfoDto> getCurrentUserAchievements()
    {
        UserCourseAchievementInfoDto quarterCompletedAchievementInfoDto = new UserCourseAchievementInfoDto()
                .setId(1L)
                .setTitle("Четверть курса пройдено.")
                .setDescription("Четверть курса пройдено.")
                .setImgUrl(QUARTER_COMPLETED_ACHIEVEMENT_IMG_URL);

        UserCourseAchievementInfoDto halfCompletedAchievementInfoDto = new UserCourseAchievementInfoDto()
                .setId(2L)
                .setTitle("Половина курса пройдена.")
                .setDescription("Половина курса пройдена.")
                .setImgUrl(HALF_COMPLETED_ACHIEVEMENT_IMG_URL);
        UserCourseAchievementInfoDto completelyPassedAchievementInfoDto = new UserCourseAchievementInfoDto()
                .setId(3L)
                .setTitle("Курс полностью пройден.")
                .setDescription("Курс полностью пройден.")
                .setImgUrl(COMPLETELY_PASSED_ACHIEVEMENT_IMG_URL);

        return restStudentCoursesService.getStudentCurrentCoursesInfo().stream()
                .map(course ->
                {
                    UserCourseWithAchievementsInfoDto dto = new UserCourseWithAchievementsInfoDto()
                            .setId(course.getId())
                            .setTitle(course.getTitle());
                    if (course.getIconImgUrl() != null)
                    {
                        dto.setImgUrl(course.getIconImgUrl());
                    }

                    dto.setUserAchievements(new ArrayList<>());

                    if (course.getPercentagePassed() != null)
                    {
                        if (course.getPercentagePassed() >= 25.0)
                        {
                            dto.getUserAchievements().add(quarterCompletedAchievementInfoDto);
                        }

                        if (course.getPercentagePassed() >= 50.0)
                        {
                            dto.getUserAchievements().add(halfCompletedAchievementInfoDto);
                        }

                        if (course.getPercentagePassed() >= 100.0)
                        {
                            dto.getUserAchievements().add(completelyPassedAchievementInfoDto);
                        }
                    }

                    return dto;
                })
                .toList();
    }
}
