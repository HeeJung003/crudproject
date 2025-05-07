package com.shinhan.dept;

import java.sql.Date;

import com.shinhan.emp.EmpDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class DeptDTO {
	 private int department_id;  
	 private String department_name;
	 private int manager_id;     
	 private int location_id;
	 
	 
	@Override
	public String toString() {
		return "[department_id=" + department_id + ", department_name=" + department_name + ", manager_id="
				+ manager_id + ", location_id=" + location_id + "]";
	}
	 
	 
	 
}
