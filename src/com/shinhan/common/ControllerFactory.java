package com.shinhan.common;

import com.shinhan.dept.DeptController;
import com.shinhan.emp.EmpController;
import com.shinhan.job.JobController;

//factory pattern 
//new 만드는건 여기서만 하자
public class ControllerFactory {

	public static CommonControllerInterface make(String business) {
		CommonControllerInterface controller = null;

		switch (business) {
		case "emp" -> {controller = new EmpController();}
		case "dept" -> {controller = new DeptController();}
		case "job" -> {controller = new JobController();}
		}
		return controller;
}
}
