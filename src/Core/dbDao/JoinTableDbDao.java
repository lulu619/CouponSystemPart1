package Core.dbDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.Beans.Customer;
import Core.dao.JoinTableDao;
import Core.exceptions.CouponSystemException;
import db.pool.ConnectionPool;

/**
 * 
 * *Maintains and updates the companies and customers tables data in the coupon
 * system database. This class defined actions that allow : create customer
 * coupon , create company coupon ,delete customers coupons, delete companies
 * coupons.
 */
public class JoinTableDbDao implements JoinTableDao {

	private ConnectionPool pool = null;

	public JoinTableDbDao() throws CouponSystemException, SQLException {

		pool = ConnectionPool.getInstance();
	}

	/**
	 * This method sends a INSERT command to the data base with the values of
	 * the new company coupon. when he finished he return the connection to the
	 * pool.
	 * 
	 */
	@Override
	public void createCompanyCoupon(Company company, Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "INSERT INTO companyCoupon VALUES(?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, company.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSysEx = new CouponSystemException("Create Company Coupon failed ", e);
			throw coupSysEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

	}

	/**
	 * This method sends a insert command to the data base with the values of
	 * the new customer coupon. when he finished he return the connection to the
	 * pool.
	 * 
	 */
	@Override
	public void createCustomerCoupon(Customer customer, Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "INSERT INTO customerCoupon VALUES(?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, customer.getId());
			pstmt.setLong(2, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSysEx = new CouponSystemException("Create Customer Coupon failed ", e);
			throw coupSysEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

	}

	/**
	 * This method sends a delete command to the data base with the values of
	 * the Coupon object that the method gets. Deletes a Coupon from the Company
	 * Coupon Table. when he finished he return the connection to the pool.
	 */
	@Override
	public void deleteCompanyCoupon(Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "DELETE FROM companyCoupon WHERE coupId = ?";
			PreparedStatement Pstmt = con.prepareStatement(sqlForPstmt);
			Pstmt.setLong(1, coupon.getId());
			Pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSysEx = new CouponSystemException(
					"Delete Coupon " + coupon.getTitle() + " failed ", e);
			throw coupSysEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * This method sends a delete command to the data base with the values of
	 * the Coupon object that the method gets. Deletes a Coupon from the Company
	 * Coupon Table. when he finished he return the connection to the pool.
	 */
	@Override
	public void deleteCustomerCoupon(Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "DELETE FROM customerCoupon WHERE couponId = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSysEx = new CouponSystemException(
					"Delete Coupon " + coupon.getTitle() + " failed ", e);
			throw coupSysEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);

			}
		}
	}

}
