package FacadeDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import Core.Beans.Coupon;
import Core.Beans.CouponType;
import Core.Beans.Customer;
import Core.dbDao.CouponDbDao;
import Core.dbDao.CustomerDbDao;
import Core.dbDao.JoinTableDbDao;
import Core.exceptions.CouponSystemException;
import Facade.CouponClientFacade;

/**
 * 
 * This class defines the actions that customers can make. Actions that
 * customers can perform: purchase coupon, get all purchased coupons, get all
 * purchased coupons by type, get all purchased coupon by price.
 * 
 */
public class CustomerFacade implements CouponClientFacade {

	private Customer currCust;
	private CustomerDbDao custDbDao;
	private CouponDbDao coupDbDao;
	private JoinTableDbDao custCoupDao;

	// CTOR
	public CustomerFacade() throws SQLException, CouponSystemException {
		this.custDbDao = new CustomerDbDao();
		this.coupDbDao = new CouponDbDao();
		this.custCoupDao = new JoinTableDbDao();
	}

	// CTOR
	public CustomerFacade(Customer customer) throws SQLException, CouponSystemException {

		this.custDbDao = new CustomerDbDao();
		this.coupDbDao = new CouponDbDao();
		this.custCoupDao = new JoinTableDbDao();
		currCust = customer;

	}

	/**
	 * 
	 * Check if the client bought the same coupon , or Coupon expires or is out
	 * of stock. else the client will be able to purchase coupon.
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Coupon purchaseCoupon(long id) throws CouponSystemException, SQLException {

		Coupon coupon = coupDbDao.getCoupon(id);

		if (coupDbDao.chackPurCoupon(id, currCust.getId()) == false) {

			if (coupon.getAmount() == 0 || coupon.getEndDate().before(new Date())) {

				System.out.println("This coupon out of stock or its validity expires or be purchased");

			} else {
				custCoupDao.createCustomerCoupon(currCust, coupon);
				
				return coupon;
			}

		}
		return null;
	}

	/**
	 * Return all purchased coupons.
	 * 
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws CouponSystemException, SQLException {

		return custDbDao.getCoupons(currCust);
	}

	/**
	 * Return all purchased coupons by type.
	 * 
	 * @param type
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponSystemException, SQLException {

		Collection<Coupon> allPurCoupons = custDbDao.getCoupons(currCust);
		Collection<Coupon> allPurCoupByType = new ArrayList<>();

		for (Coupon coupon : allPurCoupons) {
			if (coupon.getType().equals(type)) {
				allPurCoupByType.add(coupon);

			}
		}

		return allPurCoupByType;
	}

	/**
	 * Return all purchased coupons by price.
	 * 
	 * @param price
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Collection<Coupon> getAllPurchasedCouponByPrice(double price) throws CouponSystemException, SQLException {

		Collection<Coupon> allPurCoupons = custDbDao.getCoupons(currCust);
		Collection<Coupon> allPurCoupByPrice = new ArrayList<>();

		for (Coupon coupon : allPurCoupons) {
			if (coupon.getPrice() <= price) {
				allPurCoupByPrice.add(coupon);
			}
		}

		return allPurCoupByPrice;

	}

}
