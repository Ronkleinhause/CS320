package collegeregistrationsystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Course 
{
	private final int ID_;
	private String title_;
	private int numberOfCredits_;
	private List<CourseSection> courseSectionsListing_;
	
	
	protected Course(int ID, String title, int numberOfCredits) {
		// Check the pre-conditions
		if (ID < 1) {
			throw new IllegalArgumentException("The course ID must be positive.");
		}
		if ((title == null) || (title.trim().equals(""))) {
			throw new IllegalArgumentException("The course title must be non-null and non-empty.");
		}
		if (numberOfCredits < 0) {
			throw new IllegalArgumentException("The course must be worth zero or more credits.");
		}
		this.ID_ = ID;
		this.title_ = title;
		this.numberOfCredits_ = numberOfCredits;
		this.courseSectionsListing_ = new ArrayList<CourseSection>();
	}
	
	public int getID() {
		return this.ID_;
	}
	
	public String getTitle() {
		return this.title_;
	}
	
	public int getNumberOfCredits() {
		return this.numberOfCredits_;
	}
	
	protected synchronized CourseSection addCourseSection(int courseSectionEnrollmentCap) {
		// This is the factory method design pattern.
		CourseSection newCourseSection = new CourseSection(this, this.courseSectionsListing_.size() + 1, courseSectionEnrollmentCap);
		this.courseSectionsListing_.add(newCourseSection);
		return newCourseSection;
	}
	
	public synchronized CourseSection getCourseSection(int courseSectionNumber) throws CourseSectionNotFoundException 
	{
		CourseSection courseSection = null;
		for (CourseSection currentCourseSection : this.courseSectionsListing_) {
			if (currentCourseSection.getSectionNumber() == courseSectionNumber) {
				courseSection = currentCourseSection;
				break;
			}
		} // end for currentCourseSection
		if (courseSection == null) {
			throw new CourseSectionNotFoundException("The course with ID " + this.getID() + " does not have a section with number " + courseSectionNumber + ".");
		}
		return courseSection;
	}
	
	public synchronized Iterator<CourseSection> getCourseSectionsListing() {
		// This is the iterator design pattern.
		return this.courseSectionsListing_.iterator();
	}
	
	public int hashCode() {
		String stringifyCourse = "" + this.getID() + " " + this.getTitle() + " " + this.getNumberOfCredits();
		return stringifyCourse.hashCode();
	}
	
	public boolean equals(Object obj2) {
		if (obj2 == null) {
			return false;
		}
		
		if (! (obj2 instanceof Course)) {
			return false;
		}
		Course course2 = (Course)obj2;
		if (this.getID() != course2.getID()) {
			return false;
		}
		if (! this.getTitle().equals(course2.getTitle())) {
			return false;
		}
		if (this.getNumberOfCredits() != course2.getNumberOfCredits()) {
			return false;
		}
		
		return true;
	}

}
