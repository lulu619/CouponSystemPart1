package Core.dbDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.dao.CompanyDao;
import Core.exceptions.CouponSystemException;
import db.pool.ConnectionPool;

/**
 * Maintains and updates the companies table data in the coupon system database.
 * This class defined actions that allow : create company, delete company,
 * update company, get company by id, get company by name, get all companies,
 * get all coupons, allows login and return true or false.
 */
public class CompanyDbDao implements CompanyDao {

	private ConnectionPool pool;

	public CompanyDbDao() throws CouponSystemException, SQLException {
		super();

		pool = ConnectionPool.getInstance();
	}

	/**
	 * Create a new company and update the company table. and when he finished
	 * he returns the connection to the pool.
	 */
	@Override
	public void createCompany(Company company) throws CouponSystemException {

		Connection con = pool.getConnection();
		try {
			String sqlForPstmt = "INSERT INTO company VALUES(?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, company.getId());
			pstmt.setString(2, company.getCompName());
			pstmt.setString(3, company.getPassword());
			pstmt.setString(4, company.getEmail());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Create company failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * This method delete company. when he finished he returns the connection to
	 * the pool.
	 */
	@Override
	public void deleteCompany(Company company) throws CouponSystemException {

		Connection con = pool.getConnection();
		try {
			String sqlForPstmt = "DELETE FROM company WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, company.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Delete company failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * Update a new data of company in the coupon system data base. when he
	 * finished he returns the connection to the pool.
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {

		Connection con = pool.getConnection();
		try {
			String sqlForPstmt = "UPDATE company SET name = ?, password = ?, email = ? WHERE id= ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setString(1, company.getCompName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			pstmt.setLong(4, company.getId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			CouponSystemException coupSyEx = new CouponSystemException("Update company failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * This method returns company by id and when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Company getCompany(long id) throws CouponSystemException {

		Connection con = pool.getConnection();
		Company company = null;

		try {
			String sqlForPstmt = "SELECT * FROM company WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				company = new Company(id, name, password, email);
				rs.close();
			} else {
				rs.close();
				CouponSystemException coupSyEx = new CouponSystemException("Company id not found");
				throw coupSyEx;
			}

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get company id: " + id + "failed", e);
			throw coupSyEx;

		} finally {
			if (con != null) {
				pool.returnCon(con);
			}

		}
		return company;
	}

	/**
	 * This method return company by name and when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Company getCompany(String name) throws CouponSystemException {

		Connection con = pool.getConnection();
		Company company = null;

		try {
			String sqlForPstmt = "SELECT * FROM company WHERE name = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				long id = rs.getLong(1);
				String password = rs.getString(3);
				String email = rs.getString(4);
				company = new Company(id, name, password, email);
				rs.close();
			} else {
				rs.close();
				CouponSystemException coupSyEx = new CouponSystemException("Company name not found");
				throw coupSyEx;

			}

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get company by name : " + name + "failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return company;

	}

	/**
	 * This method return all companies and when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {

		Connection con = pool.getConnection();
		Collection<Company> companies = new ArrayList<>();

		try {
			String sqlForPstmt = "SELECT * FROM company";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			ResultSet rs = pstmt.executeQuery();
			boolean b = rs.next();
			while (b) {
				long id = rs.getLong(1);
				companies.add(getCompany(id));
				b = rs.next();
			}
			rs.close();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get all companies failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return companies;
	}

	/**
	 * This method return coupon by company and when he finished he returns the
	 * connection to the pool.
	 * 
	 * @throws SQLException
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException, SQLException {

		Connection con = pool.getConnection();
		Collection<Coupon> allCoupons = new ArrayList<>();
		CouponDbDao coupDbDao = new CouponDbDao();

		try {
			String sqlForPstmt = "SELECT * FROM companyCoupon WHERE compId = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, company.getId());
			ResultSet rs = pstmt.executeQuery();
			boolean b = rs.next();
			while (b) {
				long id = rs.getLong(2);
				allCoupons.add(coupDbDao.getCoupon(id));
				b = rs.next();
			}
			rs.close();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Get coupons failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return allCoupons;

	}

	/**
	 * This method allow to companies do login to coupon system and return true
	 * or false, When he finished he returns the connection to the pool.
	 */
	@Override
	public boolean login(String compName, String password) throws CouponSystemException {

		Connection con = pool.getConnection();
		boolean b = false;

		try {
			String sqlForPstmt = "SELECT * FROM Company WHERE name = ? AND password = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setString(1, compName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			b = rs.next();
			rs.close();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Company login failed", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return b;

	}
}
