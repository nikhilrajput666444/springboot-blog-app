package com.blogapp.helper;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blogapp.entities.MyUser;
import com.blogapp.entities.Role;
import com.blogapp.repo.RoleRepo;
import com.blogapp.repo.UserRepo;

@Component
public class DataInitlizerRunner implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        // Create roles
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("GUEST");

        // Create admin users
        createAdminIfNotExists("admin@gmail.com", "admin1234", "Main", "Admin");
        createAdminIfNotExists("admin2@gmail.com", "admin5678", "Second", "Admin");
        createAdminIfNotExists("admin3@gmail.com", "admin9999", "Third", "Admin");
    }

    // Method to create role
    private void createRoleIfNotExists(String roleName) {

        Role role = roleRepo.findByName(roleName);

        if (role == null) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepo.save(newRole);

            System.out.println(roleName + " role created");
        }
    }

    // Method to create admin
    private void createAdminIfNotExists(String email, String password, String firstName, String lastName) {

        MyUser existingUser = userRepo.findByEmail(email);

        if (existingUser == null) {

            MyUser adminUser = new MyUser();
            adminUser.setFirstName(firstName);
            adminUser.setLastName(lastName);
            adminUser.setEmail(email);

            String encodedPass = encoder.encode(password);
            adminUser.setPassword(encodedPass);

            Role adminRole = roleRepo.findByName("ADMIN");

            List<Role> roleList = Arrays.asList(adminRole);
            adminUser.setRoles(roleList);

            userRepo.save(adminUser);

            System.out.println("Admin created: " + email);
        }
    }

}
