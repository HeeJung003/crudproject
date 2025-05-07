package com.shinhan.emp;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

//main(시작)은 controller
//mvc2 모델
//FrontController -< controller 선택 -> service -> dao -> db
//db -> dao -> servict -> controller -> frontcontroller 

public class EmpController implements CommonControllerInterface{ // 1

	static Scanner sc = new Scanner(System.in);
	static EmpService empService = new EmpService(); // EmpService에 있는 함수들은 static이 아님 -> new 해야 됨

	@Override
	public void execute() { //나중에 EmpController new해서 쓸거니까 static 뺌

		boolean isStop = false;
		while (!isStop) {
			menuDisplay();
			int job = sc.nextInt();
			switch (job) {
			case 1 -> {f_selectAll();}
			case 2 -> {f_selectById();}
			case 3 -> {f_selectByDept();}
			case 4 -> {f_selectByJob();}
			case 5 -> {f_selectByJobAnDept();}
			case 6 -> {f_selectByCondition();}
			case 7 -> {f_deleteByEmpID();}
			case 8 -> {f_insertByEmpID();}
			case 9 -> {f_updateByEmpID();}
			case 10 -> {f_sp_Call();}
			case 99 -> {isStop = true;}
			}
		}
		System.out.println("the end");
	}

	private static void f_sp_Call() {
		System.out.print("조회할 직원 id : ");
		int employee_id = sc.nextInt();
		EmpDTO emp = empService.execute_sp(employee_id);
		
		String message = "데이터 없음";
		if(emp != null) {
			message = emp.getEmail() + " --- " + emp.getSalary();
		}
		EmpView.display(message);
	}

	private static void f_updateByEmpID() {
		System.out.print("employee_id : ");
		int employee_id = sc.nextInt();
		EmpDTO exist_emp = empService.selectById(employee_id);
		if(exist_emp == null) {
			EmpView.display("존재하지 않는 직원");
		}
		
		System.out.println("==존재하는 직원정보==");
		EmpView.display(empService.selectById(employee_id));
		int result = empService.empUpdateByID(makeEmp(employee_id));
		EmpView.display(result + "건 입력");		
	}
		
	private static EmpDTO makeEmp(int employee_id) {
		
		System.out.print("first_name : ");
		String first_name = sc.next();		
		System.out.print("last_name : ");
		String last_name = sc.next();		
		System.out.print("email : ");
		String email = sc.next();		
		System.out.print("phone_number : ");
		String phone_number = sc.next();	
		System.out.print(" hdate(yyyy-mm-dd) : ");
		String hdate = sc.next();
		Date hire_date = DateUtil.convertToSQLDate(DateUtil.converToDate(hdate));		
		System.out.print("job_id(F1K) : ");
		String job_id = sc.next();		
		System.out.print("salary : ");
		double salary = sc.nextDouble();		
		System.out.print("commission_pct(0.2) : ");
		double commission_pct = sc.nextDouble();	
		System.out.print("manager_id(FK) : ");
		int manager_id = sc.nextInt();		
		System.out.print("department_id(FK) : ");
		int department_id = sc.nextInt();
		
		EmpDTO emp = EmpDTO.builder()
				.commission_pct(commission_pct)
				.department_id(department_id)
				.email(email)
				.employee_id(employee_id)
				.first_name(first_name)
				.hire_date(hire_date)
				.job_id(job_id)
				.last_name(last_name)
				.manager_id(manager_id)
				.phone_number(phone_number)
				.salary(salary)
				.build();
		return emp;
	}

	private static void f_insertByEmpID() {
		System.out.print("신규employee_id : ");
		int employee_id = sc.nextInt();
		
		
		int result = empService.empInsertByID(makeEmp(employee_id));
		EmpView.display(result + "건 입력");

	}

	private static void f_deleteByEmpID() {
		System.out.println("삭제할 직원 id");
		int empid = sc.nextInt();
		int result = empService.empDeleteByID(empid);
		EmpView.display(result + "건 삭제");
	}

	private static void f_selectByCondition() {
		// 부서(=), 직책(like), 급여(>=), 입사일(>=)
		System.out.print("조회할 부서번호 : ");
		int deptId = sc.nextInt();
		System.out.print("조회할 job_id : ");
		String jobId = sc.next();
		System.out.print("조회할 salary(이상) : ");
		int salary = sc.nextInt();
		System.out.print("조회할 입사일(yyy-mm-dd) : ");
		String hdate = sc.next();

		List<EmpDTO> emplist = empService.selectByCondition(deptId, jobId, salary, hdate);
		EmpView.display(emplist);
	}

	private static void f_selectByJobAnDept() {
		// 직책, 부서 아이디 같이 받음
		System.out.print("조회할 job_id, 부서ID: "); // IT_PROG, 60 -> , 기준으로 자름
		String data = sc.next();
		String[] arr = data.split(",");
		String jobId = arr[0];
		int deptid = Integer.parseInt(arr[1]);
		List<EmpDTO> emplist = empService.selectByJobAnDept(jobId, deptid);

	}

	private static void f_selectByJob() {
		// job
		System.out.print("조회할 job_id : "); // 사용자한테 조회할 부서번호를 받아
		String JobId = sc.next();
		List<EmpDTO> emplist = empService.selectByJob(JobId);
		// 받은걸 empService로 넘겨 -> empDAO로 넘겨서 정보를 받아 -> list<EmpDTO>로 받아(거기에 변수들이 정의되어
		// 있으니까)
		EmpView.display(emplist);
		// EmpDTO에서 받은 정보들은 empView로 넘겨서 출력해줘
	}

	private static void f_selectByDept() {
		// 부서
		System.out.print("조회할 부서번호 : "); // 사용자한테 조회할 부서번호를 받아
		int deptId = sc.nextInt();
		List<EmpDTO> emplist = empService.selectByDept(deptId);
		// 받은걸 empService로 넘겨 -> empDAO로 넘겨서 정보를 받아 -> list<EmpDTO>로 받아(거기에 변수들이 정의되어
		// 있으니까)
		EmpView.display(emplist);
		// EmpDTO에서 받은 정보들은 empView로 넘겨서 출력해줘

	}

	private static void f_selectById() {
		// 특정 직원의 아이디입력
		// 특정 직원 아이디니까 empid를 받을거야
		// 그리고 empid 입력받은걸 empService로 보낼거야 (그러면 empService에서 controller로 보내)
		// 그 결과를 EmpDTO type의 emp로 받아
		// 그걸 display로 찍어줘
		System.out.print("조회할 id : ");
		int empid = sc.nextInt();
		EmpDTO emp = empService.selectById(empid); // service -> controller -> sql정보 service에 return-> 그걸 EmpDTO emp로 받아
		EmpView.display(emp);

	}

	private static void f_selectAll() {
		// 모든 직원 조회
		// empService에 있는 selectAll을 가져올거고
		// 그게 list로 EmpDTO type이야
		// 그걸 emplist로 받을거야
		List<EmpDTO> emplist = empService.selectAll();
		EmpView.display(emplist);
	}

	private static void menuDisplay() {
		System.out.println("--메뉴--");
		System.out.println("1. 모두 조회 || 2. 직원번호로 조회 || 3. 부서로 조회 || 4. 직책으로 조회 || 5. 직책, 부서로 조회|| "
				+ "6. condition || 7. 삭제 || 8. 입력 ||  9. 수정 || 10. sp호출 || 99. 끝");
		System.out.println("------");

		System.out.println("입력 : ");

	}
}
