package com.insurance.user.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.user.model.User;
import com.insurance.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(final Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User add(final User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(final Long id, final User user) {
        final User userDB = findById(id);
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        userDB.setAddress(user.getAddress());
        userDB.setPostCode(user.getPostCode());
        userDB.setCity(user.getCity());
        return userDB;
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void softDeleteById(final Long id) {
        final User user = findById(id);
        user.setDeletedDate(LocalDateTime.now());
    }
}
