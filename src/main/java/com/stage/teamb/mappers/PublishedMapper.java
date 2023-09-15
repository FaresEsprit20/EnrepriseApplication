package com.stage.teamb.mappers;

import com.stage.teamb.models.Published;
import com.stage.teamb.dtos.PublishedDTO;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class PublishedMapper {

    public static PublishedDTO toDTO(Published published) {
        return PublishedDTO.builder()
                .id(published.getId())
                .nom(published.getNom())
                .description(published.getDescription())
                .employeeId(published.getEmployee() != null ? published.getEmployee().getId() : null)
                .createdAt(published.getCreatedAt())
                .updatedAt(published.getUpdatedAt())
                .build();
    }

    public static List<PublishedDTO> toListDTO(List<Published> publishedList) {
        return publishedList.stream()
                .map(PublishedMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Published toEntity(PublishedDTO publishedDTO) {
        return Published.builder()
                .id(publishedDTO.getId())
                .nom(publishedDTO.getNom())
                .description(publishedDTO.getDescription())
                .createdAt(publishedDTO.getCreatedAt())
                .updatedAt(publishedDTO.getUpdatedAt())
                .build();
    }

    public static List<Published> toListEntity(List<PublishedDTO> publishedDTOList) {
        return publishedDTOList.stream()
                .map(PublishedMapper::toEntity)
                .collect(Collectors.toList());
    }
}
