package com.CN.PharmaLink.dto;

import lombok.Data;
import java.util.List;

@Data
public class MedicalStoreDto {

    private Long id;
    private String name;
    private Long contact;
    private String area;
    private Double latitude;
    private Double longitude;
    private List<String> medicines;
}