package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.StudentYearDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudentYear.
 */
public interface StudentYearService {

    /**
     * Save a studentYear.
     *
     * @param studentYearDTO the entity to save
     * @return the persisted entity
     */
    StudentYearDTO save(StudentYearDTO studentYearDTO);

    /**
     * Get all the studentYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentYearDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentYear.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StudentYearDTO> findOne(Long id);

    /**
     * Delete the "id" studentYear.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the studentYear corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentYearDTO> search(String query, Pageable pageable);
}
