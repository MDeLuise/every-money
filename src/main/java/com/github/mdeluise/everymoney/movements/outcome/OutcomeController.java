package com.github.mdeluise.everymoney.movements.outcome;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/outcome")
@Tag(name = "Outcome", description = "Endpoints for CRUD operations on outcomes")
public class OutcomeController implements AbstractCrudController<OutcomeDTO, Long> {
    private final OutcomeService outcomeService;
    private final OutcomeDTOConverter outcomeDTOConverter;


    @Autowired
    public OutcomeController(OutcomeService outcomeService, OutcomeDTOConverter outcomeDTOConverter) {
        this.outcomeService = outcomeService;
        this.outcomeDTOConverter = outcomeDTOConverter;
    }


    @Override
    @Operation(
        summary = "Get all the Outcome",
        description = "Get all the Outcome."
    )
    public ResponseEntity<Collection<OutcomeDTO>> findAll() {
        Collection<OutcomeDTO> result =
            outcomeService.getAll().stream().map(outcomeDTOConverter::convertToDTO).collect(Collectors.toSet());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Get a single Outcome",
        description = "Get the details of a given Outcome, according to the `id` parameter."
    )
    public ResponseEntity<OutcomeDTO> find(
        @Parameter(description = "The ID of the Outcome on which to perform the operation") Long id) {
        return new ResponseEntity<>(outcomeDTOConverter.convertToDTO(outcomeService.get(id)), HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Update a single Outcome",
        description = "Update the details of a given Outcome, according to the `id` parameter." +
                          "Please note that some fields may be readonly for integrity purposes."
    )
    public ResponseEntity<OutcomeDTO> update(OutcomeDTO updatedEntity, @Parameter(
        description = "The ID of the Outcome on which to perform the operation") Long id) {
        Outcome toReturn = outcomeService.update(id, outcomeDTOConverter.convertFromDTO(updatedEntity));
        return new ResponseEntity<>(outcomeDTOConverter.convertToDTO(toReturn), HttpStatus.OK);
    }


    @Override
    @Operation(
        summary = "Delete a single Outcome",
        description = "Delete the given Outcome, according to the `id` parameter."
    )
    public void remove(@Parameter(description = "The ID of the Outcome on which to perform the operation") Long id) {
        outcomeService.remove(id);
    }


    @Override
    @Operation(
        summary = "Create a new Outcome",
        description = "Create a new Outcome."
    )
    public ResponseEntity<OutcomeDTO> save(OutcomeDTO entityToSave) {
        Outcome toReturn = outcomeService.save(outcomeDTOConverter.convertFromDTO(entityToSave));
        return new ResponseEntity<>(outcomeDTOConverter.convertToDTO(toReturn), HttpStatus.OK);
    }
}
