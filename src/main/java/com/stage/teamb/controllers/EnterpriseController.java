package com.stage.teamb.controllers;//package com.stage.teamb.controllers;
//
//import com.stage.teamb.dtos.DepartmentDTO;
//import com.stage.teamb.dtos.EnterpriseDTO;
//import com.stage.teamb.services.DepartmentService;
//import com.stage.teamb.services.EntrepriseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/entreprise")
//public class EntrepriseController {
//
//    private final EntrepriseService entrepriseService;
//    private final DepartmentService departmentService;
//
//    @Autowired
//    public EntrepriseController(EntrepriseService entrepriseService, DepartmentService departmentService) {
//        this.entrepriseService = entrepriseService;
//        this.departmentService = departmentService;
//    }
//
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<EnterpriseDTO> findOne(@PathVariable Long id) {
//        EnterpriseDTO entreprise = entrepriseService.findOne(id);
//        if (entreprise != null) {
//            return ResponseEntity.ok(entreprise);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @GetMapping()
//    public List<EnterpriseDTO> findAll() {
//        return entrepriseService.findAll();
//    }
//
//    @PostMapping
//    public ResponseEntity<EnterpriseDTO> saveOne(@RequestBody EnterpriseDTO entreprise) {
//        EnterpriseDTO savedEntreprise = entrepriseService.saveOne(entreprise);
//        return ResponseEntity.ok(savedEntreprise);
//    }
//
////    @PostMapping("/addDepartementToEntreprise")
////    public ResponseEntity<String> addDepartementToEntreprise(@RequestBody EntrepriseWithDepDTO entrepriseWithDepDto) {
////        List<DepartmentDTO> departement = departementService.findDepartements(entrepriseWithDepDto.getDepartementsIds());
////
////        if (!departement.isEmpty()) {
////            Entreprise entreprise = entrepriseMapper.toEntrepriseWithDepsEntity(entrepriseWithDepDto);
////            entreprise.setDepartement(departement);
////            entrepriseService.save(entreprise);
////            return ResponseEntity.ok("Départements ajoutés à l'entreprise avec succès.");
////        } else {
////            return ResponseEntity.notFound().build();
////        }
////    }
//
//    @PutMapping("/update")
//    public ResponseEntity<EnterpriseDTO> update(@RequestBody EnterpriseDTO entreprise) {
//        EnterpriseDTO updatedEntreprise = entrepriseService.update(entreprise);
//        return ResponseEntity.ok(updatedEntreprise);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteOne(@PathVariable Long id) {
//        entrepriseService.deleteOne(id);
//        return ResponseEntity.ok("Entreprise supprimée avec succès.");
//    }
//
//    @GetMapping("/getEntreprise/{id}")
//    public ResponseEntity<EntrepriseWithDepsDTO> getEntreprise(@PathVariable Long id) {
//        EnterpriseDTO entreprise = entrepriseService.findOne(id);
//        EntrepriseWithDepsDTO entrepriseWithDepsDTO = new EntrepriseWithDepsDTO();
//         entrepriseWithDepsDTO.setId(entreprise.getId());
//         entrepriseWithDepsDTO.setNomEnreprise(entreprise.getNomEntreprise());
//         entrepriseWithDepsDTO.setLocalEnreprise(entreprise.getLocalEntreprise());
//
//        if (entreprise != null) {
//          List<DepartmentDTO> departementList = departementService.findDepByEnrepId(id);
//           entrepriseWithDepsDTO.setDepartementDTOList(departementList);
//            return ResponseEntity.ok(entrepriseWithDepsDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//}
//
//

import com.stage.teamb.dtos.EnterpriseDTO;
import com.stage.teamb.services.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
@Slf4j
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @Autowired
    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllEnterprises() {
        try {
            List<EnterpriseDTO> enterpriseDTOList = enterpriseService.findAllEnterprises();
            return ResponseEntity.ok(enterpriseDTOList);
        } catch (RuntimeException exception) {
            log.error("Error retrieving enterprises: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving enterprises: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnterpriseById(@PathVariable Long id) {
        try {
            EnterpriseDTO enterpriseDTO = enterpriseService.findEnterpriseById(id);
            return ResponseEntity.ok(enterpriseDTO);
        } catch (RuntimeException exception) {
            log.error("Enterprise not found: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Enterprise not found with id: " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createEnterprise(@RequestBody EnterpriseDTO enterpriseDTO) {
        try {
            EnterpriseDTO createdEnterprise = enterpriseService.saveEnterprise(enterpriseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnterprise);
        } catch (RuntimeException exception) {
            log.error("Could not create enterprise: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create enterprise: " + exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnterprise(@PathVariable Long id, @RequestBody EnterpriseDTO enterpriseDTO) {
        try {
            enterpriseDTO.setId(id); // Ensure the ID matches the path variable
            EnterpriseDTO updatedEnterprise = enterpriseService.updateEnterprise(enterpriseDTO);
            return ResponseEntity.ok(updatedEnterprise);
        } catch (RuntimeException exception) {
            log.error("Could not update enterprise: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not update enterprise: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnterpriseById(@PathVariable Long id) {
        try {
            enterpriseService.deleteEnterpriseById(id);
            return ResponseEntity.ok("Enterprise deleted successfully");
        } catch (RuntimeException exception) {
            log.error("Could not delete enterprise: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not delete enterprise: " + exception.getMessage());
        }
    }


}
