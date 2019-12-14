package com.tbb.demo.domain;


import com.tbb.demo.dao.UsersRepository;
import com.tbb.demo.exception.NonexisitngEntityException;
import com.tbb.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return usersRepository.findById(userId).orElseThrow(()->new NonexisitngEntityException("User with id: "+userId+" does not exist"));
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(()->new NonexisitngEntityException("User with username: "+username+" does not exist"));
    }

    @Override
    public User add(User user) {
        if(user.getRoles() == null || user.getRoles().trim().length()==0) {
            user.setRoles("ROLE_BLOGGER");
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        return usersRepository.insert(user);
    }

    @Override
    public User update(User user) {
        Optional<User> old = usersRepository.findById(user.getId());
        if (!old.isPresent()) {
            throw new NonexisitngEntityException(
                    String.format("Post with ID='%s' does not exist.", user.getId()));
        }
        return usersRepository.save(user);
    }

    @Override
    public User remove(String userId) {
        return null;
    }

    @Override
    public long count() {
        return usersRepository.count();
    }


}
