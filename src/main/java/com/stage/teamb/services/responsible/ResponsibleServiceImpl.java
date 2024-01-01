package com.stage.teamb.services.responsible;


import com.stage.teamb.dtos.responsible.ResponsibleDTO;
import com.stage.teamb.mappers.ResponsibleMapper;
import com.stage.teamb.models.Responsible;
import com.stage.teamb.repository.jpa.responsible.ResponsibleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ResponsibleServiceImpl implements ResponsibleService {

    private final ResponsibleRepository responsibleRepository;

    @Autowired
    public ResponsibleServiceImpl(ResponsibleRepository responsibleRepository) {
        this.responsibleRepository = responsibleRepository;
    }

    @Override
    public List<ResponsibleDTO> findAllResponsibles() {
        return ResponsibleMapper.toListDTO(responsibleRepository.findAll());
    }

    @Override
    public ResponsibleDTO findResponsibleById(Long id) {
        return ResponsibleMapper.toDTO(responsibleRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public List<ResponsibleDTO> findAllResponsibleByNameOrLastName(String name, String lastName) {
        return ResponsibleMapper.toListDTO(responsibleRepository.findAllResponsibleByNameOrLastName(name, lastName));
    }

    @Override
    public ResponsibleDTO saveResponsible(ResponsibleDTO responsableDTO) {
        try {
            return ResponsibleMapper.toDTO(responsibleRepository.save(ResponsibleMapper.toEntity(responsableDTO)));
        }catch (Exception exception){
            log.error("Responsible with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deleteResponsibleById(Long id) {
        if (responsibleRepository.existsById(id)) {
            try{
                responsibleRepository.deleteById(id);
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
    public ResponsibleDTO updateResponsible(ResponsibleDTO responsableDTO) {
        Responsible existingResponsible= responsibleRepository.findById(responsableDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + responsableDTO.getId());
                });
         existingResponsible.setName(existingResponsible.getName());
         existingResponsible.setLastName(existingResponsible.getLastName());
        try {
            return ResponsibleMapper.toDTO(responsibleRepository.save(existingResponsible));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }

    @Override
    public List<Responsible> findAll() {
        return responsibleRepository.findAll();
    }

    @Override
    public Optional<Responsible> findOne(Long id) {
        return responsibleRepository.findById(id);
    }

    @Override
    public Responsible saveOne(Responsible Responsible) {
        return responsibleRepository.save(Responsible);
    }

    @Override
    public void deleteOne(Long id) {
        responsibleRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return responsibleRepository.existsById(id);
    }


}


