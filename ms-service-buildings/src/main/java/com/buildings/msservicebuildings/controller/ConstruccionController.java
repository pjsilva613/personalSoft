package com.buildings.msservicebuildings.controller;

import com.buildings.msservicebuildings.entity.Construccion;
import com.buildings.msservicebuildings.entity.Material;
import com.buildings.msservicebuildings.service.IConstruccionesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/construcciones")
public class ConstruccionController {

    @Autowired
    private IConstruccionesService iConstruccionesService;
    Logger log= LoggerFactory.getLogger(ConstruccionController.class);

    @GetMapping
    public ResponseEntity<List<Construccion>> getAllConstruccion(){
        List<Construccion> ConstruccionList = iConstruccionesService.listAllConstruccion();
        if (ConstruccionList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ConstruccionList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Construccion> getConstruccion(@PathVariable("id") Long id){
        Construccion construccion= iConstruccionesService.getConstruccion(id);
        if ( null == construccion ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(construccion);
    }

    @PostMapping
    public ResponseEntity<Construccion> createConstruccion(@Valid @RequestBody Construccion construccion, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        Construccion construccionDb = iConstruccionesService.createConstruccion(construccion);
        return ResponseEntity.status(HttpStatus.CREATED).body(construccionDb);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Construccion> updateConstruccion(@PathVariable("id") Long id, @RequestBody Construccion construccion){
        construccion.setId(id);
        Construccion construccionDb = iConstruccionesService.updateConstruccion(construccion);
        if (construccionDb == null){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(construccionDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Construccion> deleteConstruccion(@PathVariable("id") Long id){
        Construccion construccionDb = iConstruccionesService.deleteConstruccion(id);
        if (construccionDb == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(construccionDb);
    }

    @GetMapping(value = "/{id}/cantidadDias")
    public ResponseEntity<Construccion> updateCantidadDiasMaterial(@PathVariable("id") Long id, @RequestParam(name = "cantidadDias", required = true) Double cantidadDias){
        Construccion construccionDb = iConstruccionesService.updateCantidadDiasConstruccion(id, cantidadDias);
        if (construccionDb == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(construccionDb);
    }

    private String formatMessage(BindingResult bindingResult){

        List<Map<String, String>> errors= bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> {Map<String, String> error = new HashMap<>();
                    log.info("Imprime error:    "+fieldError.getField()+" "+fieldError.getDefaultMessage());
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;})
                .collect(Collectors.toList());

        ErrorMessage errorMessage= ErrorMessage.builder().code("01").Message(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString="";

        try {
            jsonString= objectMapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(jsonString);
        return jsonString;
    }
}
