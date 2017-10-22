package Threads;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import Core.Beans.Coupon;
import Core.dbDao.CouponDbDao;
import Core.exceptions.CouponSystemException;

/**
 * The class contains two methods , run method that runs every 24 hours and
 * calls deleteExpirationCoupons to delete all expired coupons.
 */
public class DailyCouponExpirationTask implements MyRunnable {

	private CouponDbDao coupDbDao;

	public DailyCouponExpirationTask() throws CouponSystemException, SQLException {
		
		coupDbDao = new CouponDbDao();
	}

	/**
	 * Deletes all coupons who have passed their expiration date.
	 */
	public void deleteExpirationCoupons() throws CouponSystemException {
		Collection<Coupon> expirationCoupons = coupDbDao.getAllCoupon();
		for (Coupon coupon : expirationCoupons) {
			if (coupon.getEndDate().before(new Date())) {
				coupDbDao.deleteCoupon(coupon);
			}
		}
	}

	/**
	 * Runs every 24 four and calls deleteExpirationCoupons to delete all
	 * expired coupons.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				deleteExpirationCoupons();
				try {
					Thread.sleep(1000 * 60 * 60 * 24);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (CouponSystemException e) {
			throw new RuntimeException("task failed");
		}

	}

}
