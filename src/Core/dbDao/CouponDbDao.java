package Core.dbDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Core.Beans.Coupon;
import Core.Beans.CouponType;
import Core.dao.CouponDao;
import Core.exceptions.CouponSystemException;
import db.pool.ConnectionPool;

/**
 * Maintains and updates the coupons table data in the coupon system database.
 * This class defined actions that allow : create coupon, delete coupon, update
 * coupon, get coupon, get all coupons, get all coupons by type.
 */
public class CouponDbDao implements CouponDao {

	private ConnectionPool pool;

	public CouponDbDao() throws CouponSystemException, SQLException {
		super();
		pool = ConnectionPool.getInstance();
	}

	/**
	 * Create a new coupon and update the coupon table , when he finished he
	 * returns the connection to the pool.
	 */
	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "INSERT INTO coupon VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);

			java.sql.Date startDate = new java.sql.Date(coupon.getStartDate().getTime());

			pstmt.setLong(1, coupon.getId());
			pstmt.setString(2, coupon.getTitle());
			pstmt.setDate(3, startDate);
			pstmt.setDate(4, coupon.getEndDate());
			pstmt.setInt(5, coupon.getAmount());
			pstmt.setString(6, coupon.getType().toString());
			pstmt.setString(7, coupon.getMessage());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Failed to create new coupon", e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * This method delete coupon from coupon table in the coupon system date
	 * base, when he finished he returns the connection to the pool.
	 */
	@Override
	public void deleteCoupon(Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "DELETE FROM coupon WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Delete coupon " + coupon.getTitle() + "failed",
					e);
			throw coupSyEx;

		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
	}

	/**
	 * Update a new data of coupon in the coupon system data base. when he
	 * finished he returns the connection to the pool.
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		Connection con = pool.getConnection();

		try {
			String sqlForPstmt = "UPDATE coupon SET title = ?, startDate = ?, endDate = ?, amount = ?, type = ?, message = ?, "
					+ "price = ?, image = ? WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);

			java.sql.Date couponStartDate = new java.sql.Date(coupon.getStartDate().getTime());
			java.sql.Date couponEndDate = new java.sql.Date(coupon.getEndDate().getTime());

			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, couponStartDate);
			pstmt.setDate(3, couponEndDate);
			pstmt.setInt(4, coupon.getAmount());
			pstmt.setString(5, coupon.getType().toString());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setDouble(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.setLong(9, coupon.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Update coupon" + coupon.getTitle() + "failed",
					e);
			throw coupSyEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

	}

	/**
	 * This method returns coupon by id , when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Coupon getCoupon(Long id) throws CouponSystemException {

		Connection con = pool.getConnection();
		Coupon coupon = null;

		try {
			String sqlForPstmt = "SELECT * FROM coupon WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				String title = rs.getString(2);
				java.sql.Date couponStartDate = rs.getDate(3);
				java.sql.Date couponEndDate = rs.getDate(4);
				int amount = rs.getInt(5);
				CouponType type = CouponType.valueOf(rs.getString(6));
				String message = rs.getString(7);
				double price = rs.getDouble(8);
				String image = rs.getString(9);

				coupon = new Coupon(id, title, couponStartDate, couponEndDate, amount, type, message, price, image);
				rs.close();
			} else {
				rs.close();
				CouponSystemException coupSyEx = new CouponSystemException("Coupon id " + id + " not found");
				throw coupSyEx;
			}

		} catch (CouponSystemException | SQLException e) {
			CouponSystemException coupSysEx = new CouponSystemException("Get coupon " + coupon.getTitle() + "failed",
					e);
			throw coupSysEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}

		return coupon;

	}

	/**
	 * This method returns all coupons , when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Collection<Coupon> getAllCoupon() throws CouponSystemException {

		Connection con = pool.getConnection();
		Collection<Coupon> allCoupons = new ArrayList<>();

		try {
			String sqlForPstmt = "SELECT * FROM coupon";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			ResultSet rs = pstmt.executeQuery();
			boolean b = rs.next();
			while (b) {
				long id = rs.getLong(1);
				allCoupons.add(getCoupon(id));
				b = rs.next();
			}
			rs.close();
		} catch (SQLException e) {
			CouponSystemException couponSysEx = new CouponSystemException("Failed to get all the coupons", e);
			throw couponSysEx;
		} finally {
			if (con != null) {
				pool.returnCon(con);
			}
		}
		return allCoupons;

	}

	/**
	 * This method returns coupon by type , when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException {

		Collection<Coupon> allCoupons = getAllCoupon();
		Collection<Coupon> couponByType = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (couponType.equals(coupon.getType())) {
				couponByType.add(coupon);
			}

		}

		return couponByType;

	}

	/**
	 * This method returns coupon by price , when he finished he returns the
	 * connection to the pool.
	 */
	@Override
	public Collection<Coupon> getCouponByPrice(double price) throws CouponSystemException {

		Collection<Coupon> allCoupons = getAllCoupon();
		Collection<Coupon> couponByPrice = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (coupon.getPrice() <= price) {
				couponByPrice.add(coupon);
				return couponByPrice;
			}

		}
		return null;
	}

	public boolean chackPurCoupon(long coupId, long custId) throws CouponSystemException {

		Connection con = pool.getConnection();
		boolean b = false;
		

		try {
			String sqlForPstmt = "SELECT * FROM customerCoupon WHERE custId = ? AND couponId = ?";
			PreparedStatement pstmt = con.prepareStatement(sqlForPstmt);
			pstmt.setLong(1, custId);
			pstmt.setLong(2, coupId);
			ResultSet rs = pstmt.executeQuery();
			b = rs.next();
			rs.close();


		} catch (SQLException e) {
			CouponSystemException couponSysEx = new CouponSystemException("This Coupon can't purchased", e);
			throw couponSysEx;
		} finally {

			if (con != null) {
				pool.returnCon(con);

			}

		}

		return b;
	}
}
