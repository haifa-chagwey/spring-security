package com.haifachagwey.springsecurity.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.haifachagwey.springsecurity.config.Role.STUDENT;
import static com.haifachagwey.springsecurity.config.Role.*;

@Repository
public class UserDAOImpl implements UserDAO {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDAOImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return this.getUsers()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    private List<User> getUsers() {
        List<User> users = Lists.newArrayList(
                new User(
                        "anna",
                        passwordEncoder.encode("password"),
                        STUDENT.getRoleGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new User(
                        "linda",
                        passwordEncoder.encode("password"),
                        ADMIN.getRoleGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new User(
                        "tom",
                        passwordEncoder.encode("password"),
                        ADMIN_TRAINEE.getRoleGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
        return users;
    }
}
