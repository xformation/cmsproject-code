package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.GeneralInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GeneralInfo and its DTO GeneralInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeneralInfoMapper extends EntityMapper<GeneralInfoDTO, GeneralInfo> {



    default GeneralInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        GeneralInfo generalInfo = new GeneralInfo();
        generalInfo.setId(id);
        return generalInfo;
    }
}
