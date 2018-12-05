package com.mycompany.myapp.graphql.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mycompany.myapp.domain.enumeration.ClassPeriods;
import com.mycompany.myapp.domain.enumeration.ClassSection;
import com.mycompany.myapp.domain.enumeration.Common;
import com.mycompany.myapp.domain.enumeration.Elective;
import com.mycompany.myapp.domain.enumeration.SYear;
import com.mycompany.myapp.service.dto.CollegeBranchesDTO;
import com.mycompany.myapp.service.dto.PeriodsDTO;
import com.mycompany.myapp.service.dto.SectionDTO;
import com.mycompany.myapp.service.dto.StudentDTO;
import com.mycompany.myapp.service.dto.StudentYearDTO;
import com.mycompany.myapp.service.dto.SubjectDTO;
import com.mycompany.myapp.service.dto.TeacherDTO;
import com.mycompany.myapp.service.impl.CollegeBranchesServiceImpl;
import com.mycompany.myapp.service.impl.PeriodsServiceImpl;
import com.mycompany.myapp.service.impl.SectionServiceImpl;
import com.mycompany.myapp.service.impl.StudentServiceImpl;
import com.mycompany.myapp.service.impl.StudentYearServiceImpl;
import com.mycompany.myapp.service.impl.SubjectServiceImpl;
import com.mycompany.myapp.service.impl.TeacherServiceImpl;

@Component
public class Mutation implements GraphQLMutationResolver{


	
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
	
	public PeriodsDTO newPeriods(Long id, ClassPeriods periods, Long sectionId) {
	    PeriodsDTO periodsDTO = new PeriodsDTO();
	    periodsDTO.setId(id);
	    periodsDTO.setPeriods(periods);
	    periodsDTO.setSectionId(sectionId);
	    PeriodsDTO val = periodsServiceImpl.save(periodsDTO);
	    return val;
	    }
	
	public TeacherDTO newTeacher(Long id,String tName,Long periodsId){
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(id);
        teacherDTO.settName(tName);
        teacherDTO.setPeriodsId(periodsId);
        TeacherDTO val = teacherServiceImpl.save(teacherDTO);
        return val;
    }
	
	public StudentDTO newStudent(Long id,String sName,Boolean attendance,Elective electiveSub,Long teacherId){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(id);
        studentDTO.setsName(sName);
        studentDTO.setAttendance(attendance);
        studentDTO.setElectiveSub(electiveSub);
        studentDTO.setTeacherId(teacherId);
        StudentDTO val = studentServiceImpl.save(studentDTO);
        return val;
    }
	
	public SubjectDTO newSubject(Long id, Common commonSub, Elective electiveSub, Long periodsId, Long studentId, Long teacherId) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(id);
        subjectDTO.setCommonSub(commonSub);
        subjectDTO.setElectiveSub(electiveSub);
        subjectDTO.setPeriodsId(periodsId);
        subjectDTO.setStudentId(studentId);
        subjectDTO.setTeacherId(teacherId);
        SubjectDTO val = subjectServiceImpl.save(subjectDTO);
        return val;
    }
	
	public CollegeBranchesDTO newCollegeBranches(Long id,String branchName,String description,String collegeHead) {
		  CollegeBranchesDTO collegeBranchesDTO = new CollegeBranchesDTO();
		  collegeBranchesDTO.setId(id);
		  collegeBranchesDTO.setBranchName(branchName);
		  collegeBranchesDTO.setDescription(description);
		  collegeBranchesDTO.setCollegeHead(collegeHead);
		  CollegeBranchesDTO val = collegeBranchesServiceImpl.save(collegeBranchesDTO);
	      return val;
		}
}