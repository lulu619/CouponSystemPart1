package CouponSystem;

import java.sql.SQLException;

import Core.Beans.ClientType;
import Core.dbDao.CompanyDbDao;
import Core.dbDao.CustomerDbDao;
import Core.exceptions.CouponSystemException;
import Facade.CouponClientFacade;
import FacadeDAO.AdminFacade;
import FacadeDAO.CompanyFacade;
import FacadeDAO.CustomerFacade;
import Threads.DailyCouponExpirationTask;
import db.pool.ConnectionPool;

/**
 * This class Used as the basis for coupon system and allows different clients
 * to connect and use. when we get instance of coupon system , the following
 * method also will be execute: loading dao and Daily Coupon Expiration Task .
 */
public class CouponSystem {

	private static CouponSystem instance = null;

	private CompanyDbDao compDbDao;
	private CustomerDbDao custDbDao;

	private DailyCouponExpirationTask task;

	private CouponSystem() throws CouponSystemException {
		try {
			task = new DailyCouponExpirationTask();
			Thread t = new Thread(task);
			t.start();

			compDbDao = new CompanyDbDao();
			custDbDao = new CustomerDbDao();
		} catch (CouponSystemException | SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Failed to loaded dao and daily coupons task");
			try {
				throw coupSyEx;
			} catch (CouponSystemException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Check if coupon system not used, if coupon system equal to null we get
	 * instance of coupon system.
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	/**
	 * This method allow to client to make login with user name, password, and
	 * client type, he checked what kind of client tries to make a connection
	 * and if correct data feeder, allows to connect to coupon system.
	 * 
	 * @param name
	 * @param password
	 * @param client
	 * @return
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public CouponClientFacade login(String name, String password, ClientType client)
			throws CouponSystemException, SQLException {

		try {
			if (client.equals(ClientType.COMPANY)) {
				if (compDbDao.login(name, password)) {
					return new CompanyFacade(compDbDao.getCompany(name));
				}
				System.out.println("Company login successfully");
			} else if (client.equals(ClientType.CUSTOMER)) {
				if (custDbDao.login(name, password)) {
					return new CustomerFacade(custDbDao.getCustomer(name));

				}
			} else if (name.equals("admin") && password.equals("1234") && client.equals(ClientType.ADMIN)) {
				System.out.println("you are logged in as admin");
				return new AdminFacade();
			}

		} catch (CouponSystemException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Login failed");
			throw coupSyEx;
		}
		return null;
	}

	/**
	 * This method call to method closeAllCon, all the connection returns to
	 * pool ,stop daily task and coupon system shut down.
	 * 
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public void shutDown() throws CouponSystemException, SQLException {

		try {
			ConnectionPool.getInstance().closeAllCon();
		} catch (CouponSystemException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Failed to close all connections");
			throw coupSyEx;
		}
	}

}