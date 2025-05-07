package com.shinhan.day17;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.shinhan.day16.DBUtil;

public class CRUDTest2 {
	
	public static void main(String[] args) throws SQLException {
		//git 연동확인
		//모두 성공하면 commit, 하나라도 실패하면 rollback
		//insert
		//update
		
		Connection conn = null;
		Statement st = null; //통로 1 만들기
		Statement st2 = null; //통로 2 만들기
		String sql1 = """ 
				insert into emp1 (employee_id, first_name, last_name, email, hire_date, job_id) 
				values(2, 'aa', 'bb', 'mail', sysdate, 'it' )
				"""; //통로 1 문장
		String sql2 = """
				update emp1 set salary  = 2000 where employee_id = 198
				"""; //통로 2 문장
		
		conn = DBUtil.getConnection(); //db연결
		conn.setAutoCommit(false); //자동commit 안 할거야! 내가 하라고 하면 해 -> 밑에 if문
		
		st = conn.createStatement(); //통로 1 연결 -> insert
		int result = st.executeUpdate(sql1);
		
		st2 = conn.createStatement(); //통로 2 연결 -> update
		int result2 = st2.executeUpdate(sql2);
		
		if( result >= 1 && result2 >=1) {
			conn.commit(); //둘 다 성공하면 commit할거야
		}else {
			conn.rollback(); //둘 중 하나라도 실패하면 rollback할거야
		}
 
	}
	
	public static void f_4() throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				delete from emp2
			where employee_id = 4;
				""";
		
		conn = DBUtil.getConnection(); //db연결
		st = conn.createStatement(); //db에 갈 통로 만듦
		
		result = st.executeUpdate(sql); //지금은 insert니까 -> 자동 commit
		System.out.println(result>=1?"delete 성공": "delete 실패");
	}
	
	
	public static void f_3() throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				update emp2
			set employee_id = (
			        select employee_id
			        from emp2
			        where employee_id = 206), 
			        
			        salary = (select salary
			        from emp2 
			        where employee_id = 207)
			where employee_id = 4;
				""";
		
		conn = DBUtil.getConnection(); //db연결
		st = conn.createStatement(); //db에 갈 통로 만듦
		
		result = st.executeUpdate(sql); //지금은 insert니까 -> 자동 commit
		System.out.println((result>=1)?"update 성공": "update 실패");
	}
	
	
	
	public static void f_2() throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				insert into emp1 values(1, '홍길동', 1000);
				""";
		
		conn = DBUtil.getConnection(); //db연결
		st = conn.createStatement(); //db에 갈 통로 만듦
		
		result = st.executeUpdate(sql); //지금은 insert니까 -> 자동 commit
		System.out.println(result>=1?"insert 성공": "insert 실패");
	}
	

	public static void f_1() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				select empno, ename, job,dept.deptno, dept.dname, loc
				from emp join dept on (emp.deptno = dept.deptno)
				where emp.deptno = 10
				and job not in (
				    select job
				    from emp
				    where deptno = 30)
				""";
		
		conn = DBUtil.getConnection(); //db연결
		st = conn.createStatement(); //db에 갈 통로 만듦
		rs = st.executeQuery(sql);
		while(rs.next()) {
			String a = rs.getString(1);
			String b = rs.getString(2);
			String c = rs.getString(3);
			int d = rs.getInt(4);
			String e = rs.getString(5);
			String f = rs.getString(6);
			
			System.out.println(a + " \t" + b + " \t" + c + " \t" + d + " \t" + e + " \t"+ f);
		}
		
		DBUtil.dbDisconnect(conn, st, rs);
		
		
	}

}
