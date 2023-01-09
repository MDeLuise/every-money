package com.github.mdeluise.everymoney.wallet;

import com.github.mdeluise.everymoney.common.AbstractDTOConverter;
import com.github.mdeluise.everymoney.movements.MovementDTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WalletDTOConverter extends AbstractDTOConverter<Wallet, WalletDTO> {
    private final ModelMapper modelMapper;
    private final MovementDTOConverter movementDTOConverter;


    @Autowired
    public WalletDTOConverter(ModelMapper modelMapper, MovementDTOConverter movementDTOConverter) {
        this.modelMapper = modelMapper;
        this.movementDTOConverter = movementDTOConverter;
    }


    @Override
    public Wallet convertFromDTO(WalletDTO walletDTO) {
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        wallet.setMovements(
            walletDTO.getMovements().stream()
                     .map(movementDTOConverter::convertFromDTO)
                     .collect(Collectors.toSet())
        );
        return wallet;
    }


    @Override
    public WalletDTO convertToDTO(Wallet wallet) {
        WalletDTO result = modelMapper.map(wallet, WalletDTO.class);
        result.setMovements(wallet.getMovements().stream()
                                  .map(movementDTOConverter::convertToDTO)
                                  .collect(Collectors.toSet())
        );
        return result;
    }
}
