package collegeregistrationsystem;

import java.util.LinkedHashMap;
import java.util.Map;

public class CollegeMember 
{
	private final int ID_;
	private String firstName_;
	private String lastName_;
	private String emailAddress_;
	
	
	protected CollegeMember(int ID, String firstName, String lastName, String emailAddress) {
		super();
		
		// Check the pre-conditions
		if (ID < 1) {
			throw new IllegalArgumentException("The college member ID must be a positive number.");
		}
		if ((firstName == null) || firstName.trim().equals("")) {
			throw new IllegalArgumentException("The college nember first name must be non-null and non-empty.");
		}
		if ((emailAddress == null) || emailAddress.trim().equals("")) {
			throw new IllegalArgumentException("The college member last number must be non-null and non-empty.");
		}
		this.ID_ = ID;
		this.firstName_ = firstName.trim();
		this.lastName_ = lastName.trim();
		this.emailAddress_ = emailAddress.trim();
	}
	
	public int getID() {
		return this.ID_;
	}
	
	public String getFirstName() {
		return this.firstName_;
	}
	
	public String getLastName() {
		return this.lastName_;
	}
	
	public String getEmailAddress() {
		return this.emailAddress_;
	}
	
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	public boolean equals(Object obj2) {
		if (obj2 == null) {
			return false;
		}
		
		if (! (obj2 instanceof CollegeMember)) {
			return false;
		}
		CollegeMember collegeMember2 = (CollegeMember)obj2;
		if (this.getID() != collegeMember2.getID()) {
			return false;
		}
		if (! this.getFirstName().equals(collegeMember2.getFirstName())) {
			return false;
		}
		if (! this.getLastName().equals(collegeMember2.getLastName())) {
			return false;
		}
		if (! this.getEmailAddress().equals(collegeMember2.getEmailAddress())) {
			return false;
		}
		
		return true;
	}
	
	protected Map<String,String> getToStringAttributes() {
		Map<String,String> attributes = new LinkedHashMap<String,String>();
		
		attributes.put("ID", "" + this.getID());
		attributes.put("firstName", this.getFirstName());
		attributes.put("lastName", this.getLastName());
		attributes.put("emailAddress", this.getEmailAddress());
		
		return attributes;
	}
	
	public String toString() {
		String stringValue = this.getClass().getSimpleName() + " " + this.getToStringAttributes();
		return stringValue;
	}
}
