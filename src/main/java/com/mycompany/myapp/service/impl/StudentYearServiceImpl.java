package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.StudentYearService;
import com.mycompany.myapp.domain.StudentYear;
import com.mycompany.myapp.repository.StudentYearRepository;
import com.mycompany.myapp.repository.search.StudentYearSearchRepository;
import com.mycompany.myapp.service.dto.StudentYearDTO;
import com.mycompany.myapp.service.mapper.StudentYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentYear.
 */
@Service
@Transactional
public class StudentYearServiceImpl implements StudentYearService {

    private final Logger log = LoggerFactory.getLogger(StudentYearServiceImpl.class);

    private final StudentYearRepository studentYearRepository;

    private final StudentYearMapper studentYearMapper;

    private final StudentYearSearchRepository studentYearSearchRepository;

    public StudentYearServiceImpl(StudentYearRepository studentYearRepository, StudentYearMapper studentYearMapper, StudentYearSearchRepository studentYearSearchRepository) {
        this.studentYearRepository = studentYearRepository;
        this.studentYearMapper = studentYearMapper;
        this.studentYearSearchRepository = studentYearSearchRepository;
    }

    /**
     * Save a studentYear.
     *
     * @param studentYearDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentYearDTO save(StudentYearDTO studentYearDTO) {
        log.debug("Request to save StudentYear : {}", studentYearDTO);
        StudentYear studentYear = studentYearMapper.toEntity(studentYearDTO);
        studentYear = studentYearRepository.save(studentYear);
        StudentYearDTO result = studentYearMapper.toDto(studentYear);
        studentYearSearchRepository.save(studentYear);
        return result;
    }

    /**
     * Get all the studentYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentYears");
        return studentYearRepository.findAll(pageable)
            .map(studentYearMapper::toDto);
    }


    /**
     * Get one studentYear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentYearDTO> findOne(Long id) {
        log.debug("Request to get StudentYear : {}", id);
        return studentYearRepository.findById(id)
            .map(studentYearMapper::toDto);
    }

    /**
     * Delete the studentYear by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentYear : {}", id);
        studentYearRepository.deleteById(id);
        studentYearSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentYear corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentYearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentYears for query {}", query);
        return studentYearSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentYearMapper::toDto);
    }
}
