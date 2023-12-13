package com.stage.teamb.dtos.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingCountDTO {
    private Long upVotes;
    private Long downVotes;
}
