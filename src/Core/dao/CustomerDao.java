package Core.dao;

import java.sql.SQLException;
import java.util.Collection;

import Core.Beans.Coupon;
import Core.Beans.Customer;
import Core.exceptions.CouponSystemException;

/**
 * The interface defines general operations of customers table.
 */
public interface CustomerDao {

	/**
	 * Create a new Customer in the Customer table in the Database
	 */
	void createCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Delete a Customer from Database
	 */
	void deleteCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Update a Customer in the Customer table in the Database
	 */
	void updateCustomer(Customer customer) throws CouponSystemException;

	/**
	 * Get a Customer from the Customer table in the Database BY ID
	 */
	public Customer getCustomer(long id) throws CouponSystemException;

	/**
	 * Get a Customer from the Customer table in the Database BY string NAME
	 */
	public Customer getCustomer(String name) throws CouponSystemException;

	/**
	 * Get all the Customers from the Customer table in the Database
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException;

	/**
	 * Get ALL the given Customer's purchased COUPONS from the customerCoupon
	 * join table in the Database
	 */
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException, SQLException;

	/**
	 * Login: check the name and the password of the customer in conjunction
	 * with the customer table in the Database
	 */
	public boolean login(String custName, String password) throws CouponSystemException;

}
