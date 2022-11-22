package com.github.mdeluise.everymoney.wallet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    @Override
    Collection<Wallet> findAll();
}
