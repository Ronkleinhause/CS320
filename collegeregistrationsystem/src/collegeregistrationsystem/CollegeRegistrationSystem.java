package collegeregistrationsystem;

import java.time.LocalDate;
import java.util.Iterator;


public class CollegeRegistrationSystem implements ICollegeRegistrationSystem 
{
	private final CollegeMemberRegistry collegeMemberRegistry_;
	private final CourseRegistry courseRegistry_;
	
	
	public CollegeRegistrationSystem() {
		super();
		
		this.collegeMemberRegistry_ = new CollegeMemberRegistry();
		this.courseRegistry_ = new CourseRegistry();
	}
	
	@Override
	public Faculty addFaculty(String firstName, String lastName, String emailAddress, Department department, Degree degree) {
		// This uses the factory method design pattern.
		return this.collegeMemberRegistry_.addFaculty(firstName, lastName, emailAddress, department, degree);
	}
	
	@Override
	public Student addStudent(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String homeAddress) {
		// This uses the factory method design pattern.
		return this.collegeMemberRegistry_.addStudent(firstName, lastName, dateOfBirth, emailAddress, homeAddress);
	}
	
	@Override
	public CollegeMember getCollegeMember(int collegeMemberID) 
		throws CollegeMemberNotFoundException 
	{
		return this.collegeMemberRegistry_.getCollegeMember(collegeMemberID);
	}
	
	@Override
	public Iterator<CollegeMember> getCollegeMemberListing() {
		// This uses the iterator design pattern.
		return this.collegeMemberRegistry_.getCollegeMemberListing();
	}
	
	@Override
	public Course addCourse(String title, int numberOfCredits) 
		throws CourseAlreadyExistsException 
	{
		// This uses the factory method design pattern.
		return this.courseRegistry_.addCourse(title, numberOfCredits);
	}
	
	@Override
	public CourseSection addCourseSection(int courseID, int courseSectionEnrollmentCap) 
		throws CourseNotFoundException 
	{
		return this.courseRegistry_.addCourseSection(courseID, courseSectionEnrollmentCap);
	}
	
	@Override
	public void assignFaculty(int courseID, int courseSectionNum, int facultyID) 
		throws CollegeMemberNotFoundException, 
		       CourseNotFoundException,
		       CourseSectionNotFoundException, 
		       FacultyOnLeaveException
	{
		Course existingCourse = this.courseRegistry_.getCourse(courseID);
		CourseSection existingCourseSection = existingCourse.getCourseSection(courseSectionNum);
		Faculty existingFaculty = (Faculty)this.collegeMemberRegistry_.getCollegeMember(facultyID);
		existingCourseSection.assignFaculty(existingFaculty);
	}

	@Override
	public boolean enroll(int courseID, int courseSectionNum, int studentID) 
		throws CollegeMemberNotFoundException,
		       CourseNotFoundException,
		       CourseSectionNotFoundException, 
		       CourseSectionFullException
	{
		Course existingCourse = this.courseRegistry_.getCourse(courseID);
		CourseSection existingCourseSection = existingCourse.getCourseSection(courseSectionNum);
		Student existingStudent = (Student)this.collegeMemberRegistry_.getCollegeMember(studentID);		
		return existingCourseSection.enroll(existingStudent);
	}

	@Override
	public boolean drop(int courseID, int courseSectionNum, int studentID) 
		throws CollegeMemberNotFoundException,
			   CourseNotFoundException,
			   CourseSectionNotFoundException
	{
		Course existingCourse = this.courseRegistry_.getCourse(courseID);
		CourseSection existingCourseSection = existingCourse.getCourseSection(courseSectionNum);
		Student existingStudent = (Student)this.collegeMemberRegistry_.getCollegeMember(studentID);		
		return existingCourseSection.drop(existingStudent);
	}
	
	public Course getCourse(int courseID) throws CourseNotFoundException {
		return this.courseRegistry_.getCourse(courseID);
	}
	
	@Override
	public Iterator<Course> getCourseListing() {
		// This uses the iterator design pattern.
		return this.courseRegistry_.getCourseListing();
	}
}
