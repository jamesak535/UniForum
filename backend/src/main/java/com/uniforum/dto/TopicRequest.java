package com.uniforum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicRequest {
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;
    
    @Size(max = 2000)
    private String description;
    
    @NotBlank
    private String category;
}