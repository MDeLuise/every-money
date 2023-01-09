package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
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
public class WalletController implements AbstractCrudController<WalletDTO, Long> {
    private final WalletService service;
    private final WalletDTOConverter walletDtoConverter;


    @Autowired
    public WalletController(WalletService service, WalletDTOConverter walletDtoConverter) {
        this.service = service;
        this.walletDtoConverter = walletDtoConverter;
    }


    @Override
    public ResponseEntity<Collection<WalletDTO>> findAll() {
        Set<WalletDTO> result =
            service.getAll().stream()
                   .map(walletDtoConverter::convertToDTO)
                   .collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<WalletDTO> find(Long id) {
        WalletDTO result = walletDtoConverter.convertToDTO(service.get(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<WalletDTO> update(WalletDTO updatedEntity, Long id) {
        WalletDTO result =
            walletDtoConverter.convertToDTO(service.update(id, walletDtoConverter.convertFromDTO(updatedEntity)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        service.remove(id);
    }


    @Override
    public ResponseEntity<WalletDTO> save(WalletDTO entityToSave) {
        WalletDTO result =
            walletDtoConverter.convertToDTO(service.save(walletDtoConverter.convertFromDTO(entityToSave)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}