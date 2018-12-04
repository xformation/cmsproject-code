package com.mycompany.myapp.graphql.resolvers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mycompany.myapp.service.dto.SectionDTO;
import com.mycompany.myapp.service.dto.StudentYearDTO;
import com.mycompany.myapp.service.impl.SectionServiceImpl;
import com.mycompany.myapp.service.impl.StudentYearServiceImpl;

@Component
public class Query implements GraphQLQueryResolver{

//	@Autowired
//	private SubjectsServiceImpl	subjectsService;
//	
	@Autowired
	private StudentYearServiceImpl studentYearServiceImpl;
	
	 @Autowired
	 private SectionServiceImpl sectionServiceImpl;
	
//	public SubjectsDTO find(Long id) {
//		Optional<SubjectsDTO> val = subjectsService.findOne(id);
//		if(val.isPresent()) {
//			return val.get();
//		}
//		return new SubjectsDTO();
//	}
	
	public StudentYearDTO findStudentYear(Long id) {
        Optional<StudentYearDTO> val = studentYearServiceImpl.findOne(id);
        if (val.isPresent()) {
            return val.get();
        }
        return new StudentYearDTO();
    }
	
	public SectionDTO findSection(Long id) {
        Optional<SectionDTO> val = sectionServiceImpl.findOne(id);
        if (val.isPresent()) {
            return val.get();
        }
        return new SectionDTO();
    }
	
}