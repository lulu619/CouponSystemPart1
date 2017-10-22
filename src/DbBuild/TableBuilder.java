package DbBuild;

import java.io.File;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Core.exceptions.CouponSystemException;

/**
 * This class loading the drivers and then creates tables in the database coupon
 * system.
 */
public class TableBuilder {

	public static void main(String[] args) throws CouponSystemException {

		File driverFile = new File("files/driverName");
		File dbUrlFile = new File("files/dbUrl");

		try (Scanner sc = new Scanner(driverFile);) {
			String driverName = sc.nextLine();
			Class<?> c1 = Class.forName(driverName);
			System.out.println("Driver Class Loaded");
		} catch (FileNotFoundException | ClassNotFoundException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Failed to loading the drivers");
			throw coupSyEx;
		}
		Connection con = null;
		try (Scanner sc = new Scanner(dbUrlFile);) {
			String url = sc.nextLine();
			con = DriverManager.getConnection(url);
			createCompanyTable(con);
			createCustomerTable(con);
			createCouponTable(con);
			createCustomerCouponTable(con);
			createCompanyCouponTable(con);
			JoinTables(con);

		} catch (FileNotFoundException | SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Loading failed");
			throw coupSyEx;
		} finally {
			if (con != null) {

				try {
					con.close();
				} catch (SQLException e) {
					CouponSystemException coupSyEx = new CouponSystemException("close connection failed");
					throw coupSyEx;
				}
			}

		}
	}

	/**
	 * Create a table of companies in a database.
	 */
	private static void createCompanyTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE company(id BIGINT PRIMARY KEY, name VARCHAR(50) NOT NULL UNIQUE , password VARCHAR(50), email VARCHAR(50))";
		Statement stmt = con.createStatement();
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	/**
	 * Create a customer table in a database.
	 */
	private static void createCustomerTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE customer(id BIGINT PRIMARY KEY, custName VARCHAR(50) NOT NULL UNIQUE , password VARCHAR(50))";
		Statement stmt = con.createStatement();
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	/**
	 * Create a coupons table in a database.
	 */
	private static void createCouponTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE coupon(id BIGINT PRIMARY KEY , title VARCHAR(100) NOT NULL UNIQUE  , startDate DATE , endDate DATE , amount INTEGER , type VARCHAR(50) , message VARCHAR(100),price DOUBLE , image VARCHAR(50))";
		Statement stmt = con.createStatement();
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	/**
	 * Create a customers coupons table in a database.
	 */
	private static void createCustomerCouponTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE customerCoupon(custId BIGINT , couponId BIGINT NOT NULL)";
		Statement stmt = con.createStatement();
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	/**
	 * Create a companies coupons table in a database.
	 */
	private static void createCompanyCouponTable(Connection con) throws SQLException {
		String sql = "CREATE TABLE companyCoupon(compId BIGINT , coupId BIGINT)";
		Statement stmt = con.createStatement();
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}

	/**
	 * Summarized the coupon system tables and the connection between them.
	 */
	private static void JoinTables(Connection con) throws CouponSystemException, SQLException {
		ResultSet rs;
		Statement stmt = con.createStatement();
		String sql = "SELECT coupon.id , companyCoupon.compId FROM coupon INNER JOIN companyCoupon ON coupon.id=companyCoupon.compId";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
		sql = "SELECT coupon.id , customerCoupon.couponId FROM coupon INNER JOIN customerCoupon ON coupon.id=customerCoupon.couponId";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
		sql = "SELECT company.id , companyCoupon.compId FROM company INNER JOIN companyCoupon ON company.id=companyCoupon.compId";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
		sql = "SELECT customer.id, customerCoupon.custId FROM customer INNER JOIN customerCoupon ON customer.id=customerCoupon.custId";
		System.out.println(sql);
		rs = stmt.executeQuery(sql);
	}

}
