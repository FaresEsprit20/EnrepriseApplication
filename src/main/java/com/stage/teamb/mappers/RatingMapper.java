package com.stage.teamb.mappers;

import com.stage.teamb.models.Rating;
import com.stage.teamb.dtos.RatingDTO;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class RatingMapper {


    public static RatingDTO toDTO(Rating rating) {
         RatingDTO ratingDTO = new RatingDTO();
         ratingDTO.setId(rating.getId());
         ratingDTO.setValue(rating.getValue());
         ratingDTO.setEmployeeId(rating.getEmployee()!=null ? rating.getEmployee().getId() : null );
         ratingDTO.setCreatedAt(rating.getCreatedAt());
         ratingDTO.setUpdatedAt(rating.getUpdatedAt());
         return ratingDTO;
    }

    public static List<RatingDTO> toListDTO(List<Rating> ratings) {
         List<RatingDTO> ratingDTOList = new ArrayList<>();
         ratings.forEach(rating -> {
             ratingDTOList.add(toDTO(rating));
         });
         return ratingDTOList;
    }

    public static Rating toEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        rating.setValue(ratingDTO.getValue());
        rating.setCreatedAt(ratingDTO.getCreatedAt());
        rating.setUpdatedAt(ratingDTO.getUpdatedAt());
        return rating;
    }

    public static List<Rating> toListEntity(List<RatingDTO> ratingDTOList) {
          List<Rating> ratingList = new ArrayList<>();
        ratingDTOList.forEach(ratingDTO -> {
            ratingList.add(toEntity(ratingDTO));
        });
        return ratingList;
    }




}
