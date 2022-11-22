package com.github.mdeluise.everymoney.wallet.service;

import com.github.mdeluise.everymoney.authentication.User;
import com.github.mdeluise.everymoney.authentication.UserService;
import com.github.mdeluise.everymoney.authorization.permission.PType;
import com.github.mdeluise.everymoney.authorization.permission.Permission;
import com.github.mdeluise.everymoney.authorization.permission.PermissionService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import com.github.mdeluise.everymoney.wallet.Wallet;
import com.github.mdeluise.everymoney.wallet.WalletRepository;
import com.github.mdeluise.everymoney.wallet.WalletService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;


@ExtendWith(SpringExtension.class)
@WithMockUser(username = "admin", roles = "ADMIN")
public class WalletServiceTest {
    @Mock
    WalletRepository walletRepository;
    @Mock
    UserService userService;
    @Mock
    PermissionService permissionService;
    @InjectMocks
    WalletService walletService;


    @Test
    void whenSaveWallet_thenReturnWallet() {
        Wallet toSave = new Wallet();
        toSave.setId(0L);
        toSave.setName("wallet0");
        toSave.setDescription("description0");
        Mockito.when(walletRepository.save(toSave)).thenReturn(toSave);

        Mockito.when(permissionService.getOrCreate(Mockito.any())).thenReturn(new Permission());

        User savedUser = new User();
        savedUser.setId(0);
        savedUser.setUsername("admin");
        Mockito.when(userService.get("admin")).thenReturn(savedUser);
        Mockito.when(userService.save(Mockito.any())).thenReturn(savedUser);

        Assertions.assertThat(walletService.save(toSave)).isSameAs(toSave);
    }


    @Test
    void whenGetWallet_thenReturnWallet() {
        Wallet toGet = new Wallet();
        toGet.setId(0L);
        toGet.setName("wallet0");
        toGet.setDescription("description0");
        Mockito.when(walletRepository.findById(0L)).thenReturn(Optional.of(toGet));

        Assertions.assertThat(walletService.get(0L)).isSameAs(toGet);
    }


    @Test
    void whenGetNonExistingWallet_thenError() {
        Mockito.when(walletRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> walletService.get(0L))
                  .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    void whenGetAllWallets_thenReturnAllWallets() {
        Wallet toGet1 = new Wallet();
        toGet1.setId(0L);
        toGet1.setName("wallet0");
        toGet1.setDescription("description0");
        Wallet toGet2 = new Wallet();
        toGet2.setId(1L);
        toGet2.setName("wallet1");
        toGet2.setDescription("description1");

        Set<Wallet> allWallets = Set.of(toGet1, toGet2);
        Mockito.when(walletRepository.findAll()).thenReturn(allWallets);

        Assertions.assertThat(walletService.getAll()).isSameAs(allWallets);
    }


    @Test
    void givenWallet_whenDeleteWallet_thenDeleteWallet() {
        Mockito.when(walletRepository.existsById(0L)).thenReturn(true);
        walletService.remove(0L);
        Mockito.verify(walletRepository, Mockito.times(1)).deleteById(0L);
    }


    @Test
    void whenDeleteNonExistingWallet_thenError() {
        Mockito.when(walletRepository.existsById(0L)).thenReturn(false);

        Assertions.assertThatThrownBy(() -> walletService.remove(0L)).isInstanceOf(
            EntityNotFoundException.class);
    }


    @Test
    void givenWallet_whenUpdateWallet_thenUpdateWallet() {
        Wallet updated = new Wallet();
        updated.setName("updated");
        Mockito.when(walletRepository.existsById(0L)).thenReturn(true);
        Mockito.when(walletRepository.findById(0L)).thenReturn(Optional.of(new Wallet()));
        Mockito.when(walletRepository.save(Mockito.any())).thenReturn(updated);

        Assertions.assertThat(walletService.update(0L, updated)).isSameAs(updated);
    }


    @Test
    void whenUpdateNonExistingWallet_thenError() {
        Mockito.when(walletRepository.existsById(0L)).thenReturn(false);
        Wallet updated = new Wallet();
        updated.setName("updated");

        Assertions.assertThatThrownBy(() -> walletService.update(0L, updated)).isInstanceOf(
            EntityNotFoundException.class);
    }


    @Test
    void whenSaveWallet_thenOwnerGetsPermissions() {
        Wallet toSave = new Wallet();
        toSave.setId(0L);
        toSave.setName("wallet0");
        toSave.setDescription("description0");
        Mockito.when(walletRepository.save(toSave)).thenReturn(toSave);

        Mockito.when(permissionService.getOrCreate(Mockito.any()))
               .thenReturn(
                   new Permission(PType.READ, "wallet", "0"))
               .thenReturn(
                   new Permission(PType.WRITE, "wallet", "0")
               );

        User savedUser = new User();
        savedUser.setId(0);
        savedUser.setUsername("admin");
        Mockito.when(userService.get("admin")).thenReturn(savedUser);
        Mockito.when(userService.save(Mockito.any())).thenReturn(savedUser);

        walletService.save(toSave);

        isSameAsSet(savedUser.getPermissions(), Set.of(
                        new Permission(PType.READ, "wallet", "0"),
                        new Permission(PType.WRITE, "wallet", "0")
                    )
        );
    }


    private void isSameAsSet(Set<Permission> expected, Set<Permission> actual) {
        if (expected == null || actual == null) {
            Assertions.fail("sets cannot be null");
        }
        if (expected.size() != actual.size()) {
            Assertions.fail(String.format("sets sizes not equal, expected %s and actual %s",
                                          expected.size(), actual.size()
            ));
        }
        for (Permission p : expected) {
            if (!actual.contains(p)) {
                Assertions.fail(String.format("sets are not equal, expected: %s, actual: %s",
                                              expected, actual
                ));
            }
        }
    }

}
