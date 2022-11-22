package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/wallet")
public class WalletController implements AbstractCrudController<Wallet, Long> {
    private final WalletService service;


    @Autowired
    public WalletController(WalletService service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<Collection<Wallet>> findAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Wallet> find(Long id) {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Wallet> update(Wallet updatedEntity, Long id) {
        return new ResponseEntity<>(service.update(id, updatedEntity), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        service.remove(id);
    }


    @Override
    public ResponseEntity<Wallet> save(Wallet entityToSave) {
        return new ResponseEntity<>(service.save(entityToSave), HttpStatus.OK);
    }
}