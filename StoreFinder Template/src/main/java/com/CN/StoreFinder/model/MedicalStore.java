package com.CN.StoreFinder.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medical_store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long contact;
    private String area;
    @NonNull
    private Double latitude;
    @NonNull
    private Double longitude;
    @ElementCollection
    private List<String> medicines;

}
