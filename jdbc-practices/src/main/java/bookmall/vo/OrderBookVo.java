package bookmall.vo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderBookVo {
	private Long order_no;
	private Long book_no;
	private int quantity;
	private int price;

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
		String result = "";
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select title from book where id = ?");) {
			pstmt.setLong(1, this.book_no);

			ResultSet rs = pstmt.executeQuery();
			result = (rs.next() ? rs.getString(1) : "");
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.27:3306/bookmall";
			conn = DriverManager.getConnection(url, "bookmall", "bookmall");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return conn;
	}
}
