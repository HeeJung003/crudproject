package com.shinhan.day16;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//CRUD : create read update delete
//read : select
public class CRUDTest {
	
	//dbutil만들고 사용
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				select department_id, max(salary), min(salary)
				from employees
				group by department_id
				having max(salary) <> min(salary)
				""";		
		
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement(); 
			rs = st.executeQuery(sql); //문장 실행됨
			while(rs.next()) {
				int a = rs.getInt(1);
				int b = rs.getInt(2);
				int c = rs.getInt(3);
				System.out.println(a + " \t" + b + "\t" + c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
	}
	
	
	//exception 처리 -> X
	public static void f_2() {
		String url="jdbc:oracle:thin:@localhost:1521:xe", userid = "hr", userpass="hr";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				select department_id, count(*)
				from employees
				group by department_id
				having count(*) >=5
				""";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, userid, userpass);
			st = conn.createStatement();
			rs = st.executeQuery(sql); //rs는 표와 비슷함
			while(rs.next()){
				int deptid = rs.getInt(1);
				int cnt = rs.getInt(2);
				System.out.println("부서코드 : " + deptid + " \t 인원수 : " + cnt);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	//exception 던짐
	public static void f_1() throws ClassNotFoundException, SQLException {
		//1. jdbc driver 준비 (class path) > sql 밑에
		//2. jdbc driver load
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("load 성공");
		//3. connection
		String url="jdbc:oracle:thin:@localhost:1521:xe", userid = "hr", userpass="hr";
		Connection conn = DriverManager.getConnection(url, userid, userpass);
		System.out.println("3. 성공");
		//4. sql문 보낼 통로 얻기
		Statement st = conn.createStatement();
		System.out.println("4. 성공");
		
		//5. sql만들기, 실행
		String sql = """
				select * 
				from employees
				where department_id = 80	
				""";
		
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			int empid = rs.getInt("employee_id");
			String fname = rs.getString("First_name");
			Date hdate = rs.getDate("hire_date");
			double comm = rs.getDouble("commission_pct");
			
			System.out.printf("직원번호 : %d, 이름  %s, 날짜 %s, comm : %3.1f\n", empid, fname, hdate, comm);
		}
		rs.close();
		st.close();
		conn.close();
	}

}
