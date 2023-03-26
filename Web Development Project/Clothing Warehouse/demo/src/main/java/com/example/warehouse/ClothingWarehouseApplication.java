package com.example.warehouse;

import com.example.warehouse.model.FashionBrand;
import com.example.warehouse.model.Item;
import com.example.warehouse.model.Role;
import com.example.warehouse.model.User;
import com.example.warehouse.repository.ItemRepository;
import com.example.warehouse.repository.RoleRepository;
import com.example.warehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class ClothingWarehouseApplication {
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ClothingWarehouseApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, ItemRepository itemRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			itemRepository.saveAll(Arrays.asList(
					new Item(null, "T-Shirt", FashionBrand.BALENCIAGA, 2022, 1200),
					new Item(null, "Jacket", FashionBrand.STONE_ISLAND, 2022, 2500),
					new Item(null, "Shoes", FashionBrand.DIOR, 2022, 1800)
			));

			// Create roles
			Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));
			Role warehouseEmployeeRole = roleRepository.save(new Role(null, "ROLE_WAREHOUSE_EMPLOYEE"));
			Role userRole = roleRepository.save(new Role(null, "ROLE_USER"));

			// Create users
			User admin = new User(null, "admin", passwordEncoder.encode("admin"), new HashSet<>(Arrays.asList(adminRole)));
			User warehouseEmployee = new User(null, "employee", passwordEncoder.encode("employee"), new HashSet<>(Arrays.asList(warehouseEmployeeRole)));
			User regularUser = new User(null, "user", passwordEncoder.encode("user"), new HashSet<>(Arrays.asList(userRole)));

			userRepository.saveAll(Arrays.asList(admin, warehouseEmployee, regularUser));
		};
	}
}