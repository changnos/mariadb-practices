package bookmall.vo;

public class CartVo {
	private Long user_no;
	private Long book_no;
	private int quantity;
	private String book_title;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private int price;

	public Long getBookNo() {
		return book_no;
	}

	public void setBookNo(Long book_no) {
		this.book_no = book_no;
	}

	public Long getUserNo() {
		return user_no;
	}

	public void setUserNo(Long user_no) {
		this.user_no = user_no;
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
