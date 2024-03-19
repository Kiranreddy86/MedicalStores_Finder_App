package com.CN.PharmaLink.service;

import com.CN.PharmaLink.communicator.StoreFinderCommunicator;
import com.CN.PharmaLink.dto.UserRequest;
import com.CN.PharmaLink.model.Role;
import com.CN.PharmaLink.model.User;
import com.CN.PharmaLink.repository.UserRepository;
import com.CN.StoreFinder.dto.MedicalStoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StoreFinderCommunicator communicator;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void createUser(UserRequest userRequest) {
		String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
		User user = new User();
		user.setUsername(userRequest.getUsername());
		user.setPassword(encodedPassword);
		Role role = new Role();
		role.setRoleName("ROLE_ADMIN");
		Set<Role> roleSet = new HashSet<>();
		roleSet.add(role);
		user.setRoles(roleSet);
		user.setLatitude(userRequest.getLatitude());
		user.setLongitude(userRequest.getLongitude());
		userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public List<MedicalStoreDto> getNearestMedicalStores(Long userId, Long distance, String jwtToken) {
		User user = userRepository.findById(userId).get();
		List<MedicalStoreDto> storesDtos = communicator.getNearestMedicalStores(userId, distance, jwtToken);
		return storesDtos;
	}

	public List<MedicalStoreDto> getMedicalStoresWithMedicine(String medicine, String jwtToken) {
		List<MedicalStoreDto> storesDtos = communicator.getMedicalStoresWithMedicine(medicine, jwtToken);
		return storesDtos;
	}

	public List<MedicalStoreDto> getNearsetMedicalStoresHavingMedicine(Long userId, Long distance, String medicine,
			String token) {
		User user = userRepository.findById(userId).get();
		List<MedicalStoreDto> storesDtos = communicator.getAllNearestMedicalStoresWithMedicine(userId, distance,
				medicine, token);
		return storesDtos;
	}

}
