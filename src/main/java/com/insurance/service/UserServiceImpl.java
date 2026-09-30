package com.insurance.service;

import com.insurance.exception.NotFoundException;
import com.insurance.model.User;
import com.insurance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Class getEntityServiceClass() {
        return User.class;
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return users;
    }

    @Override
    public User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    @Override
    public User add(final User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(final User user) {
        final User userDB = findById(user.getId());
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        userDB.setAddress(user.getAddress());
        userDB.setPostCode(user.getPostCode());
        userDB.setCity(user.getCity());
        return userRepository.save(userDB);
    }

    @Override
    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void softDeleteById(final Long id) {
        final User user = findById(id);
        user.setDeletedDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
