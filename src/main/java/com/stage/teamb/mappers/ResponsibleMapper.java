package com.stage.teamb.mappers;

import com.stage.teamb.models.Responsible;
import com.stage.teamb.dtos.ResponsableDTO;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ResponsibleMapper {

    public static ResponsableDTO toDTO(Responsible responsible) {
        ResponsableDTO responsableDTO = new ResponsableDTO();
        responsableDTO.setId(responsible.getId());
        responsableDTO.setNom(responsible.getNom());
        responsableDTO.setPrenom(responsible.getPrenom());
        responsableDTO.setMatricule(responsible.getMatricule());
        responsableDTO.setCreatedAt(responsible.getCreatedAt());
        responsableDTO.setUpdatedAt(responsible.getUpdatedAt());
        return responsableDTO;
    }

    public static List<ResponsableDTO> toListDTO(List<Responsible> responsibleList) {
        List<ResponsableDTO> responsableDTOList = new ArrayList<>();
        responsibleList.forEach(responsible -> {
            responsableDTOList.add(toDTO(responsible));
        });
        return responsableDTOList;
    }

    public static Responsible  toEntity(ResponsableDTO responsableDTO) {
        Responsible responsible = new Responsible();
        responsible.setId(responsableDTO.getId());
        responsible.setNom(responsableDTO.getNom());
        responsible.setPrenom(responsableDTO.getPrenom());
        responsible.setMatricule(responsableDTO.getMatricule());
        responsible.setCreatedAt(responsableDTO.getCreatedAt());
        responsible.setUpdatedAt(responsableDTO.getUpdatedAt());
        return responsible;
    }

    public static List<Responsible> toListEntity(List<ResponsableDTO> responsableDTOList) {
        List<Responsible> responsableList = new ArrayList<>();
        responsableDTOList.forEach(responsibleDto -> {
            responsableList.add(toEntity(responsibleDto));
        });
        return responsableList;
    }

}
