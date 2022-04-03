package com.example.springmyitems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {

    private int id;
    @NotBlank
    @Size(min = 3)
    private String title;
    @DecimalMin(value = "10")
    private double price;
    private String description;
    private List<Integer> categories;

}
