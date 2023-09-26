package com.stage.teamb.mappers;

import com.stage.teamb.dtos.ResponsibleDTO;
import com.stage.teamb.models.Responsible;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ResponsibleMapper {

    public static ResponsibleDTO toDTO(Responsible responsible) {
        ResponsibleDTO responsableDTO = new ResponsibleDTO();
        responsableDTO.setId(responsible.getId());
        responsableDTO.setName(responsible.getName());
        responsableDTO.setLastName(responsible.getLastName());
        responsableDTO.setRegistrationNumber(responsible.getRegistrationNumber());
        responsableDTO.setCreatedAt(responsible.getCreatedAt());
        responsableDTO.setUpdatedAt(responsible.getUpdatedAt());
        return responsableDTO;
    }

    public static List<ResponsibleDTO> toListDTO(List<Responsible> responsibleList) {
        List<ResponsibleDTO> responsableDTOList = new ArrayList<>();
        responsibleList.forEach(responsible -> {
            responsableDTOList.add(toDTO(responsible));
        });
        return responsableDTOList;
    }

    public static Responsible  toEntity(ResponsibleDTO responsableDTO) {
        Responsible responsible = new Responsible();
        responsible.setId(responsableDTO.getId());
        responsible.setName(responsableDTO.getName());
        responsible.setLastName(responsableDTO.getLastName());
        responsible.setRegistrationNumber(responsableDTO.getRegistrationNumber());
        responsible.setCreatedAt(responsableDTO.getCreatedAt());
        responsible.setUpdatedAt(responsableDTO.getUpdatedAt());
        return responsible;
    }

    public static List<Responsible> toListEntity(List<ResponsibleDTO> responsableDTOList) {
        List<Responsible> responsableList = new ArrayList<>();
        responsableDTOList.forEach(responsibleDto -> {
            responsableList.add(toEntity(responsibleDto));
        });
        return responsableList;
    }

}
