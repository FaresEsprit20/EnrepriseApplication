//package com.stage.teamb.Address.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stage.teamb.controllers.ResponsibleController;
//import com.stage.teamb.dtos.ResponsableDTO;
//import com.stage.teamb.services.ResponsibleService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.is;
//
//@SpringBootTest
//public class ResponsibleControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private ResponsibleService responsibleService;
//
//    @InjectMocks
//    private ResponsibleController responsibleController;
//
//    private ObjectMapper objectMapper;
//
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(responsibleController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//
//
//    @Test
//    public void testFindAllResponsibles() throws Exception {
//        List<ResponsableDTO> responseDTOList = new ArrayList<>();
//        // Add some ResponsableDTO objects to responseDTOList
//
//        when(responsibleService.findAllResponsibles()).thenReturn(responseDTOList);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/responsibles"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(responseDTOList.size())));
//
//        verify(responsibleService, times(1)).findAllResponsibles();
//        verifyNoMoreInteractions(responsibleService);
//    }
//
//    @Test
//    public void testFindResponsibleById() throws Exception {
//        Long responsibleId = 1L;
//        ResponsableDTO responseDTO = new ResponsableDTO();
//        // Set responseDTO data
//
//        when(responsibleService.findResponsibleById(responsibleId)).thenReturn(responseDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/responsibles/{id}", responsibleId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(responseDTO.getId()));
//
//        verify(responsibleService, times(1)).findResponsibleById(responsibleId);
//        verifyNoMoreInteractions(responsibleService);
//    }
//
//    @Test
//    public void testFindResponsibleById_ResourceNotFound() throws Exception {
//        Long responsibleId = 1L;
//
//        when(responsibleService.findResponsibleById(responsibleId))
//                .thenThrow(new RuntimeException("Entity not found"));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/responsibles/{id}", responsibleId))
//                .andExpect(status().isNotFound());
//
//        verify(responsibleService, times(1)).findResponsibleById(responsibleId);
//        verifyNoMoreInteractions(responsibleService);
//    }
//
//    @Test
//    public void testSaveResponsible() throws Exception {
//        // Prepare fake data
//        ResponsableDTO responsableDTO = new ResponsableDTO();
//        responsableDTO.setMatricule(1234);
//        responsableDTO.setNom("John");
//        responsableDTO.setPrenom("Doe");
//
//        when(responsibleService.saveResponsible(any(ResponsableDTO.class))).thenReturn(responsableDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/responsables")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(responsableDTO))) // Serialize the DTO to JSON
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.matricule", is(1234)))
//                .andExpect(jsonPath("$.nom", is("John")))
//                .andExpect(jsonPath("$.prenom", is("Doe")));
//
//        verify(responsibleService, times(1)).saveResponsible(any(ResponsableDTO.class));
//        verifyNoMoreInteractions(responsibleService);
//    }
//
//
//    @Test
//    public void testUpdateResponsible() throws Exception {
//        // Prepare fake data
//        ResponsableDTO responsableDTO = new ResponsableDTO();
//        responsableDTO.setId(1L);
//        responsableDTO.setMatricule(Integer.parseInt("12345"));
//        responsableDTO.setNom("John");
//        responsableDTO.setPrenom("Doe");
//
//        when(responsibleService.updateResponsible(any(ResponsableDTO.class))).thenReturn(responsableDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/responsables/update")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(responsableDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.matricule", is("12345")))
//                .andExpect(jsonPath("$.nom", is("John")))
//                .andExpect(jsonPath("$.prenom", is("Doe")));
//
//        verify(responsibleService, times(1)).updateResponsible(any(ResponsableDTO.class));
//        verifyNoMoreInteractions(responsibleService);
//    }
//
//
//    @Test
//    public void testDeleteResponsibleById() throws Exception {
//        Long responsibleId = 1L;
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/responsibles/{id}", responsibleId))
//                .andExpect(status().isOk());
//
//        verify(responsibleService, times(1)).deleteResponsibleById(responsibleId);
//        verifyNoMoreInteractions(responsibleService);
//    }
//
//    private static String asJsonString(Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//}
