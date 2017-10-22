package Core.dao;

import java.sql.SQLException;
import java.util.Collection;

import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.exceptions.CouponSystemException;

/**
 *
 * The interface defines general operations of companies table.
 */
public interface CompanyDao {

	/**
	 * Create a new Company in the Company table in the Database
	 */
	void createCompany(Company company) throws CouponSystemException;

	/**
	 * Remove a Company from Database, including all the company's coupons
	 */
	void deleteCompany(Company company) throws CouponSystemException;

	/**
	 * Update a Company in the Company table in the Database
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * Get a Company from the Company table in the Database BY ID
	 */
	public Company getCompany(long id) throws CouponSystemException;

	/**
	 * Get a Company from the Company table in the Database BY STRING
	 */
	public Company getCompany(String company) throws CouponSystemException;

	/**
	 * Get all the Companies from the Company table in the Database
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * Get ALL the given Company's COUPONS from the companyCoupon join table in
	 * the Database
	 */
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException, SQLException;

	/**
	 * Login: check the name and the password of the company in conjunction with
	 * the company table in the Database
	 */
	public boolean login(String compName, String Password) throws CouponSystemException;

}
