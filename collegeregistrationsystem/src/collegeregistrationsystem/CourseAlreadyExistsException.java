package collegeregistrationsystem;


public class CourseAlreadyExistsException extends Exception 
{
	public CourseAlreadyExistsException(String msg) {
		super(msg);
	}
}
