package com.fitness.courses.http.student.model.dto.stage.content;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "VideoStageContentInfoDto", allOf = AbstractStageContentInfoDto.class)
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
