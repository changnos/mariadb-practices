package bookmall.vo;

public class OrderBookVo {
	private Long order_no;
	private Long book_no;
	private int quantity;
	private int price;
	private String book_title;

	public Long getOrderNo() {
		return order_no;
	}

	public void setOrderNo(Long order_no) {
		this.order_no = order_no;
	}

	public Long getBookNo() {
		return book_no;
	}

	public void setBookNo(Long book_no) {
		this.book_no = book_no;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBookTitle() {
		return book_title;
	}

	public void setBookTitle(String book_title) {
		this.book_title = book_title;
	}

}
