package com.tecsup.petclinic.controllers;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.services.PetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        Pet createdPet = petService.create(pet);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable long id) {
        try {
            Pet pet = petService.findById(id);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (PetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getPetsByName(@RequestParam String name) {
        List<Pet> pets = petService.findByName(name);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = (List<Pet>) petService.findAll();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }
}
