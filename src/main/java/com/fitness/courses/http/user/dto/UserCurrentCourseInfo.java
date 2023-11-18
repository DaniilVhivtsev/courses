package com.fitness.courses.http.user.dto;

import java.util.Date;

public class UserCurrentCourseInfo
{
    private Long courseId;
    private String courseTitle;
    private String courseDescription;
    private Integer percentagePassed;
    private Integer points;
    private Date courseEndDate;
    private AttachmentDto iconImgDto;

    public Long getCourseId()
    {
        return courseId;
    }

    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }

    public String getCourseTitle()
    {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle)
    {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription()
    {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription)
    {
        this.courseDescription = courseDescription;
    }

    public Integer getPercentagePassed()
    {
        return percentagePassed;
    }

    public void setPercentagePassed(Integer percentagePassed)
    {
        this.percentagePassed = percentagePassed;
    }

    public Integer getPoints()
    {
        return points;
    }

    public void setPoints(Integer points)
    {
        this.points = points;
    }

    public Date getCourseEndDate()
    {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate)
    {
        this.courseEndDate = courseEndDate;
    }

    public AttachmentDto getIconImgDto()
    {
        return iconImgDto;
    }

    public void setIconImgDto(AttachmentDto iconImgDto)
    {
        this.iconImgDto = iconImgDto;
    }
}
