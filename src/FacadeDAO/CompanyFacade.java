package FacadeDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.Beans.CouponType;
import Core.dbDao.CompanyDbDao;
import Core.dbDao.CouponDbDao;
import Core.dbDao.JoinTableDbDao;
import Core.exceptions.CouponSystemException;
import Facade.CouponClientFacade;

/**
 * This class defines the actions that companies can make. Actions that
 * companies can perform: create coupon, delete coupon, update coupon, get
 * coupon, get company, get all coupons, get coupon by type, get coupon by
 * price, get coupon by date.
 *
 */
public class CompanyFacade implements CouponClientFacade {

	private Company currComp;
	private CompanyDbDao compDbDao;
	private CouponDbDao coupDbDao;
	private JoinTableDbDao joinDbDao;
	
	public CompanyFacade() throws CouponSystemException, SQLException {
		this.compDbDao = new CompanyDbDao();
		this.coupDbDao = new CouponDbDao();
		this.joinDbDao = new JoinTableDbDao();
	}

	// CTOR
	public CompanyFacade(Company company) throws CouponSystemException, SQLException {

		this.compDbDao = new CompanyDbDao();
		this.coupDbDao = new CouponDbDao();
		this.joinDbDao = new JoinTableDbDao();
		currComp = company;
	}

	/**
	 * Creates a new coupon and updated the coupon table and company coupon
	 * table.
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void createCoupon(Coupon coupon) throws CouponSystemException {

		coupDbDao.createCoupon(coupon);
		joinDbDao.createCompanyCoupon(currComp, coupon);
		System.out.println("Create new coupon " + coupon + " sucssesfully");
	}

	/**
	 * Delete coupon from coupon table, customer coupon table and company coupon
	 * table by id.
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void deleteCoupon(Coupon coupon) throws CouponSystemException {
		coupDbDao.deleteCoupon(coupon);
		joinDbDao.deleteCompanyCoupon(coupon);
		joinDbDao.deleteCustomerCoupon(coupon);
		System.out.println("Delete coupon sucessefully");
	}

	/**
	 * Update a new data of coupon to the coupon table.
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		coupDbDao.updateCoupon(coupon);
		System.out.println("Update coupon Sucessfully");
	}

	/**
	 * Returns coupon from coupon table by id.
	 * 
	 * @param id
	 * @return
	 * @throws CouponSystemException
	 */
	public Coupon getCoupon(long id) throws CouponSystemException {
		return coupDbDao.getCoupon(id);
	}

	/**
	 * Return company from company table by id.
	 * 
	 * @param id
	 * @return
	 * @throws CouponSystemException
	 */
	public Company getCompany(long id) throws CouponSystemException {
		return compDbDao.getCompany(id);
	}

	/**
	 * Returns all coupons.
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupon() throws CouponSystemException {
		return coupDbDao.getAllCoupon();
	}

	/**
	 * Returns coupon by type.
	 * 
	 * @param Type
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Collection<Coupon> getCouponByType(CouponType Type) throws CouponSystemException, SQLException {

		Collection<Coupon> allCoupons = coupDbDao.getAllCoupon();
		Collection<Coupon> coupByType = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (coupon.getType().equals(Type)) {
				coupByType.add(coupon);
			}
		}

		return coupByType;
	}

	/**
	 * Returns coupon by price.
	 * 
	 * @param price
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Collection<Coupon> getCouponByPrice(double price) throws CouponSystemException, SQLException {

		Collection<Coupon> allCoupons = coupDbDao.getAllCoupon();
		Collection<Coupon> coupByPrice = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (coupon.getPrice() <= price) {
				coupByPrice.add(coupon);
				System.out.println("get all coupons Sucessfully");
			}
		}

		return coupByPrice;
	}

	/**
	 * Returns coupon by date.
	 * 
	 * @param date
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public Collection<Coupon> getCuponByDate(Date date) throws CouponSystemException, SQLException {

		Collection<Coupon> allCoupons = coupDbDao.getAllCoupon();
		Collection<Coupon> coupByDate = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (coupon.getStartDate().equals(date)) {
				coupByDate.add(coupon);
			}
		}

		return coupByDate;
	}
}
