package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.GeneralInfoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.GeneralInfoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GeneralInfo.
 */
@RestController
@RequestMapping("/api")
public class GeneralInfoResource {

    private final Logger log = LoggerFactory.getLogger(GeneralInfoResource.class);

    private static final String ENTITY_NAME = "generalInfo";

    private final GeneralInfoService generalInfoService;

    public GeneralInfoResource(GeneralInfoService generalInfoService) {
        this.generalInfoService = generalInfoService;
    }

    /**
     * POST  /general-infos : Create a new generalInfo.
     *
     * @param generalInfoDTO the generalInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new generalInfoDTO, or with status 400 (Bad Request) if the generalInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/general-infos")
    @Timed
    public ResponseEntity<GeneralInfoDTO> createGeneralInfo(@Valid @RequestBody GeneralInfoDTO generalInfoDTO) throws URISyntaxException {
        log.debug("REST request to save GeneralInfo : {}", generalInfoDTO);
        if (generalInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new generalInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneralInfoDTO result = generalInfoService.save(generalInfoDTO);
        return ResponseEntity.created(new URI("/api/general-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /general-infos : Updates an existing generalInfo.
     *
     * @param generalInfoDTO the generalInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated generalInfoDTO,
     * or with status 400 (Bad Request) if the generalInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the generalInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/general-infos")
    @Timed
    public ResponseEntity<GeneralInfoDTO> updateGeneralInfo(@Valid @RequestBody GeneralInfoDTO generalInfoDTO) throws URISyntaxException {
        log.debug("REST request to update GeneralInfo : {}", generalInfoDTO);
        if (generalInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeneralInfoDTO result = generalInfoService.save(generalInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, generalInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /general-infos : get all the generalInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of generalInfos in body
     */
    @GetMapping("/general-infos")
    @Timed
    public List<GeneralInfoDTO> getAllGeneralInfos() {
        log.debug("REST request to get all GeneralInfos");
        return generalInfoService.findAll();
    }

    /**
     * GET  /general-infos/:id : get the "id" generalInfo.
     *
     * @param id the id of the generalInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the generalInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/general-infos/{id}")
    @Timed
    public ResponseEntity<GeneralInfoDTO> getGeneralInfo(@PathVariable Long id) {
        log.debug("REST request to get GeneralInfo : {}", id);
        Optional<GeneralInfoDTO> generalInfoDTO = generalInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generalInfoDTO);
    }

    /**
     * DELETE  /general-infos/:id : delete the "id" generalInfo.
     *
     * @param id the id of the generalInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/general-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeneralInfo(@PathVariable Long id) {
        log.debug("REST request to delete GeneralInfo : {}", id);
        generalInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/general-infos?query=:query : search for the generalInfo corresponding
     * to the query.
     *
     * @param query the query of the generalInfo search
     * @return the result of the search
     */
    @GetMapping("/_search/general-infos")
    @Timed
    public List<GeneralInfoDTO> searchGeneralInfos(@RequestParam String query) {
        log.debug("REST request to search GeneralInfos for query {}", query);
        return generalInfoService.search(query);
    }

}
