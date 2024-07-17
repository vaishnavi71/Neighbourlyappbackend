package com.blogapp;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blogapp.config.AppConstants;
import com.blogapp.entity.Role;
import com.blogapp.repository.RoleRepository;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner, WebMvcConfigurer {

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*") // Add this line to allow all
																					// headers
				.allowCredentials(true); // Add this line to allow credentials (cookies)
	}

	public static void main(final String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
		System.out.println("Application Started...");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(final String... args) throws Exception {
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r -> {
//				System.out.println(r.getName());
			});
		} catch (Exception e) {

		}

	}

}