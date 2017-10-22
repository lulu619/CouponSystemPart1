package CouponSysTests;

import java.sql.SQLException;
import java.util.Collection;

import Core.Beans.ClientType;
import Core.Beans.Company;
import Core.Beans.Customer;
import Core.dbDao.CustomerDbDao;
import Core.exceptions.CouponSystemException;
import CouponSystem.CouponSystem;
import FacadeDAO.AdminFacade;

public class AdminTest {

	public static void main(String[] args) throws CouponSystemException {

		Company comp = new Company(1, "Oracle", "123456", "Oracle@couponsystem.net");
		Customer cust1 = new Customer(1, "Ben", "123123");

		try {
			CouponSystem cs = CouponSystem.getInstance();
			AdminFacade f = (AdminFacade) cs.login("admin", "1234", ClientType.ADMIN);
//			f.createCompany(comp);
			// f.deleteCompany(comp1);
			// comp1.setPassword("12345678");
			// f.updateCompany(comp1);
			// Company c = f.getCompany(comp1.getId());
			// System.out.println(c);
//			 Collection<Company> allCompanies = f.getAllCompanies();
//			 System.out.println(allCompanies);
//			f.createCustomer(cust1);
			 f.deleteCustomer(cust1);
			// cust1.setPassword("102030");
			 //f.updateCustomer(cust1);
			// Customer c = f.getCustomer(cust1.getId());
			// System.out.println(c);
			// Collection<Customer> allCustomers = f.getAllCustomers();
			// System.out.println(allCustomers);
			

			//cs.shutDown();

		} catch (CouponSystemException | SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Admin Test Failed");
			throw coupSyEx;
		}

	}

}
