package com.fitness.courses.http.coach.course.content.model.dto.stage.content.get;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "VideoStageContentInfoDtoAuthor")
public class VideoStageContentInfoDto extends AbstractStageContentInfoDto
{
    private String url;

    public VideoStageContentInfoDto()
    {
        super.type = StageContentType.VIDEO;
    }

    public VideoStageContentInfoDto(String url)
    {
        this.url = url;
        super.type = StageContentType.VIDEO;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
