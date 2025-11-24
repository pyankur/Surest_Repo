package com.surest.management.Surest_Management_App.dto;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.UUID;


public class MemberDto {
    public UUID id;
    public String firstName;
    public String lastName;
    public LocalDate dateOfBirth;
    public String email;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;
}
