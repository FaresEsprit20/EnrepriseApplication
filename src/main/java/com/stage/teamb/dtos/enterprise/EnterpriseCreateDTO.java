package com.stage.teamb.dtos.enterprise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterpriseCreateDTO {

    private String enterpriseName;
    private String enterpriseLocal;
//    private String[] departmentsIds;

}
