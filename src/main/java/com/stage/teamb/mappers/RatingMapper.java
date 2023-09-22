package com.stage.teamb.mappers;

import com.stage.teamb.models.Rating;
import com.stage.teamb.dtos.RatingDTO;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class RatingMapper {

    public static RatingDTO toDTO(Rating rating) {
        return RatingDTO.builder()
                .id(rating.getId())
                .value(rating.getValue())
                .employeeId(rating.getEmployee() != null ? rating.getEmployee().getId() : null)
                .publicationId(rating.getPublication() != null ? rating.getPublication().getId() : null)
                .createdAt(rating.getCreatedAt())
                .updatedAt(rating.getUpdatedAt())
                .build();
    }

    public static List<RatingDTO> toListDTO(List<Rating> ratings) {
        List<RatingDTO> ratingDTOList = new ArrayList<>();
        ratings.forEach(rating -> ratingDTOList.add(toDTO(rating)));
        return ratingDTOList;
    }

    public static Rating toEntity(RatingDTO ratingDTO) {
        return Rating.builder()
                .id(ratingDTO.getId())
                .value(ratingDTO.getValue())
                .createdAt(ratingDTO.getCreatedAt())
                .updatedAt(ratingDTO.getUpdatedAt())
                .build();
    }

    public static List<Rating> toListEntity(List<RatingDTO> ratingDTOList) {
        List<Rating> ratingList = new ArrayList<>();
        ratingDTOList.forEach(ratingDTO -> ratingList.add(toEntity(ratingDTO)));
        return ratingList;
    }



}
