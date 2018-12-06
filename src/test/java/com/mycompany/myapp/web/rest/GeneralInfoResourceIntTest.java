package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CmsprojectApp;

import com.mycompany.myapp.domain.GeneralInfo;
import com.mycompany.myapp.repository.GeneralInfoRepository;
import com.mycompany.myapp.repository.search.GeneralInfoSearchRepository;
import com.mycompany.myapp.service.GeneralInfoService;
import com.mycompany.myapp.service.dto.GeneralInfoDTO;
import com.mycompany.myapp.service.mapper.GeneralInfoMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GeneralInfoResource REST controller.
 *
 * @see GeneralInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmsprojectApp.class)
public class GeneralInfoResourceIntTest {

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_LOGO = 1L;
    private static final Long UPDATED_LOGO = 2L;

    private static final Long DEFAULT_BACKGROUND_IMAGE = 1L;
    private static final Long UPDATED_BACKGROUND_IMAGE = 2L;

    private static final String DEFAULT_INSTRUCTION_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTION_INFORMATION = "BBBBBBBBBB";

    @Autowired
    private GeneralInfoRepository generalInfoRepository;


    @Autowired
    private GeneralInfoMapper generalInfoMapper;
    

    @Autowired
    private GeneralInfoService generalInfoService;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.GeneralInfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private GeneralInfoSearchRepository mockGeneralInfoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeneralInfoMockMvc;

    private GeneralInfo generalInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeneralInfoResource generalInfoResource = new GeneralInfoResource(generalInfoService);
        this.restGeneralInfoMockMvc = MockMvcBuilders.standaloneSetup(generalInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneralInfo createEntity(EntityManager em) {
        GeneralInfo generalInfo = new GeneralInfo()
            .shortName(DEFAULT_SHORT_NAME)
            .logo(DEFAULT_LOGO)
            .backgroundImage(DEFAULT_BACKGROUND_IMAGE)
            .instructionInformation(DEFAULT_INSTRUCTION_INFORMATION);
        return generalInfo;
    }

    @Before
    public void initTest() {
        generalInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneralInfo() throws Exception {
        int databaseSizeBeforeCreate = generalInfoRepository.findAll().size();

        // Create the GeneralInfo
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);
        restGeneralInfoMockMvc.perform(post("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the GeneralInfo in the database
        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeCreate + 1);
        GeneralInfo testGeneralInfo = generalInfoList.get(generalInfoList.size() - 1);
        assertThat(testGeneralInfo.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testGeneralInfo.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testGeneralInfo.getBackgroundImage()).isEqualTo(DEFAULT_BACKGROUND_IMAGE);
        assertThat(testGeneralInfo.getInstructionInformation()).isEqualTo(DEFAULT_INSTRUCTION_INFORMATION);

        // Validate the GeneralInfo in Elasticsearch
        verify(mockGeneralInfoSearchRepository, times(1)).save(testGeneralInfo);
    }

    @Test
    @Transactional
    public void createGeneralInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = generalInfoRepository.findAll().size();

        // Create the GeneralInfo with an existing ID
        generalInfo.setId(1L);
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralInfoMockMvc.perform(post("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeneralInfo in the database
        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the GeneralInfo in Elasticsearch
        verify(mockGeneralInfoSearchRepository, times(0)).save(generalInfo);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalInfoRepository.findAll().size();
        // set the field null
        generalInfo.setShortName(null);

        // Create the GeneralInfo, which fails.
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);

        restGeneralInfoMockMvc.perform(post("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLogoIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalInfoRepository.findAll().size();
        // set the field null
        generalInfo.setLogo(null);

        // Create the GeneralInfo, which fails.
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);

        restGeneralInfoMockMvc.perform(post("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBackgroundImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalInfoRepository.findAll().size();
        // set the field null
        generalInfo.setBackgroundImage(null);

        // Create the GeneralInfo, which fails.
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);

        restGeneralInfoMockMvc.perform(post("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstructionInformationIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalInfoRepository.findAll().size();
        // set the field null
        generalInfo.setInstructionInformation(null);

        // Create the GeneralInfo, which fails.
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);

        restGeneralInfoMockMvc.perform(post("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isBadRequest());

        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeneralInfos() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

        // Get all the generalInfoList
        restGeneralInfoMockMvc.perform(get("/api/general-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.intValue())))
            .andExpect(jsonPath("$.[*].backgroundImage").value(hasItem(DEFAULT_BACKGROUND_IMAGE.intValue())))
            .andExpect(jsonPath("$.[*].instructionInformation").value(hasItem(DEFAULT_INSTRUCTION_INFORMATION.toString())));
    }
    

    @Test
    @Transactional
    public void getGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

        // Get the generalInfo
        restGeneralInfoMockMvc.perform(get("/api/general-infos/{id}", generalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(generalInfo.getId().intValue()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.intValue()))
            .andExpect(jsonPath("$.backgroundImage").value(DEFAULT_BACKGROUND_IMAGE.intValue()))
            .andExpect(jsonPath("$.instructionInformation").value(DEFAULT_INSTRUCTION_INFORMATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGeneralInfo() throws Exception {
        // Get the generalInfo
        restGeneralInfoMockMvc.perform(get("/api/general-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

        int databaseSizeBeforeUpdate = generalInfoRepository.findAll().size();

        // Update the generalInfo
        GeneralInfo updatedGeneralInfo = generalInfoRepository.findById(generalInfo.getId()).get();
        // Disconnect from session so that the updates on updatedGeneralInfo are not directly saved in db
        em.detach(updatedGeneralInfo);
        updatedGeneralInfo
            .shortName(UPDATED_SHORT_NAME)
            .logo(UPDATED_LOGO)
            .backgroundImage(UPDATED_BACKGROUND_IMAGE)
            .instructionInformation(UPDATED_INSTRUCTION_INFORMATION);
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(updatedGeneralInfo);

        restGeneralInfoMockMvc.perform(put("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isOk());

        // Validate the GeneralInfo in the database
        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeUpdate);
        GeneralInfo testGeneralInfo = generalInfoList.get(generalInfoList.size() - 1);
        assertThat(testGeneralInfo.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testGeneralInfo.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testGeneralInfo.getBackgroundImage()).isEqualTo(UPDATED_BACKGROUND_IMAGE);
        assertThat(testGeneralInfo.getInstructionInformation()).isEqualTo(UPDATED_INSTRUCTION_INFORMATION);

        // Validate the GeneralInfo in Elasticsearch
        verify(mockGeneralInfoSearchRepository, times(1)).save(testGeneralInfo);
    }

    @Test
    @Transactional
    public void updateNonExistingGeneralInfo() throws Exception {
        int databaseSizeBeforeUpdate = generalInfoRepository.findAll().size();

        // Create the GeneralInfo
        GeneralInfoDTO generalInfoDTO = generalInfoMapper.toDto(generalInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restGeneralInfoMockMvc.perform(put("/api/general-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeneralInfo in the database
        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GeneralInfo in Elasticsearch
        verify(mockGeneralInfoSearchRepository, times(0)).save(generalInfo);
    }

    @Test
    @Transactional
    public void deleteGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);

        int databaseSizeBeforeDelete = generalInfoRepository.findAll().size();

        // Get the generalInfo
        restGeneralInfoMockMvc.perform(delete("/api/general-infos/{id}", generalInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneralInfo> generalInfoList = generalInfoRepository.findAll();
        assertThat(generalInfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GeneralInfo in Elasticsearch
        verify(mockGeneralInfoSearchRepository, times(1)).deleteById(generalInfo.getId());
    }

    @Test
    @Transactional
    public void searchGeneralInfo() throws Exception {
        // Initialize the database
        generalInfoRepository.saveAndFlush(generalInfo);
        when(mockGeneralInfoSearchRepository.search(queryStringQuery("id:" + generalInfo.getId())))
            .thenReturn(Collections.singletonList(generalInfo));
        // Search the generalInfo
        restGeneralInfoMockMvc.perform(get("/api/_search/general-infos?query=id:" + generalInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.intValue())))
            .andExpect(jsonPath("$.[*].backgroundImage").value(hasItem(DEFAULT_BACKGROUND_IMAGE.intValue())))
            .andExpect(jsonPath("$.[*].instructionInformation").value(hasItem(DEFAULT_INSTRUCTION_INFORMATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralInfo.class);
        GeneralInfo generalInfo1 = new GeneralInfo();
        generalInfo1.setId(1L);
        GeneralInfo generalInfo2 = new GeneralInfo();
        generalInfo2.setId(generalInfo1.getId());
        assertThat(generalInfo1).isEqualTo(generalInfo2);
        generalInfo2.setId(2L);
        assertThat(generalInfo1).isNotEqualTo(generalInfo2);
        generalInfo1.setId(null);
        assertThat(generalInfo1).isNotEqualTo(generalInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralInfoDTO.class);
        GeneralInfoDTO generalInfoDTO1 = new GeneralInfoDTO();
        generalInfoDTO1.setId(1L);
        GeneralInfoDTO generalInfoDTO2 = new GeneralInfoDTO();
        assertThat(generalInfoDTO1).isNotEqualTo(generalInfoDTO2);
        generalInfoDTO2.setId(generalInfoDTO1.getId());
        assertThat(generalInfoDTO1).isEqualTo(generalInfoDTO2);
        generalInfoDTO2.setId(2L);
        assertThat(generalInfoDTO1).isNotEqualTo(generalInfoDTO2);
        generalInfoDTO1.setId(null);
        assertThat(generalInfoDTO1).isNotEqualTo(generalInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(generalInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(generalInfoMapper.fromId(null)).isNull();
    }
}
