package com.github.mdeluise.everymoney.user.service;

import com.github.mdeluise.everymoney.authentication.User;
import com.github.mdeluise.everymoney.authentication.UserRepository;
import com.github.mdeluise.everymoney.authentication.UserService;
import com.github.mdeluise.everymoney.authorization.role.ERole;
import com.github.mdeluise.everymoney.authorization.role.Role;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserService userService;


    @Test
    void whenSaveUser_thenSaveUser() {
        String username = "username";
        String password = "password";
        User toSave = new User();
        toSave.setUsername(username);
        toSave.setPassword(password);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(toSave);
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded");

        Assertions.assertThat(userService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenFindUser_thenFindUser() {
        String username = "username";
        String password = "password";
        User toSave = new User();
        toSave.setUsername(username);
        toSave.setPassword(password);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(toSave));
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded");

        Assertions.assertThat(userService.get(username)).isSameAs(toSave);
    }


    @Test
    void whenFindNonExistingUser_thenError() {
        String username = "username";
        Mockito.when(userRepository.findByUsername(username)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThatThrownBy(() -> userService.get(username)).isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenSaveUser_thenAlsoPermissionsSaved() {
        String username = "username";
        String password = "password";
        User toSave = new User();
        toSave.setUsername(username);
        toSave.setPassword(password);
        toSave.setRoles(Set.of(new Role(ERole.ROLE_ADMIN)));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(toSave);
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded");

        Assertions.assertThat(userService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenUpdateUser_thenUpdateUser() {
        String username = "username";
        String password = "password";
        User updated = new User();
        updated.setUsername(username);
        updated.setPassword(password);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(updated);
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(updated));
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded");

        Assertions.assertThat(userService.update(0L, updated)).isSameAs(updated);
    }


    @Test
    void whenSaveNonExistingUser_thenError() {
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.update(0L, new User()))
                  .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenSaveUser_thenUserPasswordEncrypted() {
        String username = "username";
        String password = "password";
        User toSave = new User();
        toSave.setUsername(username);
        toSave.setPassword(password);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(toSave);
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded");

        userService.save(toSave);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(password);
    }


    @Test
    void whenSaveUserWithUsernameAndPassword_thenUserPasswordEncrypted() {
        String username = "username";
        String password = "password";
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(new User());
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded");

        userService.save(username, password);
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(password);
    }


    @Test
    void whenDeleteUser_thenDeleteUser() {
        Mockito.when(userRepository.existsById(0L)).thenReturn(true);
        userService.remove(0L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(0L);
    }


    @Test
    void whenDeleteNonExistingUser_thenError() {
        Mockito.when(userRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> userService.remove(0L)).isInstanceOf(EntityNotFoundException.class);
    }
}
