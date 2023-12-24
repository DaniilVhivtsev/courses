package com.fitness.courses.http.coach.course.content.model.dto.stage.content.update;

import org.springframework.web.multipart.MultipartFile;

public class UpdateImgStageContentDto extends UpdateAbstractStageContentDto
{
    private MultipartFile image;

    public MultipartFile getImage()
    {
        return image;
    }

    public void setImage(MultipartFile image)
    {
        this.image = image;
    }
}
