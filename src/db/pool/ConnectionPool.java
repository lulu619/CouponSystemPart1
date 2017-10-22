package db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Core.exceptions.CouponSystemException;

/**
 *This class has a number of connections and allows the client to obtain a connection by methods.
 */
public class ConnectionPool {

	private Set<Connection> connections = new HashSet<>();
	private Set<Connection> allConnections = new HashSet<>();
	private String url = "jdbc:derby://localhost:1527/coupon_sys";
	private String driverName = "org.apache.derby.jdbc.ClientDriver";
	private static ConnectionPool instance = null;
	private boolean giveCon = true;

	/**
	 * Create 10 connections to pool.
	 */

	private ConnectionPool() throws CouponSystemException, SQLException {

		try {
			Class.forName(driverName);

			for (int i = 0; i < 10; i++) {
				Connection con = DriverManager.getConnection(url);
				url += ";create=true";
				connections.add(con);
				allConnections.add(con);
			}

		} catch (ClassNotFoundException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Failed to create connections.");
			throw coupSyEx;
		}
	}

	/**
	 * Return instance of connection pool.
	 */

	public static ConnectionPool getInstance() throws CouponSystemException, SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/**
	 * Return available connection, else waits to available connection.
	 */

	public synchronized Connection getConnection() throws CouponSystemException {
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				CouponSystemException coupSyEx = new CouponSystemException("Failed to retrieve connection.");
				throw coupSyEx;
			}
		}
		if (giveCon) {
			Iterator<Connection> it = connections.iterator();
			Connection con = it.next();
			it.remove();
			return con;
		} else {
			CouponSystemException coupSyEx = new CouponSystemException("Coupon system shutting down.");
			throw coupSyEx;
		}
	}

	/**
	 * Add an available connection to the pool.
	 */

	public synchronized void returnCon(Connection con) {
		connections.add(con);
		notify();
	}

	/**
	 * Close all connection.
	 */
	public synchronized void closeAllCon() throws CouponSystemException {
		giveCon = false;
		while (allConnections.size() != connections.size()) {
			try {
				wait();
			} catch (InterruptedException e) {
				CouponSystemException coupSyEx = new CouponSystemException("Cannot close all connections.");
				throw coupSyEx;
			}
		}
		Iterator<Connection> it = allConnections.iterator();
		while (it.hasNext()) {
			Connection currentElement = it.next();
			try {
				currentElement.close();
			} catch (Exception e) {
				CouponSystemException coupSyEx = new CouponSystemException("closing all connections failed.");
				throw coupSyEx;
			}
		}

	}
}
