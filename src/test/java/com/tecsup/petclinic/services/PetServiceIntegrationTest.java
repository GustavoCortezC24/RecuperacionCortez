package com.tecsup.petclinic.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.services.PetService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PetServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetService petService;

    @Test
    public void testCreatePet() throws Exception {
        Pet pet = new Pet("Buddy", 1, 1);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isCreated());


        Pet createdPet = petService.findByName("Buddy").get(0);
        assertThat(createdPet).isNotNull();
        assertThat(createdPet.getName()).isEqualTo("Buddy");
    }

    @Test
    public void testFindPetById() throws Exception {
        Pet pet = new Pet("Charlie", 1, 1);
        Pet savedPet = petService.create(pet);

        mockMvc.perform(get("/api/pets/" + savedPet.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        Pet foundPet = petService.findById(savedPet.getId());
        assertThat(foundPet).isNotNull();
        assertThat(foundPet.getName()).isEqualTo("Charlie");
    }
}
