package com.shinhan.common;

import java.util.Scanner;

import com.shinhan.dept.DeptController;
import com.shinhan.emp.EmpController;

//frontController패턴
//Controller가 여러개인 경우 사용자ㄱ의 요청과 응답에 출구가 많은 경우 바람직하지 않음
//front는 하나만 만드는게 좋음
public class FrontController {
	public static void main(String[] args) {
		//사용자가 emp , dept 작업할건지 결정
		Scanner sc = new Scanner(System.in);	
		
		
		boolean isStop = false;
		CommonControllerInterface controller = null;
		while(!isStop) {
			System.out.print("작업선택 (emp or dept) : ");
			String job = sc.next();
			switch(job) {
			case "emp" -> {controller = ControllerFactory.make("emp");}
			case "dept" -> {controller = ControllerFactory.make("dept");}
			case "job" -> {controller = ControllerFactory.make("job");}
			case "end" -> {isStop = true; continue;}
			default -> {continue;}
			}
			//전략패턴
			controller.execute(); //작업이 달라져도 사용법이 같음
		}
		sc.close();
		System.out.println("*****main 종료*****");
	}
}
