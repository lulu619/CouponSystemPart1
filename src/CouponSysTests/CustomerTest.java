package CouponSysTests;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

import Core.Beans.ClientType;
import Core.Beans.Coupon;
import Core.Beans.CouponType;
import Core.exceptions.CouponSystemException;
import CouponSystem.CouponSystem;
import FacadeDAO.CustomerFacade;

public class CustomerTest {

	public static void main(String[] args) throws CouponSystemException, ParseException {

		try {

			CouponSystem cs = CouponSystem.getInstance();
			CustomerFacade cf = (CustomerFacade) cs.login("Ben", "123123", ClientType.CUSTOMER);
			 Coupon c = cf.purchaseCoupon(7);
			 System.out.println(c);
			//Collection<Coupon> AllPurchasedCoupons = cf.getAllPurchasedCoupons();
			// System.out.println("All coupons purchases : " + AllPurchasedCoupons);
			// Collection<Coupon> allCouponsByType =
			// cf.getAllPurchasedCouponsByType(CouponType.FOOD);
			// System.out.println(allCouponsByType);
//			Collection<Coupon> allCouponsByPrice = cf.getAllPurchasedCouponByPrice(15);
//			System.out.println(allCouponsByPrice);

			
			// cs.shutDown();

		} catch (SQLException | CouponSystemException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Customer test failed ", e);
			throw coupSyEx;
		}
	}

}
