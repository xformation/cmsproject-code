package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PeriodsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Periods.
 */
public interface PeriodsService {

    /**
     * Save a periods.
     *
     * @param periodsDTO the entity to save
     * @return the persisted entity
     */
    PeriodsDTO save(PeriodsDTO periodsDTO);

    /**
     * Get all the periods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeriodsDTO> findAll(Pageable pageable);
    /**
     * Get all the PeriodsDTO where Teacher is null.
     *
     * @return the list of entities
     */
    List<PeriodsDTO> findAllWhereTeacherIsNull();


    /**
     * Get the "id" periods.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PeriodsDTO> findOne(Long id);

    /**
     * Delete the "id" periods.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the periods corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeriodsDTO> search(String query, Pageable pageable);
}
