package com.CN.PharmaLink.controller;

import com.CN.PharmaLink.dto.UserRequest;
import com.CN.PharmaLink.dto.MedicinesDto;
import com.CN.PharmaLink.model.User;
import com.CN.PharmaLink.service.UserService;
import com.CN.StoreFinder.dto.MedicalStoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerUser(@RequestBody UserRequest userRequest) {
		userService.createUser(userRequest);
	}

	@GetMapping("/getNearestStores/{userId}/{distance}/{token}")
	public List<MedicalStoreDto> getNearestMedicalStores(@PathVariable Long userId, @PathVariable Long distance,
			@PathVariable String token) {
		return userService.getNearestMedicalStores(userId, distance, token);

	}

	@GetMapping("/getStoresWithMedicine/{medicine}/{token}")
	public List<MedicalStoreDto> getMedicalStoresHavingMedicine(@PathVariable String medicine,
			@PathVariable String token) {
		return userService.getMedicalStoresWithMedicine(medicine, token);
	}

	@GetMapping("/getNearsetMedicalStoresHavingMedicine/{userId}/{distance}/{medicine}/{token}")
	public List<MedicalStoreDto> getNearsetMedicalStoresHavingMedicine(@PathVariable Long userId,
			@PathVariable Long distance, @PathVariable String medicine,
			@PathVariable String token) {
		return userService.getNearsetMedicalStoresHavingMedicine(userId, distance, medicine, token);
	}

	@GetMapping("/getNearestStores/{userId}/{token}")
	public List<MedicalStoreDto> getNearestMedicalStores(@PathVariable Long userId,
			@PathVariable String token) {
		return userService.getNearestMedicalStores(userId, token);

	}

	@GetMapping("/getStoresWithListMedicines/{token}")
	public List<MedicalStoreDto> getMedicalStoresHavingListMedicines(@RequestBody MedicinesDto medicinesDto,
			@PathVariable String token) {
		return userService.getMedicalStoreswithListMedicines(medicinesDto, token);
	}

}
