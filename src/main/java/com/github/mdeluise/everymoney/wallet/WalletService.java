package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.authentication.User;
import com.github.mdeluise.everymoney.authentication.UserService;
import com.github.mdeluise.everymoney.authorization.permission.PType;
import com.github.mdeluise.everymoney.authorization.permission.Permission;
import com.github.mdeluise.everymoney.authorization.permission.PermissionService;
import com.github.mdeluise.everymoney.common.AbstractCrudService;
import com.github.mdeluise.everymoney.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Collection;

@Service
public class WalletService extends AbstractCrudService<Wallet, Long> {
    private final UserService userService;
    private final PermissionService permissionService;


    @Autowired
    public WalletService(WalletRepository repository, UserService userService,
                         PermissionService permissionService) {
        super(repository);
        this.userService = userService;
        this.permissionService = permissionService;
    }


    @Override
    @PostFilter("hasRole('ADMIN') or hasAuthority('read:wallet:' + filterObject.id)")
    public Collection<Wallet> getAll() {
        return ((WalletRepository) repository).findAll();
    }


    @Override
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('read:wallet:' + #id)")
    public Wallet get(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('write:wallet:' + #id)")
    public void remove(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }
        repository.deleteById(id);
    }


    @Override
    public Wallet save(Wallet entityToSave) {
        Wallet saved = repository.save(entityToSave);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        User owner = userService.get(username);

        Permission readWalletPermission = permissionService.getOrCreate(
            new Permission(
                PType.READ, Wallet.class.getSimpleName().toLowerCase(), saved.getId().toString())
        );
        Permission writeWalletPermission = permissionService.getOrCreate(
            new Permission(
                PType.WRITE, Wallet.class.getSimpleName().toLowerCase(), saved.getId().toString())
        );

        owner.getPermissions().add(readWalletPermission);
        owner.getPermissions().add(writeWalletPermission);
        userService.update(owner.getId(), owner);
        return saved;
    }


    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('write:wallet:' + #id)")
    public Wallet update(Long id, Wallet updatedEntity) {
        Wallet toUpdate =
            repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        toUpdate.setName(updatedEntity.getName());
        toUpdate.setDescription(updatedEntity.getDescription());
        return repository.save(toUpdate);
    }
}
