package com.shinhan.dept;

import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

import lombok.Builder;

//부서 조회
//부서번호 입력받고 해당 부서 출력
//부서 수정
//부서 삭제
//부서 추가

public class DeptController implements CommonControllerInterface{

	static Scanner sc = new Scanner(System.in);
	static DeptService deptService = new DeptService();
	
	@Override
	public void execute() { //new해서 호출할거니까 static지움
		
		boolean isStop = false;
		
		while(true) {
			menudisplay();
			int job = sc.nextInt();
			switch(job) {
			case 1 -> {f_selectAll();}
			case 2 -> {f_selectByDeptID();}
			case 3 -> {f_updateByDeptID();}
			case 4 -> {f_deleteByDeptId();}
			case 5 -> {f_insertByDeptId();}
			case 99 -> {isStop = false; return;}
			}
		}
		
	}

	private static void f_insertByDeptId() {
		System.out.println("=== 부서 추가 중===");
		System.out.print("추가할 부서id 입력 : ");
		int deptid = sc.nextInt();
		deptService.insertDept(makeDept(deptid));
		DeptView.display("추가되었습니다");
	}

	private static void f_deleteByDeptId() {
		System.out.println("==부서 삭제 중==");
		System.out.print("삭제할 부서id 입력 : ");		
		int deptid = sc.nextInt();
		deptService.deleteByDeptId(deptid);
		DeptView.display("삭제되었습니다");
	}

	private static void f_updateByDeptID() {
		System.out.println("===부서 수정 중===");
		System.out.print("수정할 부서id 입력 : ");
		int deptid = sc.nextInt();
		
		DeptDTO dept = makeDept(deptid);
		DeptView.display("수정되었습니다");
		DeptView.display(dept);
	}

	private static DeptDTO makeDept(int deptId) {
		System.out.print("department_id : ");
		int department_id = sc.nextInt();
		System.out.print("department_name : ");
		String department_name = sc.next();
		System.out.print("manager_id : ");
		int manager_id = sc.nextInt();
		System.out.print("location_id : ");
		int location_id = sc.nextInt();
		
		DeptDTO dept = DeptDTO.builder()
				.department_id(department_id)
				.department_name(department_name)
				.manager_id(manager_id)
				.location_id(location_id)
				.build();
		
		return dept;
	}

	private static void f_selectByDeptID() {
		System.out.println("===해당하는 부서 출력===");
		System.out.print("부서id 입력 : ");
		int job = sc.nextInt();
		DeptDTO dept = deptService.selectByDept(job);
		DeptView.display(dept);
	}

	private static List<DeptDTO> f_selectAll() {
		List<DeptDTO> emplist = deptService.selectAll();
		DeptView.display(emplist);
		return emplist;
		
	}

	private static void menudisplay() {
		System.out.println("===============================================================================");
		System.out.println("1. 부서 조회 || 2. 부서 상세보기 || 3. 부서 수정 || 4. 부서 삭제 || 5. 부서 추가 || 99. 종료");
		System.out.println("===============================================================================");
		System.out.print("입력 >>> ");
		
	}
}
