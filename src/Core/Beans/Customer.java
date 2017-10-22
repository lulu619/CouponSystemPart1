package Core.Beans;

import java.util.Collection;

/**
 * Customer class, presenter attributes ,constructors ,and getters and setters.
 */
public class Customer {

	// Attributes
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	// CTOR
	public Customer() {

	}

	// CTOR
	public Customer(long id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	// CTOR
	public Customer(String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
	}

	// CTOR
	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	// get customer id
	public long getId() {
		return id;
	}

	// set customer id
	public void setId(long id) {
		this.id = id;
	}

	// get customer name
	public String getCustName() {
		return custName;
	}

	// set customer name
	public void setCustName(String custName) {
		this.custName = custName;
	}

	// get customer password
	public String getPassword() {
		return password;
	}

	// set customer password
	public void setPassword(String password) {
		this.password = password;
	}

	// get collection of coupons
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	// toString
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";
	}

}
