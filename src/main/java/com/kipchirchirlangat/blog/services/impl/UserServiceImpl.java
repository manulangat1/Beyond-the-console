package com.kipchirchirlangat.blog.services.impl;


import com.kipchirchirlangat.blog.domain.entities.User;
import com.kipchirchirlangat.blog.repositories.UserRepository;
import com.kipchirchirlangat.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with the id does not find"));
    }
}
