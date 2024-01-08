package com.fitness.courses.http.auth.dto;

public class RegistrationUserInfoDto
{
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getFirstName()
    {
        return firstName;
    }

    public RegistrationUserInfoDto setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public String getLastName()
    {
        return lastName;
    }

    public RegistrationUserInfoDto setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public RegistrationUserInfoDto setEmail(String email)
    {
        this.email = email;
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public RegistrationUserInfoDto setPassword(String password)
    {
        this.password = password;
        return this;
    }
}
