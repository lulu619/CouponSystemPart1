package Core.dao;

import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.Beans.Customer;
import Core.exceptions.CouponSystemException;

/**
 * The interface defines general operations of customers coupons table and
 * company coupons table.
 *
 */
public interface JoinTableDao {

	/**
	 * Create a new Company coupon in the companyCoupon table in the Database
	 */
	void createCompanyCoupon(Company company, Coupon coupon) throws CouponSystemException;

	/**
	 * Create a new Customer coupon in the customerCoupon table in the Database
	 */
	void createCustomerCoupon(Customer customer, Coupon coupon) throws CouponSystemException;

	/**
	 * Delete Company coupon in the companyCoupon table in the Database
	 */
	void deleteCompanyCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Delete Customer coupon in the customerCoupon table in the Database
	 */
	void deleteCustomerCoupon(Coupon coupon) throws CouponSystemException;

}
