package com.b2.Sinnanda.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.b2.Sinnanda.vo.Admin;

@Mapper
public interface AdminMapper {
	//[윤경환] 관리자회원가입
	int insertAdmin(Admin admin);
	
	
	//[윤경환] 관리자 세부정보
	Admin selectAdminOne(int adminNo);
	
	//[윤경환] 관리자 회원정보 수정으로 이동 
	Admin modifyAdmin(String adminId, String adminPw);
	
	//[윤경환] 관리자 회원정보 수정 
	int modifyAdminForm(Admin admin);

	//[윤경환] 관리자 정보 
	Admin selectAdminName(int adminNo);
	
	//[윤경환] 관리자 목록 조회 페이징 사용 
	List<Admin> selectAdminList(Map<String, Object> map);
	
	//[윤경화] 관리자 총 수 
	int selectAdminTotalCount();
	
	//[윤경환] 관리자 등급 수정 
	
	int modifyAdminList(Admin admin);

}
