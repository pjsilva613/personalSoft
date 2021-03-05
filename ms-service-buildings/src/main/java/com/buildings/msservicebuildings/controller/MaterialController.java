package com.buildings.msservicebuildings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.buildings.msservicebuildings.entity.Material;
import com.buildings.msservicebuildings.service.IMaterialesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/materiales")
public class MaterialController {

	@Autowired
	private IMaterialesService iMaterialesService;
	
    Logger log= LoggerFactory.getLogger(MaterialController.class);

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterial(){
        List<Material> materialList = iMaterialesService.listAllMaterial();
        if (materialList.isEmpty()) {
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(materialList);
     }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable("id") Long id){
    	Material material= iMaterialesService.getMaterial(id);
        if ( null == material ) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(material);
    }

    @PostMapping
    public ResponseEntity<Material> createMaterial(@Valid @RequestBody Material material, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        Material materialDb = iMaterialesService.createMaterial(material);
        return ResponseEntity.status(HttpStatus.CREATED).body(materialDb);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Material> updateMaterial(@PathVariable("id") Long id, @RequestBody Material material){
    	material.setId(id);
    	Material materialDb = iMaterialesService.updateMaterial(material);
        if (materialDb == null){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(materialDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Material> deleteMaterial(@PathVariable("id") Long id){
    	Material materialDb = iMaterialesService.deleteMaterial(id);
        if (materialDb == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materialDb);
    }

    @GetMapping(value = "/{id}/cantidad")
    public ResponseEntity<Material> updateCantidadMaterial(@PathVariable("id") Long id, @RequestParam(name = "cantidad", required = true) Double cantidad){
    	Material materiaDb = iMaterialesService.updateCantidadMaterial(id, cantidad);
        if (materiaDb == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materiaDb);
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
