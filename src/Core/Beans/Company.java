package Core.Beans;

import java.util.Collection;

/**
 * Company class, presenter attributes ,constructors ,and getters and setters.
 */

public class Company {

	// Attributes
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	//CTOR
	public Company() {
		
	}

	//CTOR
	public Company(long id, String password, String email) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
	}

	//CTOR
	public Company(String compName, String password, String email) {
		super();
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	//CTOR
	public Company(long id, String compName, String password, String email) {
		super();
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	// get the Company ID
	public long getId() {
		return id;
	}

	// set the Company ID
	public void setId(long id) {
		this.id = id;
	}

	// get the Company Name
	public String getCompName() {
		return compName;
	}

	//set company name
	public void setCompName(String compName) {
		this.compName = compName;
	}

	// get the company password
	public String getPassword() {
		return password;
	}

	// set company password
	public void setPassword(String password) {
		this.password = password;
	}

	// get company email
	public String getEmail() {
		return email;
	}

	// set company email
	public void setEmail(String email) {
		this.email = email;
	}
	
	// return collection of coupons
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	

	//toString
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}

}
