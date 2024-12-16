package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {

	public void insert(CartVo vo) {
		try (Connection conn0 = getConnection();
				PreparedStatement pstmt0 = conn0.prepareStatement("select title from book where id = ?");) {
			pstmt0.setLong(1, vo.getBookNo());
			ResultSet rs0 = pstmt0.executeQuery();
			vo.setBookTitle(rs0.next() ? rs0.getString(1) : null);
			rs0.close();
			try (Connection conn = getConnection();
					PreparedStatement pstmt1 = conn.prepareStatement("insert into cart values (null, ?, ?, ?, ?)");
					PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");) {
				pstmt1.setLong(1, vo.getUserNo());
				pstmt1.setLong(2, vo.getBookNo());
				pstmt1.setInt(3, vo.getQuantity());
				pstmt1.setString(4, vo.getBookTitle());
				pstmt1.executeUpdate();

				ResultSet rs1 = pstmt2.executeQuery();
				vo.setNo(rs1.next() ? rs1.getLong(1) : null);

				rs1.close();

			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public void deleteByUserNoAndBookNo(Long user_no, Long book_no) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("delete from cart where user_id = ? and book_id = ?");) {
			pstmt.setLong(1, user_no);
			pstmt.setLong(2, book_no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public List<CartVo> findByUserNo(Long no) {
		List<CartVo> result = new ArrayList<>();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select * from cart where user_id = ?");) {
			pstmt.setLong(1, no);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				Long user_id = rs.getLong(2);
				Long book_id = rs.getLong(3);
				int quantity = rs.getInt(4);
				String book_title = rs.getString(5);

				CartVo vo = new CartVo();
				vo.setNo(id);
				vo.setUserNo(user_id);
				vo.setBookNo(book_id);
				vo.setQuantity(quantity);
				vo.setBookTitle(book_title);

				result.add(vo);
			}
			rs.close();
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
