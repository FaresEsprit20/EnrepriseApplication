package com.stage.teamb.services;

import com.stage.teamb.dtos.ResponsableDTO;
import com.stage.teamb.models.Responsible;

import java.util.List;
import java.util.Optional;

public interface ResponsibleService {


    List<ResponsableDTO> findAllResponsibles();

    ResponsableDTO findResponsibleById(Long id);

    ResponsableDTO saveResponsible(ResponsableDTO responsableDTO);

    void deleteResponsibleById(Long id);

    ResponsableDTO updateResponsible(ResponsableDTO responsableDTO);

    List<Responsible> findAll();

    Optional<Responsible> findOne(Long id);

    Responsible saveOne(Responsible Responsible);

    void deleteOne(Long id);

    Boolean existsById(Long id);
}