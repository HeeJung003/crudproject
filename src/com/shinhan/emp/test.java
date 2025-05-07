package com.shinhan.emp;

public class test {

	public static void main(String[] args) {
		String rrn = "031010-222222";
		//주민번호에서 앞자리 빼서 생일에 넣을거야
		String bir = rrn.substring(7, 8);
		String birth = String.valueOf(rrn.charAt(7)); 
		
		//성별
		String gen = String.valueOf(rrn.charAt(7));
		String gender = "";
		if(gen.equals("1") || gen.equals("3")) {
			gender = "M";
		}else if(gen.equals("2") || gen.equals("4")) {
			gender = "F";
		}
		
		
		System.out.println(birth);
		System.out.println(gender);
		
	
	}

}
