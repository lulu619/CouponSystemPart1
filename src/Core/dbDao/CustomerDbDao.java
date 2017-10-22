package Core.dbDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Core.Beans.Coupon;
import Core.Beans.Customer;
import Core.dao.CustomerDao;
import Core.exceptions.CouponSystemException;
import db.pool.ConnectionPool;

/**
 * *Maintains and updates the customers table data in the coupon system
 * database. This class defined actions that allow : create customer, delete
 * customer, update customer, get customer by id, get customer by name, get all
 * customers, get coupons, allows login and return true or false.
 *
 */
public class CustomerDbDao implements CustomerDao {

	private ConnectionPool pool;

	public CustomerDbDao() throws SQLException, CouponSystemException {
		super();
		pool = ConnectionPool.getInstance();
	}

	/**
	 * Create a new customer and update the customer table, when he finished he
	 * returns the connection to the pool.
	 */
	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "INSERT INTO customer VALUES(?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, customer.getId());
			pstmt.setString(2, customer.getCustName());
			pstmt.setString(3, customer.getPassword());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException(
					"Create customer " + customer.getCustName() + "failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

	}

	/**
	 * This method delete customer from customer table in the coupon system date
	 * base, when he finished he returns the connection to the pool.
	 */
	@Override
	public void deleteCustomer(Customer customer) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "DELETE FROM customer WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, customer.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException(
					"Delete customer " + customer.getCustName() + " failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * Update a new data of customer in the coupon system data base. when he
	 * finished he returns the connection to the pool.
	 */
	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {

		Connection con = pool.getConnection();
		try {

			String sqlForPstmt = "UPDATE customer SET custName = ?, password = ?  WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setString(1, customer.getCustName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setLong(3, customer.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException(
					"Update customer " + customer.getCustName() + "failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * This method returns customer by id and when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Customer getCustomer(long id) throws CouponSystemException {

		Connection con = pool.getConnection();
		Customer customer = null;

		try {
			String sqlForPstmt = "SELECT * FROM customer WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString(2);
				String password = rs.getString(3);
				customer = new Customer(id, name, password);
				rs.close();
			} else {
				rs.close();
				CouponSystemException coupSyEx = new CouponSystemException("Customer id not found");
				throw coupSyEx;
			}

		} catch (Exception e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get customer id " + id + "failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
		return customer;
	}

	/**
	 * This method returns customer by name and when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Customer getCustomer(String name) throws CouponSystemException {

		Connection con = pool.getConnection();
		Customer customer = null;

		try {
			String sqlForPstmt = "SELECT * FROM customer WHERE custName = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				long id = rs.getLong(1);
				String password = rs.getString(3);
				customer = new Customer(id, name, password);
				rs.close();
			} else {
				rs.close();
				CouponSystemException coupSyEx = new CouponSystemException("Customer name not found");
				throw coupSyEx;
			}

		} catch (Exception e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get customer name " + name + "failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
		return customer;

	}

	/**
	 * This method returns all customers from customer table. when he finished
	 * he returns the connection to the pool.
	 */
	@Override
	public Collection<Customer> getAllCustomers() throws CouponSystemException {

		Collection<Customer> allCustomers = new ArrayList<>();
		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "SELECT * FROM customer";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			ResultSet rs = pstmt.executeQuery();
			boolean b = rs.next();
			while (b) {
				long id = rs.getLong(1);
				allCustomers.add(getCustomer(id));
				b = rs.next();
			}
			rs.close();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get all customer failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return allCustomers;

	}

	/**
	 * This method returns coupon by customer and when he finished he returns
	 * the connection to the pool.
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException, SQLException {

		Collection<Coupon> allCoupons = new ArrayList<>();
		Connection con = pool.getConnection();
		CouponDbDao coupDbDao = new CouponDbDao();

		try {
			String sqlForPstmt = "SELECT * FROM customerCoupon WHERE custId = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, customer.getId());
			ResultSet rs = pstmt.executeQuery();
			boolean b = rs.next();
			while (b) {
				long id = rs.getLong(2);
				allCoupons.add(coupDbDao.getCoupon(id));
				b = rs.next();
			}
			rs.close();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get all coupons failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
		return allCoupons;
	}

	/**
	 * This method allow to customers do login to coupon system and return true
	 * or false, When he finished he returns the connection to the pool
	 */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {

		Connection con = pool.getConnection();
		boolean b = false;

		try {
			String sqlForPstmt = "SELECT * FROM customer WHERE custName = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setString(1, custName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			b = rs.next();
			rs.close();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("login failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return b;
	}

}
