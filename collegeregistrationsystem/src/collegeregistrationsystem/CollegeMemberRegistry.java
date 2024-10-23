package collegeregistrationsystem;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class CollegeMemberRegistry 
{
	private Map<Integer, CollegeMember> mapIDtoCollegeMember_;
	
	
	public CollegeMemberRegistry() {
		super();
		
		this.mapIDtoCollegeMember_ = new TreeMap<Integer,CollegeMember>();
	}
	
	protected synchronized int getNextID() {
		return this.mapIDtoCollegeMember_.size() + 1;
	}
	
	protected void addCollegeMember(CollegeMember newCollegeMemmber) {
		this.mapIDtoCollegeMember_.put(newCollegeMemmber.getID(), newCollegeMemmber);
	}
	
	protected synchronized Faculty addFaculty(String firstName, String lastName, String emailAddress, Department department, Degree degree) {
		// This is the factory method design pattern.
		Faculty newFaculty = new Faculty(this.getNextID(), firstName, lastName, emailAddress, department, degree);
		this.addCollegeMember(newFaculty);
		return newFaculty;
	}
	
	protected synchronized Student addStudent(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String homeAddress) {
		// This is the factory method design pattern.
		Student newStudent = new Student(this.getNextID(), firstName, lastName, dateOfBirth, emailAddress, homeAddress);
		this.addCollegeMember(newStudent);
		return newStudent;
	}
	
	protected CollegeMember getCollegeMember(int collegeMemberID) throws CollegeMemberNotFoundException {
		CollegeMember collegeMember = this.mapIDtoCollegeMember_.get(collegeMemberID);
		if (collegeMember == null) {
			throw new CollegeMemberNotFoundException("The college member registry does not contain a college member with ID " + collegeMemberID + ".");
		}
		return collegeMember;
	}
	
	//TODO: Use the strategy design pattern to take a comparator
	public synchronized Iterator<CollegeMember> getCollegeMemberListing() {
		// This is the iterator design pattern.
		return this.mapIDtoCollegeMember_.values().iterator();
	}
}
