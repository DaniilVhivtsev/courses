package com.fitness.courses.http.massenger.service.message;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fitness.courses.global.exceptions.ValidationException;

@Service
public class MessageValidatorImpl implements MessageValidator
{
    private static final @NotNull Logger LOG = LoggerFactory.getLogger(MessageValidatorImpl.class);

    @Override
    public void validateTextMessage(String textMessage) throws ValidationException
    {
        if (StringUtils.isBlank(textMessage))
        {
            final String message = "Text message is blank";
            LOG.error(message);
            throw new ValidationException(message);
        }
    }
}
