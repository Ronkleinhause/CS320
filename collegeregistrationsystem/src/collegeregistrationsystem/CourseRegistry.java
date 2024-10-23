package collegeregistrationsystem;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class CourseRegistry 
{
	private Map<Integer,Course> mapIDToCourse_;
	
	
	public CourseRegistry() {
		super();
		
		this.mapIDToCourse_ = new TreeMap<Integer,Course>();
	}
	
	protected int getNextID() {
		return this.mapIDToCourse_.size() + 1;
	}
	
	protected synchronized Course addCourse(String title, int numberOfCredits) throws CourseAlreadyExistsException 
	{
		// Check the pre-conditions
		for (Course currentCourse : this.mapIDToCourse_.values()) {
			if (currentCourse.getTitle().equals(title)) {
				throw new CourseAlreadyExistsException("The course registry already contains a course titled \"" + title + "\".");
			}
		} // end for currentCourse
		// This is the factory method design pattern.
		Course newCourse = new Course(this.getNextID(), title, numberOfCredits);
		this.mapIDToCourse_.put(newCourse.getID(), newCourse);
		return newCourse;
	}
	
	protected synchronized CourseSection addCourseSection(int courseID, int courseSectionEnrollmentCap) throws CourseNotFoundException 
	{
		// Check the pre-conditions
		Course existingCourse = this.getCourse(courseID);
		return existingCourse.addCourseSection(courseSectionEnrollmentCap);
	}
	
	public synchronized Course getCourse(int courseID) throws CourseNotFoundException 
	{
		// Check the pre-conditions
		if (! this.mapIDToCourse_.keySet().contains(courseID)) {
			throw new CourseNotFoundException("A course with ID " + courseID + " does not exist.");
		}
		return this.mapIDToCourse_.get(courseID);
	}
	
	//TODO: Use the strategy design pattern to take a comparator
	public synchronized Iterator<Course> getCourseListing() {
		//TODO: Shallow copy the values?
		// This is the iterator design pattern.
		return this.mapIDToCourse_.values().iterator();
	}
}
