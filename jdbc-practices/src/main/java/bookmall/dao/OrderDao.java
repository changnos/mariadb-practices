package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {

	public void insert(OrderVo vo) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into orders values (null, ?, ?, ?, ?, ?)");
				PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {
			pstmt1.setString(1, vo.getNumber());
			pstmt1.setInt(2, vo.getPayment());
			pstmt1.setString(3, vo.getShipping());
			pstmt1.setString(4, vo.getStatus());
			pstmt1.setLong(5, vo.getUserNo());
			pstmt1.executeUpdate();

			ResultSet rs = pstmt2.executeQuery();
			vo.setNo(rs.next() ? rs.getLong(1) : null);
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public void insertBook(OrderBookVo vo) {
		try (Connection conn0 = getConnection();
				PreparedStatement pstmt0 = conn0.prepareStatement("select title from book where id = ?");) {
			pstmt0.setLong(1, vo.getBookNo());
			ResultSet rs0 = pstmt0.executeQuery();
			vo.setBookTitle(rs0.next() ? rs0.getString(1) : null);
			rs0.close();
			try (Connection conn = getConnection();
					PreparedStatement pstmt1 = conn.prepareStatement("insert into orders_book values (?, ?, ?, ?, ?)");
					PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {

				pstmt1.setLong(1, vo.getBookNo());
				pstmt1.setLong(2, vo.getOrderNo());
				pstmt1.setInt(3, vo.getQuantity());
				pstmt1.setInt(4, vo.getPrice());
				pstmt1.setString(5, vo.getBookTitle());
				pstmt1.executeUpdate();

				pstmt2.executeQuery();
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public OrderVo findByNoAndUserNo(Long no, Long user_no) {
		OrderVo vo = null;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select * from orders where id = ? and user_id = ?");) {
			pstmt.setLong(1, no);
			pstmt.setLong(2, user_no);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String number = rs.getString(2);
				int payment = rs.getInt(3);
				String shipping = rs.getString(4);
				String status = rs.getString(5);
				Long user_id = rs.getLong(6);

				vo = new OrderVo();
				vo.setNo(id);
				vo.setNumber(number);
				vo.setPayment(payment);
				vo.setShipping(shipping);
				vo.setStatus(status);
				vo.setUserNo(user_id);

			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return vo;
	}

	public List<OrderBookVo> findBooksByNoAndUserNo(Long order_no, Long user_no) {
		List<OrderBookVo> result = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select * from orders_book where orders_id = ?");) {
			pstmt.setLong(1, order_no);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long book_id = rs.getLong(1);
				Long orders_id = rs.getLong(2);
				int quantity = rs.getInt(3);
				int price = rs.getInt(4);
				String book_title = rs.getString(5);

				OrderBookVo vo = new OrderBookVo();
				vo.setOrderNo(orders_id);
				vo.setBookNo(book_id);
				vo.setQuantity(quantity);
				vo.setPrice(price);
				vo.setBookTitle(book_title);

				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public void deleteBooksByNo(Long no) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from orders_book where orders_id = ?");) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public void deleteByNo(Long no) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from orders where id = ?");) {
			pstmt.setLong(1, no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
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
