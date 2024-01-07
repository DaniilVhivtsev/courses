package com.fitness.courses.http.student.model.dto.stage.content;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ImgStageContentInfoDto", allOf = AbstractStageContentInfoDto.class)
public class ImgStageContentInfoDto extends AbstractStageContentInfoDto
{
    private String url;

    public ImgStageContentInfoDto()
    {
        super.type = StageContentType.IMG;
    }

    public ImgStageContentInfoDto(String url)
    {
        this.url = url;
        super.type = StageContentType.IMG;
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
