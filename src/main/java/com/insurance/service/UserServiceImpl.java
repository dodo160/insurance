package com.insurance.service;

import com.insurance.model.User;
import com.insurance.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> findAll() {
        final Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User add(final User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(final User user) throws ValidationException {
        final User userDB = userRepository.findById(user.getId()).orElse(null);
        if (Objects.isNull(userDB)) {
            throw new ValidationException("User entity doesn't exist");
        }
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
        final User user = userRepository.findById(id).orElse(null);
        if(Objects.nonNull(user)) {
            user.setDeletedDate(LocalDateTime.now());
            userRepository.save(user);
        }
    }
}
