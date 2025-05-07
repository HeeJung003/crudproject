package com.shinhan.emp;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//java beans 기술에서 사용해야 함 (default, getter/setter)
//DTO : 데이터 전송 -> service, dao, controller 왔다갔다 가능
//칼럼의 이름과 필드 이름 일치!! 하는게 좋음
//class를 new해서 하나로 만들어

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmpDTO { //1
	 private int employee_id;
	 private String first_name;     
	 private String last_name;      
	 private String email;          
	 private String phone_number;   
	 private Date hire_date;      
	 private String job_id;         
	 private double salary;         
	 private double commission_pct; 
	 private int  manager_id;     
	 private int department_id ; 
}
