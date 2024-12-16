package bookmall.vo;

public class BookVo {
	private Long no;
	private Long category_no;
	private String title;
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public BookVo(String title, int price) {
		this.title = title;
		this.price = price;
	}
	
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Long getCategoryNo() {
		return category_no;
	}

	public void setCategoryNo(Long category_no) {
		this.category_no = category_no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
