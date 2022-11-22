package com.github.mdeluise.everymoney.authentication;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController implements AbstractCrudController<User, Long> {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    public ResponseEntity<Collection<User>> findAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<User> find(Long id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<User> update(User updatedEntity, Long id) {
        return new ResponseEntity<>(userService.update(id, updatedEntity), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        userService.remove(id);
    }


    @Override
    public ResponseEntity<User> save(User entityToSave) {
        return new ResponseEntity<>(userService.save(entityToSave), HttpStatus.OK);
    }
}
