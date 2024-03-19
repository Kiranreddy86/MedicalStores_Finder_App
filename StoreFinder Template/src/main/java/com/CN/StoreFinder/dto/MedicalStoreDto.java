package com.CN.StoreFinder.dto;

import lombok.Data;

import java.util.List;

@Data
public class MedicalStoreDto {
    private String name;
    private Long contact;
    private String area;
    private Double latitude;
    private Double longitude;
    private List<String> medicines;
}
