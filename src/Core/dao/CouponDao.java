package Core.dao;

import java.util.Collection;
import Core.Beans.Coupon;
import Core.Beans.CouponType;
import Core.exceptions.CouponSystemException;

/**
 * The interface defines general operations of coupons table.
 */
public interface CouponDao {

	/**
	 * Create a new Coupon in the Coupon table in the Database
	 */
	void createCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Delete a Coupon from Database
	 */
	void deleteCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Update a Coupon in the Coupon table in the Database
	 */
	void updateCoupon(Coupon coupon) throws CouponSystemException;

	/**
	 * Get a coupon from the Coupon table in the Database BY ID
	 */
	public Coupon getCoupon(Long id) throws CouponSystemException;

	/**
	 * Get all the Coupons from the Coupon table in the Database
	 */
	public Collection<Coupon> getAllCoupon() throws CouponSystemException;

	/**
	 * Get all the Coupons of the given TYPE from the Coupon table in the
	 * Database
	 */

	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException;

	/**
	 * Get all the Coupons of the given PRICE from the Coupon table in the
	 * Database
	 */
	public Collection<Coupon> getCouponByPrice(double price) throws CouponSystemException;

}
