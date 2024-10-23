package collegeregistrationsystem;

import java.time.LocalDate;
import java.util.Iterator;


public interface ICollegeRegistrationSystem {

	/**
	 * Creates a new Faculty from the valid input parameters and 
	 * adds them to the CollegeMemberRegistry.
	 * 
	 * The pre-conditions are:
	 * 1. A valid firstName must be non-null and non-empty.
	 * 2. A valid lastName must be non-null and non-empty.
	 * 3. A valid email address must be non-null and non-empty.
	 * 4. A valid department must be non-null.
	 * 5. A valid degree must be non-null.
	 * 
	 * @param firstName The first name of the new Faculty
	 * @param lastName The last name of the new Faculty
	 * @param emailAddress The email address of the new Faculty
	 * @param department The department of the new Faculty
	 * @param degree The degree of the new Faculty
	 * @return The new Faculty created from the valid input parameters
	 * @throws IllegalArgumentException if any of the input parameters are invalid
	 */
	public Faculty addFaculty(String firstName, String lastName, String emailAddress, Department department, Degree degree);

	/**
	 * Creates a new Student from the valid input parameters and 
	 * adds them to the CollegeMemberRegistry.
	 * 
	 * The pre-conditions are:
	 * 1. A valid firstName must be non-null and non-empty.
	 * 2. A valid lastName must be non-null and non-empty.
	 * 3. A valid dateOfBirth is non-null.
	 * 4. A valid email address must be non-null and non-empty.
	 * 5. A valid home address must be non-null and non-empty.
	 * 
	 * @param firstName The first name of the new Student
	 * @param lastName The last name of the new Student
	 * @param dateOfBirth The date of birth of the new Student
	 * @param emailAddress The emaill address of the new Student
	 * @param homeAddress The home address of the new Student
	 * @return The new Student created from the valid input parameters
	 * @throws IllegalArgumentException if any of the input parameters are invalid
	 */
	public Student addStudent(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String homeAddress);

	/**
	 * Retrieves the CollegeMember with the given ID if they already exist in
	 * the CollegeMemberRegistry.
	 * 
	 * @param collegeMemberID The ID of the CollegeMember to be retrieved from the CourseRegistry
	 * @return The CollegeMember with the given ID if they already exist in the CollegeMemberRegistry
	 * @throws CollegeMemberNotFoundException if a CollegeMember with the given ID does not exist
	 */
	public CollegeMember getCollegeMember(int collegeMemberID) throws CollegeMemberNotFoundException; 
	
	/**
	 * Returns an Iterator over the list of all college members.
	 * 
	 * NOTE) This is applying the Iterator design pattern.
	 * 
	 * @return An Iterator over the list of all college members
	 */
	public Iterator<CollegeMember> getCollegeMemberListing();

	/**
	 * Creates a new Course from the valid input parameters and 
	 * adds it to the CourseRegistry.
	 * 
	 * The pre-conditions are:
	 * 1. A valid title must be non-null and non-empty.
	 * 2. A valid numberOfCredits must be non-negative.
	 * 
	 * @param title The title for the new Course
	 * @param numberOfCredits The number of credits for the new Course
	 * @return The new Course created from the valid input parameters
	 * @throws CourseAlreadyExistsException if another course with the same title is already exists in the CourseRegistry
	 * @throws IllegalArgumentException if any of the input parameters are invalid
	 */
	public Course addCourse(String title, int numberOfCredits)
		throws CourseAlreadyExistsException;

	/**
	 * Creates a new CourseSection from the valid input parameters and 
	 * adds it to the specified Course.
	 * 
	 * The pre-condition is that a valid enrollment cap for the new CourseSection 
	 * must be a non-negative number.
	 * 
	 * @param courseID The ID of the Course to which the new CourseSection belongs
	 * @param courseSectionEnrollmentCap The enrollment cap for the new CourseSection
	 * @return The new CourseSection created from the valid input parameters
	 * @throws CourseNotFoundException if a Course with the given ID cannot be found in the CourseRegistry
	 * @throws IllegalArgumentException if any of the input parameters are invalid
	 */
	public CourseSection addCourseSection(int courseID, int courseSectionEnrollmentCap) throws CourseNotFoundException;

	/**
	 * Assigns the given Faculty to teach the specified CourseSection.
	 * 
	 * @param courseID The ID of the Course which contains the CourseSection to be taught
	 * @param courseSectionNum The section number of the CourseSection which the Faculty has been assigned to teach
	 * @param facultyID The ID of the Faculty assigned to teach the CourseSection
	 * @throws CollegeMemberNotFoundException if a Faculty with the given ID cannot be found in the CollegeMemberRegistry
	 * @throws CourseNotFoundException if a Course with the given ID cannot be found in the CourseRegistry
	 * @throws CourseSectionNotFoundException if a CourseSection with the given section number cannot be found in the Course
	 * @throws FacultyOnLeaveException if the Faculty is scheduled to be on leave and cannot teach the CourseSection
	 */
	public void assignFaculty(int courseID, int courseSectionNum, int facultyID) 
		throws CollegeMemberNotFoundException,
			   CourseNotFoundException, 
			   CourseSectionNotFoundException, 
			   FacultyOnLeaveException;

	/**
	 * Enrolls the given Student in the specified CourseSection if 
	 * that CourseSection is not already full.
	 * 
	 * @param courseID The ID of the Course which contains the CourseSection
	 * @param courseSectionNum The section number of the CourseSection in which the Student wants to enroll
	 * @param studentID The ID of the Student wanting to enroll in the CourseSection
	 * @throws CollegeMemberNotFoundException if a Student with the given ID cannot be found in the CollegeMemberRegistry
	 * @throws CourseNotFoundException if a Course with the given ID cannot be found in the CourseRegistry
	 * @throws CourseSectionNotFoundException if a CourseSection with the given section number cannot be found in the Course
	 * @throws CourseSectionFullException if the CourseSection is already full and not allowing additional enrollments by Students
	 */
	public boolean enroll(int courseID, int courseSectionNum, int studentID) 
		throws CollegeMemberNotFoundException,
			   CourseNotFoundException, 
			   CourseSectionNotFoundException, 
			   CourseSectionFullException;

	/**
	 * Drops the given Student from the specified CourseSection if that 
	 * Student is enrolled in that CourseSection.
	 * 
	 * @param courseID The ID of the Course which contains the CourseSection
	 * @param courseSectionNum The section number of the CourseSection to be dropped by the Student
	 * @param studentID The ID of the Student wanting to drop the CourseSection
	 * @throws CollegeMemberNotFoundException if a Student with the given ID cannot be found in the CollegeMemberRegistry
	 * @throws CourseNotFoundException if a Course with the given ID cannot be found in the CourseRegistry
	 * @throws CourseSectionNotFoundException if a CourseSection with the given section number cannot be found in the Course
	 */
	public boolean drop(int courseID, int courseSectionNum, int studentID)
			throws CollegeMemberNotFoundException, CourseNotFoundException, CourseSectionNotFoundException;

	/**
	 * Retrieves the Course with the given ID if it already exists in
	 * the CourseRegistry.
	 * 
	 * @param courseID The ID of the Course to be retrieved from the CourseRegistry
	 * @return The Course with the given ID if it already exists in the CourseRegistry
	 * @throws CourseNotFoundException if a course with the given ID does not exist
	 */
	public Course getCourse(int courseID) throws CourseNotFoundException; 
	
	/**
	 * Returns an Iterator over all Courses in the CourseRegistry.
	 * 
	 * NOTE) This is applying the Iterator design pattern.
	 * 
	 * @return An Iterator over all Courses in the CourseRegistry
	 */
	public Iterator<Course> getCourseListing();

}