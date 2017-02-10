package com.shiksha.apnagang;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Sdfsdf fsrgr!@#dsxfbghsh";
		str = str.replaceAll("\\W+", ",");
		String[] words = str.split(",");
		for(String tmp:words){
			System.out.println(tmp);
		}
	}

}
