package com.b2.Sinnanda.vo;

import lombok.Data;

@Data
public class Member {
	int memberNo;
	String memberId;
	String memberPw;
	String memberName;
	int memberAge;
	String memberTel;
	String createDate;
	String updateDate;
}