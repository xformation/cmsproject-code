package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.GeneralInfoService;
import com.mycompany.myapp.domain.GeneralInfo;
import com.mycompany.myapp.repository.GeneralInfoRepository;
import com.mycompany.myapp.repository.search.GeneralInfoSearchRepository;
import com.mycompany.myapp.service.dto.GeneralInfoDTO;
import com.mycompany.myapp.service.mapper.GeneralInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GeneralInfo.
 */
@Service
@Transactional
public class GeneralInfoServiceImpl implements GeneralInfoService {

    private final Logger log = LoggerFactory.getLogger(GeneralInfoServiceImpl.class);

    private final GeneralInfoRepository generalInfoRepository;

    private final GeneralInfoMapper generalInfoMapper;

    private final GeneralInfoSearchRepository generalInfoSearchRepository;

    public GeneralInfoServiceImpl(GeneralInfoRepository generalInfoRepository, GeneralInfoMapper generalInfoMapper, GeneralInfoSearchRepository generalInfoSearchRepository) {
        this.generalInfoRepository = generalInfoRepository;
        this.generalInfoMapper = generalInfoMapper;
        this.generalInfoSearchRepository = generalInfoSearchRepository;
    }

    /**
     * Save a generalInfo.
     *
     * @param generalInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GeneralInfoDTO save(GeneralInfoDTO generalInfoDTO) {
        log.debug("Request to save GeneralInfo : {}", generalInfoDTO);

        GeneralInfo generalInfo = generalInfoMapper.toEntity(generalInfoDTO);
        generalInfo = generalInfoRepository.save(generalInfo);
        GeneralInfoDTO result = generalInfoMapper.toDto(generalInfo);
        //generalInfoSearchRepository.save(generalInfo);
        return result;
    }

    /**
     * Get all the generalInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GeneralInfoDTO> findAll() {
        log.debug("Request to get all GeneralInfos");
        return generalInfoRepository.findAll().stream()
            .map(generalInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one generalInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GeneralInfoDTO> findOne(Long id) {
        log.debug("Request to get GeneralInfo : {}", id);
        return generalInfoRepository.findById(id)
            .map(generalInfoMapper::toDto);
    }

    /**
     * Delete the generalInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GeneralInfo : {}", id);
        generalInfoRepository.deleteById(id);
        generalInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the generalInfo corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GeneralInfoDTO> search(String query) {
        log.debug("Request to search GeneralInfos for query {}", query);
        return StreamSupport
            .stream(generalInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(generalInfoMapper::toDto)
            .collect(Collectors.toList());
    }
}
