package com.shinhan.dept;

import java.util.List;

public class DeptView {

	public static void display(List<DeptDTO> deptlist) {
		if(deptlist.size() == 0) {
			display("해당하는 부서 없음");
		}
		
		System.out.println("===부서 전체 출력===");
		deptlist.stream().forEach(dept -> System.out.println(dept));
	}
	
	public static void display(DeptDTO dept) {
		if(dept == null) {
			display("해당하는 부서 없음");
		}
		
		System.out.println("===부서 출력===");
		System.out.println(dept);
	}

	public static void display(String message) {
		System.out.println("**알림** "+ message);
	}
}
