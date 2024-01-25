package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

import org.springframework.web.multipart.MultipartFile;

public class UpdateVideoStageContentDto extends UpdateAbstractStageContentDto
{
    private MultipartFile video;

    public MultipartFile getVideo()
    {
        return video;
    }

    public void setVideo(MultipartFile video)
    {
        this.video = video;
    }
}
