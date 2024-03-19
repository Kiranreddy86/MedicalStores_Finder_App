package com.CN.StoreFinder.service;

import com.CN.StoreFinder.dto.MedicalStoreDto;
import com.CN.StoreFinder.model.MedicalStore;
import com.CN.StoreFinder.model.User;
import com.CN.StoreFinder.repository.MedicalStoreRepository;
import com.CN.StoreFinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalStoreService {

    private static final double EARTH_RADIUS = 6371;

    @Autowired
    private MedicalStoreRepository repository;

    @Autowired
    private UserRepository userRepository;

    public MedicalStore createMedicalStore(MedicalStoreDto medicalStoreDto) {
        MedicalStore store = new MedicalStore();
        store.setArea(medicalStoreDto.getArea());
        store.setName(medicalStoreDto.getName());
        store.setContact(medicalStoreDto.getContact());
        store.setMedicines(medicalStoreDto.getMedicines());
        store.setLatitude(medicalStoreDto.getLatitude());
        store.setLongitude(medicalStoreDto.getLongitude());
        return repository.save(store);
    }

    public MedicalStore getMedicalStoreById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical Store not found with id: " + id));
    }

    public List<MedicalStore> getAllMedicalStores() {
        return repository.findAll();
    }

    public MedicalStore updateMedicalStore(Long id, MedicalStoreDto medicalStoreDto) {
        MedicalStore existingStore = getMedicalStoreById(id);
        existingStore.setArea(medicalStoreDto.getArea());
        existingStore.setName(medicalStoreDto.getName());
        existingStore.setContact(medicalStoreDto.getContact());
        existingStore.setMedicines(medicalStoreDto.getMedicines());
        existingStore.setLatitude(medicalStoreDto.getLatitude());
        existingStore.setLongitude(medicalStoreDto.getLongitude());
        return repository.save(existingStore);
    }

    public void deleteMedicalStore(Long id) {
        repository.deleteById(id);
    }

    public long calculateDistance(Long userId, MedicalStore store) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user.getLatitude() + " " + user.getLongitude() + " " + store.getLongitude() + " "
                + store.getLatitude());

        double lat1 = user.getLatitude();
        double lon1 = user.getLongitude();
        double lat2 = store.getLatitude();
        double lon2 = store.getLongitude();

        double latDiff = Math.toRadians(lat2 - lat1);
        double lonDiff = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(latDiff / 2), 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.pow(Math.sin(lonDiff / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        return Math.round(distance);
    }

    public List<MedicalStore> getNearestMedicalStores(Long userId, Long distance) {
        return getAllMedicalStores().stream().filter(store -> calculateDistance(userId, store) <= distance)
                .collect(Collectors.toList());
    }

    public List<MedicalStore> getMedicalStoresHavingMedicine(String medicine) {
        return getAllMedicalStores().stream().filter(store -> store.getMedicines().contains(medicine))
                .collect(Collectors.toList());
    }

    public List<MedicalStore> getNearsetMedicalStoresHavingMedicine(Long userId, Long distance, String medicine) {
        List<MedicalStore> nearestMedicalStores = getNearestMedicalStores(userId, distance);
        return nearestMedicalStores.stream()
                .filter(store -> store.getMedicines().contains(medicine))
                .collect(Collectors.toList());
    }

}
