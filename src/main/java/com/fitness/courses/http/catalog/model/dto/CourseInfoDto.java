package com.fitness.courses.http.catalog.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fitness.courses.http.catalog.model.dto.content.ModuleInfoDto;
import com.fitness.courses.http.coach.course.model.entity.CourseCategoryEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Ответ сервера с информации (публичной и содержанием: модули, уроки) о курсе.")
public class CourseInfoDto
{
    @Schema(description = "Идентификатор курса.")
    private Long id;

    @Schema(description = "Название курса.")
    private String title;

    @Schema(description = "Краткое описание курса.")
    private String shortDescription;

    @Schema(description = "Категории курса курса. ПОКА ЗДЕСЬ НИЧЕГО")
    private List<CourseCategoryEnum> categories;

    @Schema(description = "Полное описание курса.")
    private String about;

    @Schema(description = "Описание для кого данный курс.")
    private String courseFor;

    @Schema(description = "Требования для прохождения данного курса. (Может быть также: какой инвентарь требуется)")
    private String requirements;

    @Schema(description = "Идентификатор автора курса.")
    private Long authorId;

    @Schema(description = "Полное имя (Фамилия Имя) автора курса.")
    private String authorFullName;

    @Schema(description = "Краткое описание автора курса.")
    private String authorShortDescription;

    @Schema(description = "Url иконки автора курса.")
    private String authorIconImgDto;

    @Schema(description = "Дата создания курса.")
    private LocalDateTime dateTimeCreated;

    @Schema(description = "Url для получения иконки курса.")
    private String iconImgUrl;

    @Schema(description = "Рейтинг курса.")
    private Double rating;

    @Schema(description = "Количество студентов на курсе.")
    private Integer numberOfPeople;

    @Schema(description = "Бесплатный ли курс. true - курс бесплатный, false - курс платный).")
    private boolean isFree;

    @Schema(description = "Цена за курс. (Если курс платный)")
    private Integer price;

    @Schema(description = "Пользователь является студентом данного курса. true - если пользователь прошел "
            + "аутентификацию и является студентом, иначе - false.")
    private boolean userIsRegisteredForTheCourse;

    @Schema(description = "Модули курса. (Содержание курса).")
    private List<ModuleInfoDto> modules;

    @Schema(description = "Идентификатор урока, на котором остановился студент. Может быть null, т.к. пользователь "
            + "может не быть студентом курса или может в курсе отсутствуют уроки.")
    private Long lessonUuidStoppedAt;

    @Schema(description = "Идентификатор этапа, на котором остановился студент. Может быть null, т.к. пользователь "
            + "может не быть студентом курса или может в курсе отсутствуют этапы.")
    private Long stageUuidStoppedAt;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public List<CourseCategoryEnum> getCategories()
    {
        return categories;
    }

    public void setCategories(List<CourseCategoryEnum> categories)
    {
        this.categories = categories;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public String getCourseFor()
    {
        return courseFor;
    }

    public void setCourseFor(String courseFor)
    {
        this.courseFor = courseFor;
    }

    public String getRequirements()
    {
        return requirements;
    }

    public void setRequirements(String requirements)
    {
        this.requirements = requirements;
    }

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public String getAuthorFullName()
    {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName)
    {
        this.authorFullName = authorFullName;
    }

    public String getAuthorShortDescription()
    {
        return authorShortDescription;
    }

    public void setAuthorShortDescription(String authorShortDescription)
    {
        this.authorShortDescription = authorShortDescription;
    }

    public String getAuthorIconImgDto()
    {
        return authorIconImgDto;
    }

    public void setAuthorIconImgDto(String authorIconImgDto)
    {
        this.authorIconImgDto = authorIconImgDto;
    }

    public LocalDateTime getDateTimeCreated()
    {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated)
    {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getIconImgUrl()
    {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl)
    {
        this.iconImgUrl = iconImgUrl;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Integer getNumberOfPeople()
    {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople)
    {
        this.numberOfPeople = numberOfPeople;
    }

    public boolean getIsFree()
    {
        return isFree;
    }

    public void setFree(boolean free)
    {
        isFree = free;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public boolean isUserIsRegisteredForTheCourse()
    {
        return userIsRegisteredForTheCourse;
    }

    public void setUserIsRegisteredForTheCourse(boolean userIsRegisteredForTheCourse)
    {
        this.userIsRegisteredForTheCourse = userIsRegisteredForTheCourse;
    }

    public List<ModuleInfoDto> getModules()
    {
        return modules;
    }

    public void setModules(List<ModuleInfoDto> modules)
    {
        this.modules = modules;
    }

    public boolean isFree()
    {
        return isFree;
    }

    public Long getLessonUuidStoppedAt()
    {
        return lessonUuidStoppedAt;
    }

    public void setLessonUuidStoppedAt(Long lessonUuidStoppedAt)
    {
        this.lessonUuidStoppedAt = lessonUuidStoppedAt;
    }

    public Long getStageUuidStoppedAt()
    {
        return stageUuidStoppedAt;
    }

    public void setStageUuidStoppedAt(Long stageUuidStoppedAt)
    {
        this.stageUuidStoppedAt = stageUuidStoppedAt;
    }
}
