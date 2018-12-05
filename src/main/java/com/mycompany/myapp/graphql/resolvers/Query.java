package com.mycompany.myapp.graphql.resolvers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mycompany.myapp.service.dto.CollegeBranchesDTO;
import com.mycompany.myapp.service.dto.DepartmentsDTO;
import com.mycompany.myapp.service.dto.LocationDTO;
import com.mycompany.myapp.service.dto.PeriodsDTO;
import com.mycompany.myapp.service.dto.SectionDTO;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.dto.StudentYearDTO;
import com.mycompany.myapp.service.dto.SubjectDTO;
import com.mycompany.myapp.service.dto.TeacherDTO;
import com.mycompany.myapp.service.impl.CollegeBranchesServiceImpl;
import com.mycompany.myapp.service.impl.DepartmentsServiceImpl;
import com.mycompany.myapp.service.impl.LocationServiceImpl;
import com.mycompany.myapp.service.impl.PeriodsServiceImpl;
import com.mycompany.myapp.service.impl.SectionServiceImpl;
import com.mycompany.myapp.service.impl.StudentServiceImpl;
import com.mycompany.myapp.service.impl.StudentYearServiceImpl;
import com.mycompany.myapp.service.impl.SubjectServiceImpl;
import com.mycompany.myapp.service.impl.TeacherServiceImpl;

@Component
public class Query implements GraphQLQueryResolver{

//	@Autowired
//	private SubjectsServiceImpl	subjectsService;
//	
	@Autowired
	private StudentYearServiceImpl studentYearServiceImpl;
	
	 @Autowired
	 private SectionServiceImpl sectionServiceImpl;
	 
	 @Autowired
	 private PeriodsServiceImpl periodsServiceImpl;
	 
	 @Autowired
	 private TeacherServiceImpl teacherServiceImpl;
	 
	 @Autowired
	 private StudentServiceImpl studentServiceImpl;
	 
	 @Autowired
	 private SubjectServiceImpl subjectServiceImpl;
	 
	 @Autowired
	 private CollegeBranchesServiceImpl collegeBranchesServiceImpl;
	 
	 @Autowired
	 private DepartmentsServiceImpl departmentsServiceImpl;
	 
	 @Autowired
	 private LocationServiceImpl locationServiceImpl;
	
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
	
	public PeriodsDTO findPeriods(Long id) {
        Optional<PeriodsDTO> val = periodsServiceImpl.findOne(id);
        if (val.isPresent()) {
            return val.get();
        }
        return new PeriodsDTO();
    }
	
	 public TeacherDTO findTeacher(Long id) {
	        Optional<TeacherDTO> val = teacherServiceImpl.findOne(id);
	        if (val.isPresent()) {
	            return val.get();
	        }
	        return new TeacherDTO();
	    }
	
	 public StudentDTO findStudent(Long id) {
	        Optional<StudentDTO> val = studentServiceImpl.findOne(id);
	        if (val.isPresent()) {
	            return val.get();
	        }
	        return new StudentDTO();
	    }
	 
	 public SubjectDTO findSubject(Long id) {
	        Optional<SubjectDTO> val = subjectServiceImpl.findOne(id);
	        if (val.isPresent()) {
	            return val.get();
	        }
	        return new SubjectDTO();
	    }
	 
	 public CollegeBranchesDTO findCollegeBranches(Long id) {
	        Optional<CollegeBranchesDTO> val = collegeBranchesServiceImpl.findOne(id);
	        if (val.isPresent()) {
	            return val.get();
	        }
	        return new CollegeBranchesDTO();
	    }
	
	 public DepartmentsDTO findDepartments(Long id) {
	        Optional<DepartmentsDTO> val = departmentsServiceImpl.findOne(id);
	        if (val.isPresent()) {
	            return val.get();
	        }
	        return new DepartmentsDTO();
	    }
	 
	 public LocationDTO findLocation(Long id) {
	        Optional<LocationDTO> val = locationServiceImpl.findOne(id);
	        if (val.isPresent()) {
	            return val.get();
	        }
	        return new LocationDTO();
	    }
}