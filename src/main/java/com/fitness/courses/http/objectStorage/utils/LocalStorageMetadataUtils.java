package com.fitness.courses.http.objectStorage.utils;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.s3.internal.Mimetypes;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;

public class LocalStorageMetadataUtils
{
    private LocalStorageMetadataUtils()
    {
    }

    public static ObjectMetadata getObjectMetadata(LocalStorageFileEntity localStorageFileEntity, InputStream inputStream)
    {
        return switch (localStorageFileEntity.getContentType())
                {
                    case IMG -> getImgObjectMetadata(localStorageFileEntity, inputStream);
                };
    }

    private static ObjectMetadata getImgObjectMetadata(LocalStorageFileEntity localStorageFileEntity, InputStream inputStream)
    {
        ObjectMetadata objectMetadata = new ObjectMetadata();
/*        try
        {
            int inputStreamAllBytesLength = inputStream.readAllBytes().length;
            inputStream.reset();
            objectMetadata.setContentLength(inputStreamAllBytesLength);
        }
        catch(IOException e)
        {
            //
        }*/
        objectMetadata.setContentType(Mimetypes.MIMETYPE_OCTET_STREAM);

        return objectMetadata;
    }
}
