package com.shinhan.emp;

import java.util.List;

//emp data를 display하려는 목적 -> 나중에 웹으로 변경되면 JSP로 만들 예정
public class EmpView { //4
	
	//여러 건 출력
	public static void display(List<EmpDTO> emplist) { //empdto 여러 건을 가져올거야
		if(emplist.size() == 0 ) { //add를 못 했으니까 size가 0
			display("해당하는 직원 존재 X");
			return;
		}
		System.out.println("===직원 여러건 조회===");
		emplist.stream().forEach(emp->System.out.println(emp));
	}
	
	public static void display(EmpDTO emp) {
		if(emp == null) {
			display("해당하는 직원 존재 X");
			return;
		}
		System.out.println("==직원정보 출력(1건)==\n" + emp);
	}
	
	public static void display(String message) {
		System.out.println("**알림** \n" + message);
	}

}
