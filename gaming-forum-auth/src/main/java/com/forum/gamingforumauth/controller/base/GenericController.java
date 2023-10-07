package com.forum.gamingforumauth.controller.base;


import com.forum.gamingforumauth.service.GenericService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class GenericController<DTO> {

    @Autowired
    protected GenericService<DTO> service;

    @GetMapping
    public ResponseEntity<List<DTO>> findAll(){
        List<DTO> entities = service.findAll();
        return new ResponseEntity<List<DTO>>(entities,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> findById(@PathVariable Long id){
        DTO entity = service.findById(id);
        return new ResponseEntity<DTO>(entity,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DTO entity, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(errorMessages(result.getFieldErrors()),HttpStatus.BAD_REQUEST);
        }
        DTO savedEntity = service.save(entity);
        return new ResponseEntity<DTO>(savedEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable Long id, @RequestBody DTO entity) {
        DTO updatedEntity = service.update(id,entity);
        return new ResponseEntity<DTO>(updatedEntity,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>("Successfully Deleted!",HttpStatus.OK);
    }

    private List<String> errorMessages(List<FieldError> errors){
        List<String> errorMessages = new ArrayList<>();
        for(FieldError error:errors){
            errorMessages.add(error.getDefaultMessage());
        }
        return errorMessages;
    }

}
