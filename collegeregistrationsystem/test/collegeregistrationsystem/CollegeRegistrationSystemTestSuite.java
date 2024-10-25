package collegeregistrationsystem;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CollegeRegistrationSystemTestSuite 
{
	private CollegeRegistrationSystem collegeRegistrationSystem_;
	
	public static final String STUDENT_1_FIRSTNAME_VALID = "Jane";
	public static final String STUDENT_1_LASTNAME_VALID = "Doe";
	public static final LocalDate STUDENT_1_DATEOFBIRTH_VALID = LocalDate.parse("2004-01-31");
	public static final String STUDENT_1_EMAILADDRESS_VALID = "jdoe@gmail.com";
	public static final String STUDENT_1_HOMEADDRESS_VALID = "1 Main Street, Smalltown, MA, 01234";
	public static final String STUDENT_1_HOMEADDRESS_INVALID = "";
	
	public static final String COURSE_1_TITLE = Department.COMPUTER_SCIENCE + " 429";
	public static final int COURSE_1_NUMBEROFCREDITS_VALID = 4;
	public static final int COURSE_1_NUMBEROFCREDITS_INVALID = -1;
	
	public static final int COURSE_1_COURSESECTION_1_ENROLLMENTCAP_VALID = 3;
	public static final int COURSE_1_COURSESECTION_1_ENROLLMENTCAP_INVALID = -1;
	
	
	@Before
	public void setUp() {
		this.collegeRegistrationSystem_ = new CollegeRegistrationSystem();
	}
	
	@After
	public void tearDown() {
		this.collegeRegistrationSystem_ = null;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddStudentViolatesPreconditions() 
		throws IllegalArgumentException
	{
		// Call the unit under test
		this.collegeRegistrationSystem_.addStudent(STUDENT_1_FIRSTNAME_VALID, STUDENT_1_LASTNAME_VALID, STUDENT_1_DATEOFBIRTH_VALID, STUDENT_1_EMAILADDRESS_VALID, STUDENT_1_HOMEADDRESS_INVALID);
		// Check the post-conditions
		// 
		// With an invalid home address, should throw IllegalArgumentException (See the @Test annotation)
	}
	
	protected Student testAddStudentSucceedsHelper() {
		// Call the unit under test
		Student student1 = this.collegeRegistrationSystem_.addStudent(STUDENT_1_FIRSTNAME_VALID, STUDENT_1_LASTNAME_VALID, STUDENT_1_DATEOFBIRTH_VALID, STUDENT_1_EMAILADDRESS_VALID, STUDENT_1_HOMEADDRESS_VALID);
		// Check the post-conditions
		//
		// Check that the class fields are set appropriately
		System.out.println(student1);
		Assert.assertNotNull(student1);
		Assert.assertEquals(STUDENT_1_FIRSTNAME_VALID, student1.getFirstName());
		Assert.assertEquals(STUDENT_1_LASTNAME_VALID, student1.getLastName());
		Assert.assertEquals(STUDENT_1_DATEOFBIRTH_VALID, student1.getDateOfBirth());
		Assert.assertEquals(STUDENT_1_EMAILADDRESS_VALID, student1.getEmailAddress());
		Assert.assertEquals(STUDENT_1_HOMEADDRESS_VALID, student1.getHomeAddress());
		// Check that the new student is the only college member
		Iterator<CollegeMember> collegeMemberListing = this.collegeRegistrationSystem_.getCollegeMemberListing();
		Assert.assertTrue(collegeMemberListing.hasNext());
		CollegeMember firstCollegeMember = collegeMemberListing.next();
		Assert.assertEquals(student1, firstCollegeMember);
		Assert.assertFalse(collegeMemberListing.hasNext());
		return student1;
	}
	
	@Test
	public void testAddStudentSucceeds() {
		this.testAddStudentSucceedsHelper();
	}
	
	@Test
	public void testGetCollegeMemberSucceeds() {
		
		Student student1 = testAddStudentSucceedsHelper();
		int id_ = student1.getID();
		Assert.assertNotNull(id_);
		Assert.assertTrue(id_ > 0);
		CollegeMember output;
		try {
			output = this.collegeRegistrationSystem_.getCollegeMember(id_);
			Assert.assertEquals(student1, output);
		} catch (CollegeMemberNotFoundException e) {
			Assert.fail();
		}
	}
	
	@Test(expected=CollegeMemberNotFoundException.class)
	public void testGetCollegeMemberReportsCollegeMemberNotFound() throws CollegeMemberNotFoundException {
		Assert.assertFalse(this.collegeRegistrationSystem_.getCollegeMemberListing().hasNext());
		// Call the unit under test
		this.collegeRegistrationSystem_.getCollegeMember(1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddCourseViolatesPreconditions() 
		throws IllegalArgumentException
	{
		// Call the unit under test
		try {
			this.collegeRegistrationSystem_.addCourse(COURSE_1_TITLE, COURSE_1_NUMBEROFCREDITS_INVALID);
		}
		// Check the post-conditions 
		// 
		// With an invalid number of credits, should throw IllegalArgumentException (See the @Test annotation)
		catch (CourseAlreadyExistsException caee) {
			// Since there is not already a course with this title,
			// this exception should not be thrown.
			Assert.fail();
		}
	}
	
	protected Course testAddCourseSucceedsHelper() {
		// Call the unit under test
		try {
			Course course1 = this.collegeRegistrationSystem_.addCourse(COURSE_1_TITLE, COURSE_1_NUMBEROFCREDITS_VALID);
			// Check the post-conditions
			//
			// Check the class fields
			Assert.assertNotNull(course1);
			Assert.assertEquals(COURSE_1_TITLE, course1.getTitle());
			Assert.assertEquals(COURSE_1_NUMBEROFCREDITS_VALID, course1.getNumberOfCredits());
			// Check that the Course is in only one in the CourseRegistry
			Iterator<Course> courseListing = this.collegeRegistrationSystem_.getCourseListing();
			Assert.assertTrue(courseListing.hasNext());
			Course firstCourse = courseListing.next();
			Assert.assertEquals(course1, firstCourse);
			Assert.assertFalse(courseListing.hasNext());
			return course1;
		}
		// Check the post-conditions
		catch (CourseAlreadyExistsException caee) {
			// Since there is not already a course with this title,
			// this exception should not be thrown.
			Assert.fail();
			return null;
		}
	}
	
	@Test
	public void testAddCourseSucceeds() {
		this.testAddCourseSucceedsHelper();
	}
	
	@Test(expected=CourseAlreadyExistsException.class)
	public void testAddCourseReportsCourseAlreadyExists() throws CourseAlreadyExistsException
	{
		// Perform setup and check pre-conditions
		this.testAddCourseSucceedsHelper();
		// Call the unit under test
		this.collegeRegistrationSystem_.addCourse(COURSE_1_TITLE, COURSE_1_NUMBEROFCREDITS_VALID);
		// Check the post-conditions 
		// 
		// Since the setup already added a course with the same title,
		// should throw CourseAlreadyExistsException (see @Test annotation)
	}
		
	@Test
	public void testGetCourseSucceeds() 
	{
		// Perform setup and check pre-conditions
		Course course1 = this.testAddCourseSucceedsHelper();
		// Call the unit under test
		try {
			Course courseFound = this.collegeRegistrationSystem_.getCourse(course1.getID());
			// Check the post-conditions
			Assert.assertEquals(course1, courseFound);
		}
		catch (CourseNotFoundException cnfe) {
			// The setup ensured that course1 does exist
			// in the CourseRegistry.
			Assert.fail();
		}
	}
	
	@Test(expected=CourseNotFoundException.class)
	public void testGetCourseReportsCourseNotFound() throws CourseNotFoundException
	{
		// Perform setup and check pre-conditions
		// 
		// There are no courses.
		Assert.assertFalse(this.collegeRegistrationSystem_.getCourseListing().hasNext());
		// Call the unit under test
		this.collegeRegistrationSystem_.getCourse(1);
		// Check the post-conditions
		//
		// When there are no courses, should throw CourseNotFoundException (See @Test annotation)
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddCourseSectionViolationsPreconditions() 
		throws IllegalArgumentException
	{
		// Perform the setup and check the pre-conditions
		Course course1 = this.testAddCourseSucceedsHelper();
		// Call the unit under test
		try {
			this.collegeRegistrationSystem_.addCourseSection(course1.getID(), COURSE_1_COURSESECTION_1_ENROLLMENTCAP_INVALID);
		}
		catch (CourseNotFoundException cnfe) {
			// The setup ensured that course1 does exist
			// in the CourseRegistry.
			Assert.fail();
		}
	}
	
	protected CourseSection testAddCourseSectionSucceedsHelper() {
		// Perform the setup and check the pre-conditions
		Course course1 = this.testAddCourseSucceedsHelper();
		// Call the unit under test
		try {
			CourseSection courseSection1 = this.collegeRegistrationSystem_.addCourseSection(course1.getID(), COURSE_1_COURSESECTION_1_ENROLLMENTCAP_VALID);
			// Check the post-conditions
			//
			// Check the class fields
			Assert.assertNotNull(courseSection1);
			Assert.assertEquals(course1, courseSection1.getCourse());
			Assert.assertEquals(1, courseSection1.getSectionNumber());
			Assert.assertEquals(COURSE_1_COURSESECTION_1_ENROLLMENTCAP_VALID, courseSection1.getEnrollmentCap());
			// A faculty has not been assigned to teach this course section yet
			Assert.assertNull(courseSection1.getAssignedFaculty());
			// There are no students enrolled in the course section yet
			Assert.assertFalse(courseSection1.getEnrolledStudentsListing().hasNext());
			// Check that the course has only this course section
			Iterator<CourseSection> courseSectionsListing = course1.getCourseSectionsListing();
			Assert.assertTrue(courseSectionsListing.hasNext());
			CourseSection firstCourseSection = courseSectionsListing.next();
			Assert.assertEquals(courseSection1, firstCourseSection);
			Assert.assertFalse(courseSectionsListing.hasNext());
			return courseSection1;
		}
		catch (CourseNotFoundException cnfe) {
			// The setup ensured that course1 does exist
			// in the CourseRegistry.
			Assert.fail();
			return null;
		}
	}
	
	@Test
	public void testAddCourseSectionSucceeds() {
		this.testAddCourseSectionSucceedsHelper();
	}
	
	@Test
	public void testEnrollReturnsTrue() {
		Student student1 = testAddStudentSucceedsHelper();
		CourseSection courseSection1 = testAddCourseSectionSucceedsHelper();
		Course course1 = courseSection1.getCourse();
		int student_id = student1.getID();
		int course_id = course1.getID();
		int courseSectionNum = courseSection1.getSectionNumber();
		Assert.assertNotNull(student_id);
		Assert.assertNotNull(course_id);
		Assert.assertNotNull(courseSectionNum);
		Assert.assertTrue(student_id > 0 && course_id > 0 && courseSectionNum > 0);
		try{
			boolean enrolled = collegeRegistrationSystem_.enroll(course_id, courseSectionNum, student_id);
			Assert.assertTrue(enrolled);
		}catch(CollegeMemberNotFoundException | CourseNotFoundException | CourseSectionNotFoundException | CourseSectionFullException badException){
			Assert.fail();
		}
	}
	
	@Test
	public void testEnrollReturnsFalse() {
		Student student1 = testAddStudentSucceedsHelper();
		Student student2 = this.collegeRegistrationSystem_.addStudent("CCC", "BBB", LocalDate.parse("2005-01-31"), "AA", "AA");
		CourseSection courseSection1 = testAddCourseSectionSucceedsHelper();
		Course course1 = courseSection1.getCourse();
		int student_id = student1.getID();
		int course_id = course1.getID();
		try { 			//checking for CourseSectionFullException
			CourseSection courseSection2 = this.collegeRegistrationSystem_.addCourseSection(course_id, 1);
			Assert.assertTrue(collegeRegistrationSystem_.enroll(course_id, courseSection2.getSectionNumber(), student_id));
			Assert.assertThrows(CourseSectionFullException.class, () -> {
				collegeRegistrationSystem_.enroll(course_id, courseSection2.getSectionNumber(), student2.getID());	});
		} catch (CourseNotFoundException | CollegeMemberNotFoundException | CourseSectionNotFoundException | CourseSectionFullException e) {
			Assert.fail();
		}

		int courseSectionNum = courseSection1.getSectionNumber();
		//check for other ecaeptions different edge cases for each value
		Assert.assertThrows(CourseNotFoundException.class, () -> {
            collegeRegistrationSystem_.enroll(5, courseSectionNum, student_id);	});
		Assert.assertThrows(CourseNotFoundException.class, () -> {
            collegeRegistrationSystem_.enroll(-1, courseSectionNum, student_id);	});
		Assert.assertThrows(CourseNotFoundException.class, () -> {
			collegeRegistrationSystem_.enroll(0, courseSectionNum, student_id);	});	
		Assert.assertThrows(CourseSectionNotFoundException.class, () -> {
            collegeRegistrationSystem_.enroll(course_id, 0, student_id);	});
		Assert.assertThrows(CollegeMemberNotFoundException.class, () -> {
            collegeRegistrationSystem_.enroll(course_id, courseSectionNum, -2045);	});
		Assert.assertThrows(CourseSectionNotFoundException.class, () -> {
            collegeRegistrationSystem_.enroll(course_id, 3222, student_id);	});
		Assert.assertThrows(CollegeMemberNotFoundException.class, () -> {
            collegeRegistrationSystem_.enroll(course_id, courseSectionNum, 0);	});
		Assert.assertThrows(CourseSectionNotFoundException.class, () -> {
			collegeRegistrationSystem_.enroll(course_id, -36, student_id);	});
		Assert.assertThrows(CollegeMemberNotFoundException.class, () -> {
			collegeRegistrationSystem_.enroll(course_id, courseSectionNum, 5);	});
	}
	@Test 
	public void testDropReturnsTrue() throws CollegeMemberNotFoundException, CourseNotFoundException, CourseSectionNotFoundException, CourseSectionFullException {
		//intializing
		Student student1 = testAddStudentSucceedsHelper();
		Student student2 = this.collegeRegistrationSystem_.addStudent("CCC", "BBB", LocalDate.parse("2005-01-31"), "AA", "AA");
		CourseSection courseSection1 = testAddCourseSectionSucceedsHelper();
		Course course1 = courseSection1.getCourse();
		int student_id = student1.getID();
		int course_id = course1.getID();
		int sectionNumber = courseSection1.getSectionNumber();
		//adding dropping, checking it was removed
		Assert.assertTrue(courseSection1.enroll(student1));
		Assert.assertTrue(courseSection1.enroll(student2));
		int i = 0;
		Iterator<Student> itr =  courseSection1.getEnrolledStudentsListing();
		while (itr.hasNext()) {
			itr.next();
			i++;
		}		Assert.assertEquals(i,2);
		i=0;
		Assert.assertTrue(courseSection1.drop(student1));
		itr =  courseSection1.getEnrolledStudentsListing();
		while (itr.hasNext()) {
			itr.next();
			i++;
		}
		Assert.assertEquals(i,1);
		i=0;
		Assert.assertTrue(courseSection1.drop(student2));
		itr =  courseSection1.getEnrolledStudentsListing();
		while (itr.hasNext()) {
			itr.next();
			i++;
		}		
		Assert.assertEquals(i,0);		
	}
	
	@Test 
	public void testDropReturnsFalse() throws CourseSectionFullException {
		//intializing
		Student student1 = testAddStudentSucceedsHelper();
		Student student2 = this.collegeRegistrationSystem_.addStudent("CCC", "BBB", LocalDate.parse("2005-01-31"), "AA", "AA");
		CourseSection courseSection1 = testAddCourseSectionSucceedsHelper();
		Course course1 = courseSection1.getCourse();
		int student_id = student1.getID();
		int course_id = course1.getID();
		int sectionNumber = courseSection1.getSectionNumber();
		//adding two students
		Assert.assertTrue(courseSection1.enroll(student1));
		Assert.assertTrue(courseSection1.enroll(student2));
		//asserting throws right exceptions
		Assert.assertThrows(CourseNotFoundException.class, () -> {
            collegeRegistrationSystem_.drop(5, sectionNumber, student_id);	});
		Assert.assertThrows(CourseSectionNotFoundException.class, () -> {
            collegeRegistrationSystem_.drop(course_id, 5, student_id);	});
		Assert.assertThrows(CollegeMemberNotFoundException.class, () -> {
            collegeRegistrationSystem_.drop(course_id, sectionNumber, 5);	});
	}
}
