package com.stage.teamb.services;

import com.stage.teamb.dtos.RatingDTO;
import com.stage.teamb.mappers.RatingMapper;
import com.stage.teamb.models.Rating;
import com.stage.teamb.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public List<RatingDTO> findAllRatings() {
        return RatingMapper.toListDTO(ratingRepository.findAll());
    }

    @Override
    public RatingDTO findRatingById(Long id) {
        return RatingMapper.toDTO(ratingRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found ")));
    }

    @Override
    public RatingDTO saveRating(RatingDTO ratingDTO) {
        try {
            return RatingMapper.toDTO(ratingRepository.save(RatingMapper.toEntity(ratingDTO)));
        }catch (Exception exception){
            log.error("Rating with not found.");
            throw new RuntimeException("Can not save this entity  :   "+exception.getMessage());
        }
    }

    @Override
    public void deleteRatingById(Long id) {
        if (ratingRepository.existsById(id)) {
            try{
                ratingRepository.deleteById(id);
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
    public RatingDTO updateRating(RatingDTO ratingDTO) {
        Rating existingRating= ratingRepository.findById(ratingDTO.getId())
                .orElseThrow(() -> {
                    log.error("entity not found ");
                    return new RuntimeException("entity not found with id " + ratingDTO.getId());
                });
        existingRating.setValue(ratingDTO.getValue());
        try {
            return RatingMapper.toDTO(ratingRepository.save(existingRating));
        }catch (Exception exception){
            log.error("Could not update "+exception.getMessage());
            throw new RuntimeException("Could not update "+exception.getMessage());
        }
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> findOne(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Rating saveOne(Rating Rating) {
        return ratingRepository.save(Rating);
    }

    @Override
    public void deleteOne(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return ratingRepository.existsById(id);
    }



}