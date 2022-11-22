package com.github.mdeluise.everymoney.authorization.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;


    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }


    public Permission getOrCreate(Permission permission) {
        Optional<Permission> alreadySavedPermission =
            permissionRepository.findByTypeAndResourceClassNameAndResourceId(
                permission.getType(), permission.getResourceClass(), permission.getResourceId()
            );
        return alreadySavedPermission.orElse(permissionRepository.save(permission));
    }

}
