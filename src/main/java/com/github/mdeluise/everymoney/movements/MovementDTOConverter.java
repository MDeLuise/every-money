package com.github.mdeluise.everymoney.movements;

import com.github.mdeluise.everymoney.common.AbstractDTOConverter;
import com.github.mdeluise.everymoney.movements.income.Income;
import com.github.mdeluise.everymoney.movements.income.IncomeDTO;
import com.github.mdeluise.everymoney.movements.income.IncomeDTOConverter;
import com.github.mdeluise.everymoney.movements.outcome.Outcome;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTO;
import com.github.mdeluise.everymoney.movements.outcome.OutcomeDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovementDTOConverter extends AbstractDTOConverter<AbstractMovement, MovementDTO> {
    private final IncomeDTOConverter incomeDtoConverter;
    private final OutcomeDTOConverter outcomeDtoConverter;


    @Autowired
    public MovementDTOConverter(IncomeDTOConverter incomeDtoConverter, OutcomeDTOConverter outcomeDtoConverter) {
        this.incomeDtoConverter = incomeDtoConverter;
        this.outcomeDtoConverter = outcomeDtoConverter;
    }


    @Override
    public AbstractMovement convertFromDTO(MovementDTO dto) {
        if (dto instanceof IncomeDTO i) {
            return incomeDtoConverter.convertFromDTO(i);
        }
        return outcomeDtoConverter.convertFromDTO((OutcomeDTO) dto);
    }


    @Override
    public MovementDTO convertToDTO(AbstractMovement data) {
        MovementDTO result;
        if (data instanceof Income i) {
            result = incomeDtoConverter.convertToDTO(i);
            result.setType(MovementDTO.MovementDTOType.INCOME);
            return result;
        }
        result = outcomeDtoConverter.convertToDTO((Outcome) data);
        result.setType(MovementDTO.MovementDTOType.OUTCOME);
        return result;
    }
}
