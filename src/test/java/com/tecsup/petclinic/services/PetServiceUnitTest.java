package com.tecsup.petclinic.services;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.repositories.PetRepository;

@ExtendWith(MockitoExtension.class)
public class PetServiceUnitTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
    }

    @Test
    void testUpdatePet() {
        Pet updatedPet = new Pet();
        updatedPet.setId(1L);
        updatedPet.setName("Max");

        when(petRepository.save(any(Pet.class))).thenReturn(updatedPet);

        Pet saved = petService.update(updatedPet);

        assertEquals("Max", saved.getName());
        verify(petRepository).save(updatedPet);
    }

    @Test
    void testFindByIdThrowsException() {
        when(petRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> {
            petService.findById(99L);
        });
    }
}