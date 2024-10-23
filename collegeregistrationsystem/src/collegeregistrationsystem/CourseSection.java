package collegeregistrationsystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CourseSection 
{
	private final Course course_;
	private int sectionNumber_;
	private int enrollmentCap_;
	private Faculty assignedFaculty_;
	private List<Student> enrolledStudentsListing_;
	
	
	protected CourseSection(Course course, int sectionNumber, int enrollmentCap) {
		super();
		
		// Check pre-conditions
		if (course == null) {
			throw new IllegalArgumentException("The course must be non-null.");
		}
		if (sectionNumber < 1) {
			throw new IllegalArgumentException("The course section number must be positive.");
		}
		if (enrollmentCap < 1) {
			throw new IllegalArgumentException("The course section enrollment cap must be positive.");
		}		
		this.course_ = course;
		this.sectionNumber_ = sectionNumber;
		this.enrollmentCap_ = enrollmentCap;
		this.enrolledStudentsListing_ = new ArrayList<Student>();
	}
	
	public synchronized Course getCourse() {
		return this.course_;
	}
	
	public synchronized int getSectionNumber() {
		return this.sectionNumber_;
	}
	
	public synchronized int getEnrollmentCap() {
		return this.enrollmentCap_;
	}
	
	protected synchronized void assignFaculty (Faculty faculty) throws FacultyOnLeaveException 
	{
		// Check the pre-conditions
		if (faculty == null) {
			throw new IllegalArgumentException("The faculty cannot be null.");
		}
		if (faculty.getIsOnLeave()) {
			throw new FacultyOnLeaveException();
		}
		this.assignedFaculty_ = faculty;
	}
	
	public synchronized Faculty getAssignedFaculty() {
		return this.assignedFaculty_;
	}
	
	protected synchronized boolean enroll (Student newStudent) throws CourseSectionFullException 
	{
		// Check the pre-conditions
		if (newStudent == null) {
			throw new IllegalArgumentException("The new student must be non-null.");
		}
		if (this.enrolledStudentsListing_.size() == this.enrollmentCap_) {
			throw new CourseSectionFullException();
		}
		return this.enrolledStudentsListing_.add(newStudent);
	}
	
	protected synchronized boolean drop (Student existingStudent) {
		return this.enrolledStudentsListing_.remove(existingStudent);
	}
	
	public synchronized Iterator<Student> getEnrolledStudentsListing() {
		// This is the iterator design pattern.
		return this.enrolledStudentsListing_.iterator();
	}
}
