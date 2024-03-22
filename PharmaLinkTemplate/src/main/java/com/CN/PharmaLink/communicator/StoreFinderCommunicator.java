package com.CN.PharmaLink.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.CN.PharmaLink.dto.MedicinesDto;
import com.CN.StoreFinder.dto.MedicalStoreDto;
import com.CN.StoreFinder.model.MedicalStore;

@Service
public class StoreFinderCommunicator {

    private final RestTemplate restTemplate;

    @Autowired
    public StoreFinderCommunicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public List<MedicalStoreDto> getNearestMedicalStores(Long userId, Long distance, String jwtToken) {
        String url = "http://localhost:8081/store/getNearestStores/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Map<String, Long>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<MedicalStore>> storeEntity = restTemplate.exchange(
                url + userId + "/" + distance,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<MedicalStore>>() {
                });

        List<MedicalStore> aList = storeEntity.getBody();
        List<MedicalStoreDto> aList2 = new ArrayList<MedicalStoreDto>();
        for (MedicalStore store : aList) {
            MedicalStoreDto dto = new MedicalStoreDto();
            dto.setArea(store.getArea());
            dto.setContact(store.getContact());
            dto.setMedicines(store.getMedicines());
            dto.setName(store.getName());
            dto.setLatitude(store.getLatitude());
            dto.setLongitude(store.getLongitude());
            aList2.add(dto);
        }
        return aList2;
    }

    public List<MedicalStoreDto> getMedicalStoresWithMedicine(String medicine, String jwtToken) {
        String url = "http://localhost:8081/store/getStoresWithMedicine/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Map<String, Long>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<MedicalStore>> storeEntity = restTemplate.exchange(
                url + medicine,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<MedicalStore>>() {
                });

        List<MedicalStore> aList = storeEntity.getBody();
        List<MedicalStoreDto> aList2 = new ArrayList<MedicalStoreDto>();
        for (MedicalStore store : aList) {
            MedicalStoreDto dto = new MedicalStoreDto();
            dto.setArea(store.getArea());
            dto.setContact(store.getContact());
            dto.setMedicines(store.getMedicines());
            dto.setName(store.getName());
            dto.setLatitude(store.getLatitude());
            dto.setLongitude(store.getLongitude());
            aList2.add(dto);
        }
        return aList2;
    }

    public List<MedicalStoreDto> getAllNearestMedicalStoresWithMedicine(Long userId, Long distance, String medicine,
            String jwtToken) {
        String url = "http://localhost:8081/store/getNearsetMedicalStoresHavingMedicine/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Map<String, Long>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<MedicalStore>> storeEntity = restTemplate.exchange(
                url + userId + "/" + distance + "/" + medicine + "/" + jwtToken,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<MedicalStore>>() {
                });

        List<MedicalStore> nearestStores = storeEntity.getBody();
        List<MedicalStoreDto> nearestStoresWithMedicine = new ArrayList<>();

        for (MedicalStore store : nearestStores) {
            if (store.getMedicines().contains(medicine)) {
                MedicalStoreDto dto = new MedicalStoreDto();
                dto.setArea(store.getArea());
                dto.setContact(store.getContact());
                dto.setMedicines(store.getMedicines());
                dto.setName(store.getName());
                dto.setLatitude(store.getLatitude());
                dto.setLongitude(store.getLongitude());
                nearestStoresWithMedicine.add(dto);
            }
        }

        return nearestStoresWithMedicine;
    }

    public List<MedicalStoreDto> getNearestMedicalStores(Long userId, String jwtToken) {
        String url = "http://localhost:8081/store/getNearestStores/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        HttpEntity<Map<String, Long>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<MedicalStore>> storeEntity = restTemplate.exchange(
                url + userId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<MedicalStore>>() {
                });

        List<MedicalStore> aList = storeEntity.getBody();
        List<MedicalStoreDto> aList2 = new ArrayList<MedicalStoreDto>();
        for (MedicalStore store : aList) {
            MedicalStoreDto dto = new MedicalStoreDto();
            dto.setArea(store.getArea());
            dto.setContact(store.getContact());
            dto.setMedicines(store.getMedicines());
            dto.setName(store.getName());
            dto.setLatitude(store.getLatitude());
            dto.setLongitude(store.getLongitude());
            aList2.add(dto);
        }
        return aList2;
    }

    public List<MedicalStoreDto> getMedicalStoresWithListMedicines(MedicinesDto medicinesDto, String token) {
        String url = "http://localhost:8081/store/getStoresWithListMedicines/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<MedicinesDto> requestEntity = new HttpEntity<>(medicinesDto, headers);
        try {
            ResponseEntity<List<MedicalStore>> storeEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<MedicalStore>>() {
                    });

            List<MedicalStore> aList = storeEntity.getBody();
            List<MedicalStoreDto> aList2 = new ArrayList<>();
            for (MedicalStore store : aList) {
                MedicalStoreDto dto = new MedicalStoreDto();
                dto.setArea(store.getArea());
                dto.setContact(store.getContact());
                dto.setMedicines(store.getMedicines());
                dto.setName(store.getName());
                dto.setLatitude(store.getLatitude());
                dto.setLongitude(store.getLongitude());
                aList2.add(dto);
            }
            return aList2;
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

}
