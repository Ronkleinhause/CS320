package collegeregistrationsystem;

import java.time.LocalDate;
import java.util.Map;


public class Student extends CollegeMember 
{
	private final LocalDate dateOfBirth_;
	private String homeAddress_;
	
	
	protected Student(int ID, String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String homeAddress) {
		super(ID, firstName, lastName, emailAddress);
		
		//Check pre-conditions
		if (dateOfBirth == null) {
			throw new IllegalArgumentException("The student date of birth must be non-null.");
		}
		this.dateOfBirth_ = dateOfBirth;
		this.setHomeAddress(homeAddress);
	}
	
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth_;
	}
	
	public String getHomeAddress() {
		return this.homeAddress_;
	}
	
	protected void setHomeAddress(String homeAddress) {
		//Check pre-conditions
		if ((homeAddress == null) || homeAddress.trim().equals("")) {
			throw new IllegalArgumentException("The student home address must be non-null and non-empty.");
		}
		this.homeAddress_ = homeAddress;
	}
	
	protected Map<String,String> getToStringAttributes() {
		Map<String,String> attributes = super.getToStringAttributes();
		
		attributes.put("dateOfBirth", "" + this.getDateOfBirth());
		attributes.put("homeAddress", this.getHomeAddress());
		
		return attributes;
	}
	
	public boolean equals(Object obj2) {
		if (super.equals(obj2) == false) {
			return false;
		}
		
		if (! (obj2 instanceof Student)) {
			return false;
		}
		Student student2 = (Student)obj2;
		if (! this.getDateOfBirth().equals(student2.getDateOfBirth())) {
			return false;
		}
		if (! this.getHomeAddress().equals(student2.getHomeAddress())) {
			return false;
		}
		
		return true;
	}

}
