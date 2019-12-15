package com.tbb.demo.web;


import com.tbb.demo.domain.UsersService;
import com.tbb.demo.exception.InvalidEntityException;
import com.tbb.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {


    @Autowired
    private UsersService usersService;

//    @GetMapping
//    public List<User> getUsers() {
//        if (!isLoggedInUserAdmin()) {
//            throw new InvalidEntityException("Only admins can see other users data");
//        }
//        return usersService.findAll();
//    }
//
    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") String userId) {
        validatePermissions(userId);
        return usersService.findById(userId);
    }
//
//    @PostMapping
//    public ResponseEntity<User> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
//        if (!isLoggedInUserAdmin()) {
//            throw new InvalidEntityException("Only admins can create users");
//        }
//        if (bindingResult.hasFieldErrors()) {
//            String message = bindingResult.getFieldErrors().stream()
//                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
//                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
//                    .reduce("", (acc, errStr) -> acc + errStr);
//            throw new InvalidEntityException(message);
//        }
//        User created = usersService.add(user);
//        return ResponseEntity.created(
//                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}").build(created.getId()))
//                .body(created);
//    }
//
    @PutMapping("{id}")
    public User update(@PathVariable String id, @Valid @RequestBody User user) {
        validatePermissions(user.getId());
        return usersService.update(user);
    }
//
//
//    @DeleteMapping("{id}")
//    public User remove(@PathVariable String id) {
//
//        validatePermissions(id);
//        return usersService.remove(id);
//    }
//
    private void validatePermissions(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User searchedUser = usersService.findById(id);
        if (!authentication.getPrincipal().equals(searchedUser.getUsername())) {
            throw new InvalidEntityException("You do not delete other users data");
        }
    }
//
//    private boolean isLoggedInUserAdmin() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()).contains("ROLE_ADMIN");
//    }
@GetMapping
public String getMessage() {
    return "Hello from private API controller";
}
}

