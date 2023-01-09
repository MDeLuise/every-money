package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet", description = "Endpoints for CRUD operations on wallets.")
public class WalletController implements AbstractCrudController<WalletDTO, Long> {
    private final WalletService service;
    private final WalletDTOConverter walletDtoConverter;


    @Autowired
    public WalletController(WalletService service, WalletDTOConverter walletDtoConverter) {
        this.service = service;
        this.walletDtoConverter = walletDtoConverter;
    }


    @Override
    @Operation(
        summary = "Get all the Wallet",
        description = "Get all the Wallet."
    )
    public ResponseEntity<Collection<WalletDTO>> findAll() {
        Set<WalletDTO> result =
            service.getAll().stream()
                   .map(walletDtoConverter::convertToDTO)
                   .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Get a single Wallet",
        description = "Get the details of a given Wallet, according to the `id` parameter."
    )
    public ResponseEntity<WalletDTO> find(
        @Parameter(description = "The ID of the Wallet on which to perform the operation") Long id) {
        WalletDTO result = walletDtoConverter.convertToDTO(service.get(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Update a single Wallet",
        description = "Update the details of a given Wallet, according to the `id` parameter." +
                          "Please note that some fields may be readonly for integrity purposes."
    )
    public ResponseEntity<WalletDTO> update(WalletDTO updatedEntity, @Parameter(
        description = "The ID of the Wallet on which to perform the operation") Long id) {
        WalletDTO result =
            walletDtoConverter.convertToDTO(service.update(id, walletDtoConverter.convertFromDTO(updatedEntity)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Delete a single Wallet",
        description = "Delete the given Wallet, according to the `id` parameter."
    )
    public void remove(@Parameter(description = "The ID of the Wallet on which to perform the operation") Long id) {
        service.remove(id);
    }


    @Override
    @Operation(
        summary = "Create a new Wallet",
        description = "Create a new Wallet."
    )
    public ResponseEntity<WalletDTO> save(WalletDTO entityToSave) {
        WalletDTO result =
            walletDtoConverter.convertToDTO(service.save(walletDtoConverter.convertFromDTO(entityToSave)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}