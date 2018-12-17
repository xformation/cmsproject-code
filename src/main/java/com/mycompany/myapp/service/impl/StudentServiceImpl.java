package com.mycompany.myapp.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.graphql.resolvers.StudentFilter;
import com.mycompany.myapp.graphql.resolvers.StudentOrder;
import com.mycompany.myapp.repository.search.StudentSearchRepository;
import com.mycompany.myapp.repository.StudentRepository;
import com.mycompany.myapp.service.StudentService;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.mapper.StudentMapper;

import java.util.List;

/**
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	
	@PersistenceContext
    private EntityManager em;

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final StudentSearchRepository studentSearchRepository;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, StudentSearchRepository studentSearchRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentSearchRepository = studentSearchRepository;
    }

    /**
     * Save a student.
     *
     * @param studentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Request to save Student : {}", studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        StudentDTO result = studentMapper.toDto(student);
        //studentSearchRepository.save(student);
        return result;
    }

    /**
     * Get all the students.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable)
            .map(studentMapper::toDto);
    }


    /**
     * Get one student by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDTO> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id)
            .map(studentMapper::toDto);
    }

    /**
     * Delete the student by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
        studentSearchRepository.deleteById(id);
    }

    /**
     * Search for the student corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Students for query {}", query);
        return studentSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentMapper::toDto);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Student> findAllByFilterOrder(StudentFilter filter, List<StudentOrder> orders) throws DataAccessException{
        StringBuilder sb = new StringBuilder("SELECT student FROM Student student");

        Optional<StudentFilter> nonNullFilter = Optional.ofNullable(filter);
        nonNullFilter.ifPresent(f -> sb.append(f.buildJpaQuery()));

        sb.append(StudentOrder.buildOrderJpaQuery(orders));
        
        System.out.println("studentfilter"+sb.toString());
        Query query = this.em.createQuery(sb.toString());
        nonNullFilter.ifPresent(f -> f.buildJpaQueryParameters(query));

        return query.getResultList();
    }

    
}
