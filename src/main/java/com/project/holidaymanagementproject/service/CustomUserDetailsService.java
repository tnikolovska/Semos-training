package com.project.holidaymanagementproject.service;

import com.project.holidaymanagementproject.model.Person;
import com.project.holidaymanagementproject.model.Role;
import com.project.holidaymanagementproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Vasilija Uzunova
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("Username and Password is not valid");
        }
        Person user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username and Password is not valid");
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            list.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(username, user.getGeneratedPassword(), list) {};
    }

}

