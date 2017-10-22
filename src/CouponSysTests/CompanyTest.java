package CouponSysTests;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import Core.Beans.ClientType;
import Core.Beans.Company;
import Core.Beans.Coupon;
import Core.Beans.CouponType;
import Core.exceptions.CouponSystemException;
import CouponSystem.CouponSystem;
import FacadeDAO.CompanyFacade;

public class CompanyTest {

	public static void main(String[] args) throws CouponSystemException, SQLException, ParseException {

		String startDate = "15-10-2017";
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date date1 = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());

		String endDate = "15-10-2020";
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-mm-yyyy");
		java.util.Date date2 = sdf2.parse(endDate);
		java.sql.Date sqlEndDate = new java.sql.Date(date2.getTime());

		Coupon coupon1 = new Coupon(1, "FastFood", sqlStartDate, sqlEndDate, 10, CouponType.FOOD, "Come and eat the best food ever", 8, "image");
		Coupon coupon2 = new Coupon(2, "TheBestFood", sqlStartDate, sqlEndDate, 10, CouponType.FOOD, "Come and eat the best food ever", 11, "image");
		Coupon coupon3 = new Coupon(3, "ComeAndEat", sqlStartDate, sqlEndDate, 10, CouponType.FOOD, "Come and eat the best food ever", 9, "image");
		Coupon coupon4 = new Coupon(4, "YesOrNo", sqlStartDate, sqlEndDate, 10, CouponType.FOOD, "Come and eat the best food ever", 6, "image");
		Coupon coupon5 = new Coupon(5, "Segev Kitchen Garden", sqlStartDate, sqlEndDate, 10, CouponType.RESTAURANTS, "Come and eat the best food ever", 25, "image");
		Coupon coupon6 = new Coupon(6, "Carmel Forest", sqlStartDate, sqlEndDate, 10, CouponType.CAMPING, "Amazing place", 19, "image");
		Coupon coupon7 = new Coupon(7, " Merom Golan ", sqlStartDate, sqlEndDate, 10, CouponType.CAMPING, "Come and rest in a quiet place", 22, "image");
		Coupon coupon8 = new Coupon(8, " Anita ", sqlStartDate, sqlEndDate, 10, CouponType.CAMPING, "The most spectacular scenery you will see", 20, "image");
		Coupon coupon9 = new Coupon(9, "Nike", sqlStartDate, sqlEndDate, 10, CouponType.SPORTS, "Shirts Pants and sneakers at cheap prices", 20, "image");
		Coupon coupon10 = new Coupon(10, "Mega Sport", sqlStartDate, sqlEndDate, 10, CouponType.SPORTS, "Tickets to all games at cheap prices", 15, "image");
		Coupon coupon11= new Coupon(12, "Sportek", sqlStartDate, sqlEndDate, 10, CouponType.SPORTS, "fo2od", 11, "image");
		

		try {

			CouponSystem cs = CouponSystem.getInstance();
			CompanyFacade cf = (CompanyFacade) cs.login("Oracle", "123456", ClientType.COMPANY);

			
   			 cf.createCoupon(coupon1);
			 cf.createCoupon(coupon2);
			 cf.createCoupon(coupon3);
			 cf.createCoupon(coupon4);
			 cf.createCoupon(coupon5);
			 cf.createCoupon(coupon6);
			 cf.createCoupon(coupon7);
			 cf.createCoupon(coupon8);
			 cf.createCoupon(coupon9);
			 cf.createCoupon(coupon10);
			 cf.createCoupon(coupon11);
			//cf.deleteCoupon(coupon2);
			// coupon.setPrice(15);
			// cf.updateCoupon(coupon);
//			 Coupon coup = cf.getCoupon(2);
//			 System.out.println(coup);
			// Company c = cf.getCompany(123456);
			// System.out.println(c);
			// Collection<Coupon>allCoupons = cf.getAllCoupon();
			// System.out.println(allCoupons);	
//			 Collection<Coupon> allCouponByType = cf.getCouponByType(CouponType.FOOD);
//			 System.out.println(allCouponByType);
//			 Collection<Coupon> allCouponByPrice = cf.getCouponByPrice(15);
//			 System.out.println(allCouponByPrice);
			 //Collection<Coupon>couponByDate = cf.getCuponByDate(sqlStartDate);
			 //System.out.println(couponByDate);
			
			//cs.shutDown();

		} catch (CouponSystemException | SQLException e) {
			CouponSystemException coupSyEx = new CouponSystemException("Company Test Failed");
			throw coupSyEx;
		}
	}

}
