package collegeregistrationsystem;

import java.util.Map;

public class Faculty extends CollegeMember 
{
    private Department department_;
    private Degree degree_;
    private boolean isOnLeave_;
    

	protected Faculty(int ID, String firstName, String lastName, String emailAddress, Department department, Degree degree) {
		super(ID, firstName, lastName, emailAddress);
	
		// Check pre-conditions
		if (department == null) {
			throw new IllegalArgumentException("The faculty must be associated with an existing department.");
		}
		this.department_ =department;
		this.setDegree(degree);
		this.setIsOnLeave(false);
	}
	
	public Department getDepartment() {
		return this.department_;
	}
	
	public Degree getDegree() {
		return this.degree_;
	}
	
	protected void setDegree(Degree degree) {
		// Check the pre-conditions
		if (degree == null) {
			throw new IllegalArgumentException("The faculty must have an existing degree.");
		}
		this.degree_ = degree;
	}

	public boolean getIsOnLeave() {
		return this.isOnLeave_;
	}
	
	protected void setIsOnLeave(boolean isOnLeave) {
		this.isOnLeave_ = isOnLeave;
	}
	
	protected Map<String,String> getToStringAttributes() {
		Map<String,String> attributes = super.getToStringAttributes();
		
		attributes.put("department", "" + this.getDepartment());
		attributes.put("degree", "" + this.getDegree());
		attributes.put("isOnLeave", "" + this.getIsOnLeave());
		
		return attributes;
	}
	
	public boolean equals(Object obj2) {
		if (super.equals(obj2) == false) {
			return false;
		}
		
		if (! (obj2 instanceof Faculty)) {
			return false;
		}
		Faculty faculty2 = (Faculty)obj2;
		if (! this.getDepartment().equals(faculty2.getDepartment())) {
			return false;
		}
		if (! this.getDegree().equals(faculty2.getDegree())) {
			return false;
		}
		if (this.getIsOnLeave() != faculty2.getIsOnLeave()) {
			return false;
		}
		
		return true;
	}
}
