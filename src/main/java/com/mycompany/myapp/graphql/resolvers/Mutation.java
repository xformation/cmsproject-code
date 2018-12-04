package com.mycompany.myapp.graphql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mycompany.myapp.domain.enumeration.ClassSection;
import com.mycompany.myapp.domain.enumeration.SYear;
import com.mycompany.myapp.service.dto.SectionDTO;
import com.mycompany.myapp.service.dto.StudentYearDTO;
import com.mycompany.myapp.service.impl.SectionServiceImpl;
import com.mycompany.myapp.service.impl.StudentYearServiceImpl;

@Component
public class Mutation implements GraphQLMutationResolver{


	
	@Autowired
	private StudentYearServiceImpl studentYearServiceImpl;
	
	@Autowired
    private SectionServiceImpl sectionServiceImpl;
	
	
	public StudentYearDTO newStudentYear(Long id, SYear sYear) {
		StudentYearDTO studentYearDTO = new StudentYearDTO();
		studentYearDTO.setId(id);
		studentYearDTO.setsYear(sYear);
		StudentYearDTO val =studentYearServiceImpl.save(studentYearDTO);
		return val;
	}
	
	public SectionDTO newSection(Long id, ClassSection section, Long studentYearId) {
        SectionDTO sectionDTO = new SectionDTO();
        sectionDTO.setId(id);
        sectionDTO.setSection(section);
        sectionDTO.setStudentyearId(studentYearId);
        SectionDTO val = sectionServiceImpl.save(sectionDTO);
        return val;
    }
}