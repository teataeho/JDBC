package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JdbcSelect2 {

	public static void main(String[] args) {
		
		/*
		 사용자에게 Scanner를 이용하여 job_id를 입력받습니다.
		 입력받은 job_id에 해당하는 사람들의 first_name, salary를
		 콘솔창에 출력해 주세요. (employees 테이블 사용.)
		 조회된 내용이 없다면 조회 결과가 없다고 출력해 주세요.
		 */
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("조회할 job_id를 입력해 주세요: ");
		String jobId = sc.next();
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "hr";
		String upw = "hr";
		
		String sql = "SELECT first_name, salary FROM employees "
				+ "WHERE job_id = '" + jobId + "'";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				String firstName = rs.getString("first_name");
				int salary = rs.getInt("salary");				
				System.out.println("# 이름: " + firstName + "\n# 급여: " + salary + "달러");
				System.out.println("------------------");
			}
			if(count == 0) {
				System.out.println("조회 결과가 없습니다.");
			} else {
				System.out.println("조회된 행의 개수: " + count + "개");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
				sc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
