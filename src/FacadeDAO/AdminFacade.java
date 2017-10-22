package FacadeDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.Beans.Customer;
import Core.dbDao.CompanyDbDao;
import Core.dbDao.CustomerDbDao;
import Core.dbDao.JoinTableDbDao;
import Core.exceptions.CouponSystemException;
import Facade.CouponClientFacade;

/**
 * This class defines the actions that administrator can make. Actions that
 * administrator can perform: create company, delete company, update company,
 * get company by id, get all companies, create customer, delete customer,
 * update customer, get customer by id, get all customers.
 *
 */
public class AdminFacade implements CouponClientFacade {

	private CompanyDbDao compDbDao;
	private CustomerDbDao custDbDao;
	private JoinTableDbDao joinDbDao;

	// CTOR
	public AdminFacade() throws CouponSystemException, SQLException {
		this.compDbDao = new CompanyDbDao();
		this.custDbDao = new CustomerDbDao();

	}

	/**
	 * Checks if there is a company name , if the company name is already used ,
	 * He will not allow to create a new company. If no such a name exists , The
	 * administrator will be able to create a new company.
	 * 
	 * @throws CouponSystemException
	 */
	public void createCompany(Company company) throws CouponSystemException {

		compDbDao.createCompany(company);
		System.out.println("Create new company " + company + "successfully");
	}

	/**
	 * Delete company from company table and deletes all the company coupons
	 * belonging to her and her customers.
	 * 
	 * @param company
	 * @throws CouponSystemException
	 * @throws SQLException
	 */
	public void deleteCompany(Company company) throws CouponSystemException, SQLException {

		Company check = getCompany(company.getId());
		if (check != null) {
			Collection<Coupon> compCoupons = (ArrayList<Coupon>) this.compDbDao.getCoupons(check);
			for (Coupon coupon : compCoupons) {
				joinDbDao.deleteCompanyCoupon(coupon);
				joinDbDao.deleteCustomerCoupon(coupon);
			}
		}
		compDbDao.deleteCompany(company);
		System.out.println("Delete company " + company + "successfully");

	}

	/**
	 * Update a new data of company to the company table.
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		Company comp = compDbDao.getCompany(company.getId());
		comp.setEmail(company.getEmail());
		comp.setId(company.getId());
		comp.setPassword(company.getPassword());
		compDbDao.updateCompany(comp);
	}

	/**
	 * Returns company from company table by id.
	 * 
	 * @throws CouponSystemException
	 */
	public Company getCompany(long id) throws CouponSystemException {
		return compDbDao.getCompany(id);
	}

	/**
	 * returns all companies.
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		return compDbDao.getAllCompanies();
	}

	/**
	 * Checks if there is a customer name , if the customer name is already used
	 * , He will not allow to create a new customer. If such a name exists , The
	 * administrator will be able to create a new customer.
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void createCustomer(Customer customer) throws CouponSystemException {

		custDbDao.createCustomer(customer);
		System.out.println("Create customer " + customer + " successfully");
	}

	/**
	 * Delete customer from customers table and deletes all the coupons
	 * belonging to this customer.
	 * 
	 * @throws SQLException
	 */
	public void deleteCustomer(Customer customer) throws CouponSystemException, SQLException {
		Customer check = getCustomer(customer.getId());
		if (check != null) {
			Collection<Coupon> custCoupons = (ArrayList<Coupon>) this.custDbDao.getCoupons(check);
			for (Coupon coupon : custCoupons) {
				joinDbDao.deleteCustomerCoupon(coupon);
			}
		}
		custDbDao.deleteCustomer(customer);
	}

	/**
	 * Update a new customer data to the customer table.
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Customer cust = custDbDao.getCustomer(customer.getId());
		cust.setId(customer.getId());
		cust.setPassword(customer.getPassword());
		custDbDao.updateCustomer(cust);
	}

	/**
	 * Returns customer from customer table by id.
	 * 
	 * @param id
	 * @return
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(long id) throws CouponSystemException {
		return custDbDao.getCustomer(id);
	}

	/**
	 * Returns all customers from customer table.
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		return custDbDao.getAllCustomers();
	}

}
