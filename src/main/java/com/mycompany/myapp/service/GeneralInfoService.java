package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.GeneralInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing GeneralInfo.
 */
public interface GeneralInfoService {

    /**
     * Save a generalInfo.
     *
     * @param generalInfoDTO the entity to save
     * @return the persisted entity
     */
    GeneralInfoDTO save(GeneralInfoDTO generalInfoDTO);

    /**
     * Get all the generalInfos.
     *
     * @return the list of entities
     */
    List<GeneralInfoDTO> findAll();


    /**
     * Get the "id" generalInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GeneralInfoDTO> findOne(Long id);

    /**
     * Delete the "id" generalInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the generalInfo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<GeneralInfoDTO> search(String query);
}
