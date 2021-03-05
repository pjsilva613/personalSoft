package com.buildings.msservicebuildings.controller;

import com.buildings.msservicebuildings.entity.ConstruccionMaterial;
import com.buildings.msservicebuildings.service.IConstruccionesxMaterialesService;
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
@RequestMapping(value = "/construccionesxmateriales")
public class ConstruccionxMaterialesController {


    @Autowired
    private IConstruccionesxMaterialesService iConstruccionesxMaterialesService;
    private Logger log= LoggerFactory.getLogger(ConstruccionxMaterialesController.class);

    @GetMapping
    public ResponseEntity<List<ConstruccionMaterial>> getAllConstruccionMaterial(){
        List<ConstruccionMaterial> ConstruccionMaterialList = iConstruccionesxMaterialesService.listAllConstruccionMaterial();
        if (ConstruccionMaterialList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ConstruccionMaterialList);
    }

    @GetMapping(value = "/{idConstruccion}")
    public ResponseEntity<List<ConstruccionMaterial>> findByIdConstrucion(@PathVariable("idConstruccion") Long idConstruccion){
        List<ConstruccionMaterial> ConstruccionMaterialList = iConstruccionesxMaterialesService.findByIdConstruccion(idConstruccion);
        if (ConstruccionMaterialList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ConstruccionMaterialList);
    }

    @PostMapping
    public ResponseEntity<List<ConstruccionMaterial>> createConstruccionMaterial(@RequestBody List<ConstruccionMaterial> postConstruccionMaterialList){
        List<ConstruccionMaterial> ConstruccionMaterialListDb = iConstruccionesxMaterialesService.createConstruccionMaterial(postConstruccionMaterialList);
        if (ConstruccionMaterialListDb == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ConstruccionMaterialListDb);
    }

    @PutMapping
    public ResponseEntity<List<ConstruccionMaterial>> updateConstruccionMaterial(@RequestBody List<ConstruccionMaterial> putConstruccionMaterialList){
        List<ConstruccionMaterial> construccionMaterialListDb = iConstruccionesxMaterialesService.updateConstruccionMaterial(putConstruccionMaterialList);
        if (construccionMaterialListDb == null){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(construccionMaterialListDb);
    }

    @DeleteMapping
    public ResponseEntity<List<ConstruccionMaterial>> deleteConstruccionMaterial(@RequestBody List<ConstruccionMaterial> deleteConstruccionMaterialList){
        List<ConstruccionMaterial> construccionMaterialListDb = iConstruccionesxMaterialesService.deleteConstruccionMaterial(deleteConstruccionMaterialList);
        if (construccionMaterialListDb == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(construccionMaterialListDb);
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
