package com.shinhan.dept;

import java.util.List;


public class DeptService {
	
	DeptDAO deptDAO = new DeptDAO();
	
	//부서 전체 보기
	public List<DeptDTO> selectAll() {
		return deptDAO.selectAll();
	}
	
	//사용자가 부서번호 입력하면 해당하는 부서 보기
	public DeptDTO selectByDept(int deptId) {
		return deptDAO.selectByDept(deptId);
	}
	
	//부서 수정하기
	public int updateDept(DeptDTO dept) {
		return deptDAO.updateDept(dept);
	}
	
	//부서 삭제하기
	public int deleteByDeptId(int deptId) {
		return deptDAO.deleteByDeptId(deptId);
	}
	
	//부서 추가하기
	public int insertDept(DeptDTO dept) {
		return deptDAO.insertDept(dept);
	}
}
