package com.stage.teamb.mappers;

import com.stage.teamb.dtos.publication.PublicationDTO;
import com.stage.teamb.dtos.publication.PublicationGetDTO;
import com.stage.teamb.models.Publication;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class PublicationMapper {


    public static PublicationDTO toDTO(Publication published) {
        return PublicationDTO.builder()
                .id(published.getId())
                .name(published.getName())
                .description(published.getDescription())
                .employeeId(published.getEmployee() != null ? published.getEmployee().getId() : null)
                .createdAt(published.getCreatedAt())
                .updatedAt(published.getUpdatedAt())
                .build();
    }

    public static PublicationGetDTO toGetDTO(Publication published) {
        return PublicationGetDTO.builder()
                .id(published.getId())
                .name(published.getName())
                .description(published.getDescription())
                .employeeId(published.getEmployee() != null ? published.getEmployee().getId() : null)
                .employeeFirstName(published.getEmployee() != null ? published.getEmployee().getName(): null)
                .employeeLastName(published.getEmployee() != null ? published.getEmployee().getLastName() : null)
                .createdAt(published.getCreatedAt())
                .updatedAt(published.getUpdatedAt())
                .build();
    }

    public static PublicationGetDTO toGetWithRatingDTO(Publication published, Boolean vote) {
        return PublicationGetDTO.builder()
                .id(published.getId())
                .name(published.getName())
                .description(published.getDescription())
                .employeeId(published.getEmployee() != null ? published.getEmployee().getId() : null)
                .employeeFirstName(published.getEmployee() != null ? published.getEmployee().getName(): null)
                .employeeLastName(published.getEmployee() != null ? published.getEmployee().getLastName() : null)
                .createdAt(published.getCreatedAt())
                .updatedAt(published.getUpdatedAt())
                .build();
    }

    public static List<PublicationDTO> toListDTO(List<Publication> publishedList) {
        return publishedList.stream()
                .map(PublicationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<PublicationGetDTO> toListGetDTO(List<Publication> publishedList) {
        return publishedList.stream()
                .map(PublicationMapper::toGetDTO)
                .collect(Collectors.toList());
    }

    public static Publication toEntity(PublicationDTO publishedDTO) {
        return Publication.builder()
                .id(publishedDTO.getId())
                .name(publishedDTO.getName())
                .description(publishedDTO.getDescription())
                .createdAt(publishedDTO.getCreatedAt())
                .updatedAt(publishedDTO.getUpdatedAt())
                .build();
    }

    public static List<Publication> toListEntity(List<PublicationDTO> publishedDTOList) {
        return publishedDTOList.stream()
                .map(PublicationMapper::toEntity)
                .collect(Collectors.toList());
    }



}
