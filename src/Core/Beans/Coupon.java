package Core.Beans;

import java.sql.Date;

/**
 * Coupon class, presenter attributes ,constructors ,and getters and setters.
 */
public class Coupon {

	// Attributes
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	// CTOR
	public Coupon() {

	}

	// CTOR
	public Coupon(long id) {
		this.id = id;
	}

	// CTOR
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	// set id
	public void setId(long id) {
		this.id = id;
	}

	// set title
	public void setTitle(String title) {
		this.title = title;
	}

	// set start date
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	// set amount
	public void setAmount(int amount) {
		this.amount = amount;
	}

	// set type
	public void setType(CouponType type) {
		this.type = type;
	}

	// set message
	public void setMessage(String message) {
		this.message = message;
	}

	// set image
	public void setImage(String image) {
		this.image = image;
	}

	// get end date
	public Date getEndDate() {
		return endDate;
	}

	// set end date
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// get coupon price
	public double getPrice() {
		return price;
	}

	// set coupon price
	public void setPrice(double price) {
		this.price = price;
	}

	// get coupon id
	public long getId() {
		return id;
	}

	// get coupon title
	public String getTitle() {
		return title;
	}

	// get start date
	public Date getStartDate() {
		return startDate;
	}

	// get coupon amount
	public int getAmount() {
		return amount;
	}

	// get coupon type
	public CouponType getType() {
		return type;
	}

	// get coupon message
	public String getMessage() {
		return message;
	}

	// get coupon image
	public String getImage() {
		return image;
	}

	// toString
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
