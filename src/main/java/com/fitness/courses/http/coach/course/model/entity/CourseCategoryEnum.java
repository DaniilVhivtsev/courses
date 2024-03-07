package com.fitness.courses.http.coach.course.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CourseCategoryEnum
{
    CARDIO("CARDIO");

    private final String value;

    CourseCategoryEnum(String value)
    {
        this.value = value;
    }

    @JsonValue
    public String getValue()
    {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CourseCategoryEnum fromValue(String value) {
        for (CourseCategoryEnum b : CourseCategoryEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
