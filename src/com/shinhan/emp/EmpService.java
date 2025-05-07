package com.shinhan.emp;

import java.util.List;

//Service : business logic 수행
//1) 이체업무 : (인출하기, 입금하기), = 인출하기, 입금하기는 dao가.
//2) 비밀번호: 암호화 
public class EmpService { // 3

	EmpDAO empDAO = new EmpDAO();
	
	
	public EmpDTO execute_sp(int empid) {
		return empDAO.execute_sp(empid);
	}
	
	
	
	
	
	
	//----------------------------------------

	public List<EmpDTO> selectAll() {
		return empDAO.selectAll();

	}
	
	public int empUpdateByID(EmpDTO emp) { 
		return empDAO.empUpdateByID(emp);
	}

	//입력하고싶어
	public int empInsertByID(EmpDTO emp) {
		return empDAO.empInsertByID(emp);
	}
	
	
	// 삭제하고싶음. 특정 직원을 삭제해
	public int empDeleteByID(int empid) {
		return empDAO.empDeleteByID(empid);
	}

	// 특정 직원만 보회
	public EmpDTO selectById(int empid) {
		return empDAO.selectById(empid); // empDAO의 selectById로 가 (가면 sql문장 받을 수 있음) 그걸 return해. 누구한테? controller한테
	}

	// 부서아이디 -> dap 넘기고 cont
	public List<EmpDTO> selectByDept(int deptId) {
		// Controller로 받은 depId를 empDAO로 넘겨서 sql을 통해서 가져공ㄹ거야
		// 결과가 여러개니까 list로
		// empDAO에서 받은 정보를 다시 contoller에 return해
		return empDAO.selectByDept(deptId);
	}

	// 직책으로
	public List<EmpDTO> selectByJob(String job) {
		return empDAO.selectByJob(job);
	}

	public List<EmpDTO> selectByJobAnDept(String job, int dept) {
		return empDAO.selectByJobAnDept(job, dept);
	}

	public List<EmpDTO> selectByCondition(int deptId, String jobId, int salary, String hdate) {
		// TODO Auto-generated method stub
		return empDAO.selectByCondition(deptId, jobId, salary, hdate);
	}
}
