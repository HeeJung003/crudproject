package com.shinhan.emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shinhan.day16.DBUtil;

//DAO (data access object) : db에 crud 작성 (select, insert, update, delete)
public class EmpDAO { // 2
	
	//Stored procedure 실행하기(직원번호를 받아서 이메일, 급여 출력하기)
	//PreparedStatement는 sp호출 지원 안 함 -> 
	public EmpDTO execute_sp(int empid) {
		EmpDTO emp = null;
		
		Connection conn = DBUtil.getConnection();
		CallableStatement st = null; //callableStatement : sp호출 지원
		String sql = "{call sp_empInfo2(?, ?, ?)"; //sp 호출하는 문장
		
		try {
			st = conn.prepareCall(sql);
			st.setInt(1, empid); //값을 줌
			st.registerOutParameter(2, Types.VARCHAR); //값을 읽는거니까 
			st.registerOutParameter(3, Types.DECIMAL); //값을 읽는거니까 
			
			boolean result = st.execute(); //execute이 return값이 boolean임
			
			emp = new EmpDTO(); //있으면 new하고 값을 넣어		
			String email = st.getString(2);
			double salary = st.getDouble(3);
				
			emp.setEmail(email);
			emp.setSalary(salary);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return emp;
	}
	
	
	//---------------------------------------------------------------------------------------------------------

	public int empUpdateByID(EmpDTO emp) { // 삭제된 건수의 개수를 보여줄거야 int
		int result = 0;
		Connection conn = DBUtil.getConnection(); // db연결
		PreparedStatement st = null;
		String sql = """
				update employees set
				FIRST_NAME = ?,    
				LAST_NAME = ?,     
				EMAIL = ?,          
				PHONE_NUMBER = ?,   
				HIRE_DATE = ?,      
				JOB_ID = ?,         
				SALARY = ?,         
				COMMISSION_PCT = ?, 
				MANAGER_ID = ?,     
				DEPARTMENT_ID = ?  
				
				where EMPLOYEE_ID = ?
				""";
		try {
			st = conn.prepareStatement(sql); // sql문 준비한다.
			st.setInt(11, emp.getEmployee_id());
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());
			st.setDate(5, emp.getHire_date());
			st.setString(6, emp.getJob_id());
			st.setDouble(7, emp.getSalary());
			st.setDouble(8, emp.getCommission_pct());
			st.setInt(9, emp.getManager_id());
			st.setInt(10, emp.getDepartment_id());
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	
	// 입력할거야
	//11개 칼럼을 ()에 다 넣는건 비효율적이니까 만들어둔 EmpDTO를 가져올거야
		public int empInsertByID(EmpDTO emp) { // 삭제된 건수의 개수를 보여줄거야 int
			int result = 0;
			Connection conn = DBUtil.getConnection(); // db연결
			PreparedStatement st = null;
			String sql = """
					insert into employees (
					EMPLOYEE_ID,    
					FIRST_NAME,    
					LAST_NAME,     
					EMAIL,          
					PHONE_NUMBER,   
					HIRE_DATE,      
					JOB_ID,         
					SALARY,         
					COMMISSION_PCT, 
					MANAGER_ID,     
					DEPARTMENT_ID) 
					values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
					""";
			try {
				st = conn.prepareStatement(sql); // sql문 준비한다.
				st.setInt(1, emp.getEmployee_id());
				st.setString(2, emp.getFirst_name());
				st.setString(3, emp.getLast_name());
				st.setString(4, emp.getEmail());
				st.setString(5, emp.getPhone_number());
				st.setDate(6, emp.getHire_date());
				st.setString(7, emp.getJob_id());
				st.setDouble(8, emp.getSalary());
				st.setDouble(9, emp.getCommission_pct());
				st.setInt(10, emp.getManager_id());
				st.setInt(11, emp.getDepartment_id());
				
				result = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return result;
		}
	
	
	
	// 삭제하고싶음. 특정 직원을 삭제해
	public int empDeleteByID(int empid) { // 삭제된 건수의 개수를 보여줄거야 int
		int result = 0;
		Connection conn = DBUtil.getConnection(); // db연결
		PreparedStatement st = null;
		String sql = "delete from employees where employee_id = ?";
		
		try {
			st = conn.prepareStatement(sql);// sql문 준비한다.
			st.setInt(1, empid);
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return result;
	}

	public List<EmpDTO> selectByCondition(int deptId, String jobId, int salary, String hdate) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = DBUtil.getConnection(); // db연결
		PreparedStatement st = null;
		ResultSet rs = null; // 들어온 data

		String sql = "select * from employees " + " where department_id = ? " + " and job_id like upper(?)"
				+ " and salary >= ?" + " and hire_date >= ?"; // 바인딩 변수

		try {
			st = conn.prepareStatement(sql); // sql문 준비한다.
			st.setInt(1, deptId); // 첫번째 물음표에 값(job)을 넣을거야
			st.setString(2, "%" + jobId + "%");
			st.setInt(3, salary);

			Date d = DateUtil.convertToSQLDate(DateUtil.converToDate(hdate));
			st.setDate(4, d);

			rs = st.executeQuery(); // st에서 sql문을 준비하니까 ()안에 sql 넣으면 안됨
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs); // 길어지니까 makeEmp로 만들어야
				emplist.add(emp); // 그리고 그 emp를 emplist에 담을거야 그리고 보낼거야
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	public List<EmpDTO> selectByJobAnDept(String job, int dept) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = DBUtil.getConnection(); // db연결
		PreparedStatement st = null;
		ResultSet rs = null; // 들어온 data

		String sql = "select * from employees where job_id = ? and department_id = ?"; // 바인딩 변수

		try {
			st = conn.prepareStatement(sql); // sql문 준비한다.
			st.setString(1, job); // 첫번째 물음표에 값(job)을 넣을거야
			st.setInt(2, dept);
			rs = st.executeQuery(); // st에서 sql문을 준비하니까 ()안에 sql 넣으면 안됨
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs); // 길어지니까 makeEmp로 만들어야
				emplist.add(emp); // 그리고 그 emp를 emplist에 담을거야 그리고 보낼거야
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 직책으로 직원 조회 -> 여러명이니까 List로
	public List<EmpDTO> selectByJob(String job) {
		// 들어오는 건 없는데 나가는건 여러개
		// statement : sql문을 보내는 통로. 바인딩변수 지원 X
		// PreparedStatment : Statement 상속받아서 바인딩변수 지원
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = DBUtil.getConnection(); // db연결
		// Statement st = null; // 통로
		PreparedStatement st = null;
		ResultSet rs = null; // 들어온 data
		// String sql = "select * from employees where job_id = " + job; //job은 값인데
		// ''없으니까 오류 -> IT_PROG
		// String sql = "select * from employees where job_id = '" + job + "'";
		// //'IT_PROG -> 매번 이러면 성능 저하
		String sql = "select * from employees where job_id = ?"; // 바인딩 변수

		try {
			// st = conn.createStatement();// 통로 만듦
			// rs = st.executeQuery(sql); // sql 실행

			st = conn.prepareStatement(sql); // sql문 준비한다.
			st.setNString(1, job); // 첫번째 물음표에 값(job)을 넣을거야
			rs = st.executeQuery(); // st에서 sql문을 준비하니까 ()안에 sql 넣으면 안됨
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs); // 길어지니까 makeEmp로 만들어야
				emplist.add(emp); // 그리고 그 emp를 emplist에 담을거야 그리고 보낼거야
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 부서코드로 직원 조회 -> 여러명이니까 List로
	public List<EmpDTO> selectByDept(int deptId) {
		// 들어오는 건 없는데 나가는건 여러개
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = DBUtil.getConnection(); // db연결
		Statement st = null; // 통로
		ResultSet rs = null; // 들어온 data
		String sql = "select * from employees where department_id = " + deptId;

		try {
			st = conn.createStatement();// 통로 만듦
			rs = st.executeQuery(sql); // sql 실행
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs); // 길어지니까 makeEmp로 만들어야
				emplist.add(emp); // 그리고 그 emp를 emplist에 담을거야 그리고 보낼거야
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 직원번호로 직원정보 상세보기
	public EmpDTO selectById(int empid) {
		// 직원 번호가 들어와야 내보낼 수 있음 -> 파라미터 입력
		// 한 건 출력됨 -> list X -> 그냥 EmpDTO
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection(); // db연결
		Statement st = null; // 통로
		ResultSet rs = null; // 들어온 data
		String sql = "select * from employees where employee_id = " + empid; // 직원아이디 들어온거

		try {
			st = conn.createStatement();// 통로 만듦
			rs = st.executeQuery(sql); // sql 실행
			if (rs.next()) {
				// 한 건이니까
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emp;
	}

	// 모든 직원 조회
	public List<EmpDTO> selectAll() {
		// 들어오는 건 없는데 나가는건 여러개
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = DBUtil.getConnection(); // db연결
		Statement st = null; // 통로
		ResultSet rs = null; // 들어온 data
		String sql = "select * from employees";

		try {
			st = conn.createStatement();// 통로 만듦
			rs = st.executeQuery(sql); // sql 실행
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs); // 길어지니까 makeEmp로 만들어야
				emplist.add(emp); // 그리고 그 emp를 emplist에 담을거야 그리고 보낼거야
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder().commission_pct(rs.getDouble("commission_pct"))
				.department_id(rs.getInt("department_id")).email(rs.getString("email"))
				.employee_id(rs.getInt("employee_id")).first_name(rs.getString("first_name"))
				.hire_date(rs.getDate("hire_date")).job_id(rs.getString("job_id")).last_name(rs.getString("last_name"))
				.manager_id(rs.getInt("manager_id")).phone_number(rs.getString("phone_number"))
				.salary(rs.getDouble("salary")).build();

		return emp;
	}

}
