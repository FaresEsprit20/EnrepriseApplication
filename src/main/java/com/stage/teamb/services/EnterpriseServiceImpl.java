package com.stage.teamb.services;


import com.stage.teamb.dtos.EnterpriseDTO;
import com.stage.teamb.mappers.EnterpriseMapper;
import com.stage.teamb.models.Enterprise;
import com.stage.teamb.repository.EnterpriseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;

    @Autowired
    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
    }


    @Override
    public List<EnterpriseDTO> findAllEnterprises() {
        return EnterpriseMapper.toListDTO(enterpriseRepository.findAll());
    }

    @Override
    public EnterpriseDTO findEnterpriseById(Long id) {
        return EnterpriseMapper.toDTO(enterpriseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public EnterpriseDTO saveEnterprise(EnterpriseDTO enterpriseDTO) {
        try {
            return EnterpriseMapper.toDTO(enterpriseRepository.save(EnterpriseMapper.toEntity(enterpriseDTO)));
        }catch (Exception exception){
            log.error("Address with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deleteEnterpriseById(Long id) {
        if (enterpriseRepository.existsById(id)) {
            try{
                enterpriseRepository.deleteById(id);
            }catch (Exception exception) {
                log.error("Can not delete this entity"+exception.getMessage());
                throw new RuntimeException("Can not delete this entity  :   "+exception.getMessage());
            }
        } else {
            log.error("Entity Not Exist");
            throw new RuntimeException("Entity Not Exist");
        }
    }

    @Override
    public EnterpriseDTO updateEnterprise(EnterpriseDTO enterpriseDTO) {
        Enterprise existingEnterprise = enterpriseRepository.findById(enterpriseDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + enterpriseDTO.getId());
                });
        existingEnterprise.setLocalEnterprise(enterpriseDTO.getLocalEntreprise());
        existingEnterprise.setEnterpriseName(enterpriseDTO.getNomEntreprise());
        try {
            return EnterpriseMapper.toDTO(enterpriseRepository.save(existingEnterprise));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }

    @Override
    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }

    @Override
    public Optional<Enterprise> findOne(Long id) {
        return enterpriseRepository.findById(id);
    }

    @Override
    public Enterprise saveOne(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public void deleteOne(Long id) {
       enterpriseRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return enterpriseRepository.existsById(id);
    }


}
