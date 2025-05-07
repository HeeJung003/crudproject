package com.shinhan.dept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultButtonModel;

import com.shinhan.day16.DBUtil;
import com.shinhan.emp.EmpDTO;

public class DeptDAO {

	//부서 조회
	public List<DeptDTO> selectAll() {
		//들어오는거 없고 나가는건 여러개 -> list
		List<DeptDTO> deptlist = new ArrayList<DeptDTO>();
		Connection conn = DBUtil.getConnection(); //db 연결할거야
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from departments";
		
		try {
			st = conn.createStatement(); //통로 만들어줬어
			rs = st.executeQuery(sql); //sql에 있느 데이터 가져올거야
			
			while(rs.next()) {
				DeptDTO dept = makeDept(rs);
				deptlist.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return deptlist;
	}
	
	//부서 상세보기
	public DeptDTO selectByDept(int deptId) {
		//사용자가 볼 부서id를 입력해야 돼
		//그럼 결과가 하나니까 list로 할 필요없어
		DeptDTO dept = new DeptDTO();
		Connection conn = DBUtil.getConnection(); //db 연결할거야
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from departments where department_id = ?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, deptId);
			rs = st.executeQuery();
			
			while(rs.next()) {
				dept = makeDept(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return dept;
	}
	
	//부서 수정하기
	public int updateDept(DeptDTO dept) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = """
				update departments set
					DEPARTMENT_ID = ?,
					DEPARTMENT_NAME = ?, 
					MANAGER_ID = ?,        
					LOCATION_ID = ?,        
				where department_id = ?
				""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, dept.getDepartment_id());
			st.setString(2, dept.getDepartment_name());
			st.setInt(3, dept.getManager_id());
			st.setInt(4, dept.getLocation_id());
			st.setInt(5, dept.getDepartment_id());
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}	
		return result;
	}

	
	private DeptDTO makeDept(ResultSet rs) throws SQLException {
		DeptDTO dept = DeptDTO.builder()
				.department_id(rs.getInt("department_id"))
				.department_name(rs.getString("department_name"))
				.manager_id(rs.getInt("manager_id"))
				.location_id(rs.getInt("location_id"))
				.build();
		return dept;
	}
	
	//부서 삭제하기
	public int deleteByDeptId(int deptId) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = "delete from departments where department_id = ?";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, deptId);
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		return result;
	}
	
	//부서 추가하기
	public int insertDept(DeptDTO dept) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = """
				insert into departments 
				values (?,?,?,?)
				""";
		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, dept.getDepartment_id());
			st.setString(2, dept.getDepartment_name());
			st.setInt(3, dept.getManager_id());
			st.setInt(4, dept.getLocation_id());
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		return result;
		
	}
}
