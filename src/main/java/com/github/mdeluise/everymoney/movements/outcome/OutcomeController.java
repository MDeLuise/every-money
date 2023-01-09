package com.github.mdeluise.everymoney.movements.outcome;

import com.github.mdeluise.everymoney.common.AbstractCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/outcome")
public class OutcomeController implements AbstractCrudController<OutcomeDTO, Long> {
    private final OutcomeService outcomeService;
    private final OutcomeDTOConverter outcomeDTOConverter;


    @Autowired
    public OutcomeController(OutcomeService outcomeService, OutcomeDTOConverter outcomeDTOConverter) {
        this.outcomeService = outcomeService;
        this.outcomeDTOConverter = outcomeDTOConverter;
    }


    @Override
    public ResponseEntity<Collection<OutcomeDTO>> findAll() {
        Collection<OutcomeDTO> result =
            outcomeService.getAll().stream().map(outcomeDTOConverter::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<OutcomeDTO> find(Long id) {
        return new ResponseEntity<>(outcomeDTOConverter.convertToDTO(outcomeService.get(id)), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<OutcomeDTO> update(OutcomeDTO updatedEntity, Long id) {
        Outcome toReturn = outcomeService.update(id, outcomeDTOConverter.convertFromDTO(updatedEntity));
        return new ResponseEntity<>(outcomeDTOConverter.convertToDTO(toReturn), HttpStatus.OK);
    }


    @Override
    public void remove(Long id) {
        outcomeService.remove(id);
    }


    @Override
    public ResponseEntity<OutcomeDTO> save(OutcomeDTO entityToSave) {
        Outcome toReturn = outcomeService.save(outcomeDTOConverter.convertFromDTO(entityToSave));
        return new ResponseEntity<>(outcomeDTOConverter.convertToDTO(toReturn), HttpStatus.OK);
    }
}
