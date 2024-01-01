package com.stage.teamb.services.responsible;

import com.stage.teamb.dtos.responsible.ResponsibleDTO;
import com.stage.teamb.models.Responsible;

import java.util.List;
import java.util.Optional;

public interface ResponsibleService {


    ResponsibleDTO findResponsibleByEmail(String email);
    List<ResponsibleDTO> findAllResponsibles();

    ResponsibleDTO findResponsibleById(Long id);

    List<ResponsibleDTO> findAllResponsibleByNameOrLastName(String name, String lastName);

    ResponsibleDTO saveResponsible(ResponsibleDTO responsableDTO);

    void deleteResponsibleById(Long id);

    ResponsibleDTO updateResponsible(ResponsibleDTO responsableDTO);

    List<Responsible> findAll();

    Optional<Responsible> findOne(Long id);

    Responsible saveOne(Responsible Responsible);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}