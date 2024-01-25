package com.fitness.courses.global.dozer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dozer.DozerConverter;

public class LocalDateTimeToStringConverter extends DozerConverter<LocalDateTime, String>
{

    public LocalDateTimeToStringConverter()
    {
        super(LocalDateTime.class, String.class);
    }

    @Override
    public String convertTo(LocalDateTime source, String destination)
    {
        return source != null ? source.toString() : null;
    }

    @Override
    public LocalDateTime convertFrom(String source, LocalDateTime destination)
    {
        return source != null ? LocalDateTime.parse(source) : null;
    }
}
