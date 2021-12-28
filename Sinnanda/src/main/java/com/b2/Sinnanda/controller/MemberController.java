package com.b2.Sinnanda.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b2.Sinnanda.commons.DL;
import com.b2.Sinnanda.service.CertifyEmailService;
import com.b2.Sinnanda.service.MemberService;
import com.b2.Sinnanda.vo.Admin;
import com.b2.Sinnanda.vo.Complain;
import com.b2.Sinnanda.vo.Member;
import com.b2.Sinnanda.vo.MemberOut;
import com.b2.Sinnanda.vo.Qna;
import com.b2.Sinnanda.vo.Reserve;
import com.b2.Sinnanda.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@Autowired MemberService memberService;
	@Autowired CertifyEmailService certifyEmailService;
	@Autowired DL dl;
	
	// [유동진] 마이페이지
	@GetMapping("/member/myPage")
	public String myPage(Model model, int memberNo) {
      // memberNo을 이용해서 member의 데이터들을 조회하고, member 객체에 삽입
      Member member = memberService.myPage(memberNo);
      log.debug("멤버 넘버 : "+ memberNo);
      
      // member 객체의 데이터를 전달
      model.addAttribute(member);
      return "/member/myPage";
	}
		
	// [유동진] 회원 정보 수정(프로필)
	@GetMapping("/member/modifyMember")
	public String modifyMember(HttpServletRequest request, Model model) {
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		model.addAttribute(loginUser);
		return "/member/modifyMember";
	}
	@PostMapping("/member/modifyMember")
	public String modifyMember(Member member) {
		log.debug("♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥MemberController : modifyMember -> " + member.toString());
		memberService.modifyMember(member);
		log.debug("♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥MemberController : 회원정보수정 성공!");
		return "redirect:/member/myPage?memberNo="+member.getMemberNo();
	}
	
	// [유동진] 비밀번호 변경
	   @GetMapping("/member/checkMemberPw")
	   public String checkMemberPw(HttpServletRequest request, Model model) {
	      // 로그인 세션 조회
	      HttpSession session = request.getSession();
	      User loginUser = (User)session.getAttribute("loginUser");
	      // 로그인 세션 디버깅
	      if(loginUser != null) {
	         log.debug(" ├[param] loginUser : "+loginUser.toString());
	      } else {
	         log.debug(" ├[param] loginUser : Null");
	      }
	      
	      Member member = new Member();
	      member = memberService.getNowMemberPw(loginUser.getMember().getMemberNo());
	      log.debug(" ├[param] getMemberNo : "+ loginUser.getMember().getMemberNo());
	      log.debug(" ├[param] member : "+ member.toString());
	      
	      
	      model.addAttribute(member);
	      model.addAttribute(loginUser);
	      return "member/checkMemberPw";
	   }
	@PostMapping("/member/checkMemberPw")
	public String postcheckMemberPw(String memberPw) {
		log.debug("memberPw ->->->->->->-> " + memberPw);
		
		memberService.getCheckMemberPw(memberPw);
		return "/member/modifyMemberPw";
	}
	@PostMapping("/member/modifyMemberPw")
	public String modifyMemberPw(HttpSession session, Member member) {

		memberService.modifyMemberPw(member);
		// 비밀번호 변경완료시 자동으로 로그아웃
		session.invalidate();
		log.debug("★★★★★★★★★★★★★ 회원비밀번호 변경 성공!!! 다시 로그인 해주세요!!!!");
		return "redirect:/index";
	}
	
	// [유동진] QnA 목록 페이징용 상수
	private final int ROW_PER_PAGE = 10;
	
	// [유동진] 내가 작성한 QnA 목록 조회
	@GetMapping("/member/myQnaList")
	public String myQnaList(HttpServletRequest request, Model model, 
			@RequestParam(defaultValue = "1") int currentPage, 
			@RequestParam(required = false) String qnaCategory) {
		log.debug("[Debug] \"START\" MemberController.myQnaList() | Get");
		log.debug(" ├[param] currentPage : "+currentPage);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
			return "redirect:/index";
		}

		// QnA 목록 조회
		Map<String, Object> map = memberService.getMyQnaListByQnaCategory(loginUser.getMember().getMemberNo(), qnaCategory, currentPage, ROW_PER_PAGE);
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("qnaCategory", map.get("qnaCategory"));	// 선택된 QnA 카테고리
		model.addAttribute("myQnaList", map.get("myQnaList"));	// QnA 목록 정보
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지
		return "/member/myQnaList";
	}
	
	// [유동진] My QnA 상세 조회
	@GetMapping("/member/myQnaOne")
	public String myQnaOne(HttpServletRequest request, Model model, int qnaNo) {
		log.debug("[Debug] \"START\" memberController.myQnaOne() | Get");
		log.debug(" ├[param] qnaNo : "+qnaNo);
		
		// QnA 상세 조회
		Qna qna = memberService.getMyQnaOne(qnaNo);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		// *비밀문의인 경우, 접근제한 필요(작성자 본인 or 관리자)
		if(qna.getQnaSecret().equals("비밀문의")) {
			// 1. 비회원 or 사업자인 경우 -> qnaList
			if((loginUser == null) || (loginUser.getUserLevel() == 2)) {
				log.info(" ├[info] myQnaOne 접근 불가 : 비회원 or 사업자");
				return "redirect:/member/myQnaList";
			}
			log.debug(" ├[param] 작성자 memberNo : "+qna.getMemberNo());
			log.debug(" ├[param] 접근자 Level : "+loginUser.getUserLevel());
			
			// 2. 멤버인 경우, 작성자 No + 세션 No = 일치 X -> qnaList
			if(loginUser.getUserLevel() == 1) {
				log.debug(" ├[param] 세션 memberNo : "+loginUser.getMember().getMemberNo());
				if(qna.getMemberNo() != loginUser.getMember().getMemberNo()) {
					return "redirect:/member/myQnaList";
				}
			}
			log.info(" ├[info] myQnaOne 접근 허용");
		}
		// 모델 추가
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute(qna);	// 선택된 QnA 상세 정보
		
		return "/member/myQnaOne";
	}
	
	// [유동진] 내가 작성한 리뷰 목록 조회
	@GetMapping("/member/myReviewList")
	public String myReviewList(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(required = false) String reviewRecommend) {		
		dl.p("MemberController", "myReviewList()", "나의 리뷰 목록 시작");
		dl.p("MemberController", "myReviewList()", "currentPage : " + currentPage);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			dl.p("MemberController", "myReviewList", "loginUser : "+loginUser.toString());
		} else {
			dl.p("MemberController", "myReviewList", "loginUser : Null");
			return "redirect:/index";
		}
		
		// review 목록 조회
		Map<String, Object> map = memberService.getMyReviewListByReviewRecommend(loginUser.getMember().getMemberNo(), reviewRecommend, currentPage, ROW_PER_PAGE);
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("reviewRecommend", map.get("reviewRecommend"));	// 선택된 reviewRecommend
		model.addAttribute("myReviewList", map.get("myReviewList"));	// 리뷰 목록 정보
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지

		return "/member/myReviewList";
	}
	
	// [유동진] 회원 예약내역 조회
	@GetMapping("/member/myReserveList")
	public String myReserveList(HttpServletRequest request, Model model, 
		@RequestParam(defaultValue = "1") int currentPage, 
		@RequestParam(required = false) String reserveUse) {
		dl.p("MemberController", "myReserveList()", "예약내역 조회");
		dl.p("MemberController", "myReserveList()", "currentPage : " + currentPage);
	
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			dl.p("MemberController", "myReviewList", "loginUser : "+loginUser.toString());
		} else {
			dl.p("MemberController", "myReviewList", "loginUser : Null");
			return "redirect:/index";
		}
		
		// review 목록 조회
		Map<String, Object> map = memberService.getMyReserveList(loginUser.getMember().getMemberNo(), reserveUse, currentPage, ROW_PER_PAGE);
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("reserveUse", map.get("reserveUse"));	// 선택된 reserveUse
		model.addAttribute("myReserveList", map.get("myReserveList"));	// 예약 목록 정보
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지

		return "/member/myReserveList";
	}
	
	// [유동진] 예약내역 상세조회
	@GetMapping("/member/myReserveOne")
	public String myReserveOne(HttpServletRequest request, Model model, int reserveNo) {
		dl.p("memberController","myReserveOne()", "예약내역 상세조회");
		dl.p("memberController ","myReserveOne()","reserveNo : " + reserveNo);
		
		// 예약내역 상세 조회
		Reserve reserve = memberService.getMyReserveOne(reserveNo);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			dl.p("memberController","myReserveOne()", "loginUser : "+loginUser.toString());
		} else {
			dl.p("memberController","myReserveOne()", "loginUser : Null");
		}
		
		// 멤버인 경우, 작성자 No + 세션 No = 일치 X -> myReserveList
		if(loginUser.getUserLevel() == 1) {
			dl.p("memberController","myReserveOne()","memberNo : "+loginUser.getMember().getMemberNo());
		}
		dl.p("memberController","myReserveOne()", "myReserveOne 접근 허용");
	
	// 모델 추가
	model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
	model.addAttribute(reserve);	// 선택된 Reserve 상세 정보
	
	return "/member/myReserveOne";
	}
	
	// [유동진] 내가 작성한 컴플레인 목록 조회
	@GetMapping("/member/myComplainList")
	public String myComplainList(HttpServletRequest request, Model model, 
			@RequestParam(defaultValue = "1") int currentPage, 
			@RequestParam(required = false) String complainCategory) {
		dl.p("MemberController", "myComplainList()", "내가 작성한 컴플레인 목록 조회");
		dl.p("MemberController", "myComplainList()", "currentPage : "+currentPage);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			dl.p("MemberController", "myComplainList()", "loginUser : "+loginUser.toString());
		} else {
			dl.p("MemberController", "myComplainList()", "loginUser : Null");
			return "redirect:/index";
		}

		// 컴플레인 목록 조회
		Map<String, Object> map = memberService.getMyComplainList(loginUser.getMember().getMemberNo(), complainCategory, currentPage, ROW_PER_PAGE);
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("complainCategory", complainCategory);	// 선택된 컴플레인 카테고리
		model.addAttribute("myComplainList", map.get("myComplainList"));	// 컴플레인 목록 정보
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지
		return "/member/myComplainList";
	}
	
	// [유동진] 컴플레인 상세조회
	@GetMapping("/member/myComplainOne")
	public String myComplainOne(HttpServletRequest request, Model model, int complainNo) {
		dl.p("memberController","myComplainOne()", "컴플레인 상세조회");
		dl.p("memberController ","myComplainOne()","complainNo : " + complainNo);
		
		// 컴플레인 상세 조회
		Complain complain = memberService.getMyComplainOne(complainNo);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		
		// 로그인 세션 디버깅
		if(loginUser != null) {
			dl.p("memberController","myComplainOne()", "loginUser : "+loginUser.toString());
		} else {
			dl.p("memberController","myComplainOne()", "loginUser : Null");
		}
		
		// 멤버인 경우, 작성자 No + 세션 No = 일치 X -> myReserveList
		if(loginUser.getUserLevel() == 1) {
			dl.p("memberController","myComplainOne()","memberNo : "+loginUser.getMember().getMemberNo());
		}
		dl.p("memberController","myComplainOne()", "myComplainOne 접근 허용");
	
		// 모델 추가
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute(complain);	// 선택된 complain 상세 정보
		
		return "/member/myComplainOne";
	}
	
	//	[김영후] 회원 가입
	@GetMapping("/insertMember")
	public String getInsertMember() {
		return "/insertMemberForm";
	}
	@PostMapping("/insertMember")
	public String postInsertMember(Member member) {
		log.debug("입력된 회원 정보 :" + member.toString());
		//	회원정보 입력
		memberService.addMember(member);
		//	입력된 이메일로 인증코드 발송
		certifyEmailService.sendMail(member);
		
		return "redirect:/login";
	}
	
	//	[김영후]	회원 이메일 인증
	@PostMapping("/certifyMember")
	public String certifyMember(Member member) {
		int result = memberService.certifyMember(member);
		log.debug("├[Debug] 이메일 인증 result : " + result);
		if(result == 1) {	//	인증 완료 후, Member 정보 update
			memberService.certifyMemberUpdate(member);
			return "index";
		} else {	//	인증 실패시 다시 인증 화면
			return "certifyEmailForm";
		}
	}
	
	//	[김영후] 회원 탈퇴
	@GetMapping("/member/insertMemberOut")
	public String getInsertMemberOut() {
		return "/member/memberOutForm";
	}	
	@PostMapping("/member/insertMemberOut")
	public String postInsertMemberOut(Member member, MemberOut memberOut) {
		log.debug("탈퇴할 멤버 정보 :" + member);		
		//	트랜잭션 처리 -> member 테이블 데이터 삭제 후 memberOut 테이블 데이터 삽입
		memberService.removeMember(member, memberOut);
		
		return "redirect:/index";
	}
	
	//	[김영후] 회원 가입 시 실시간 ID 중복체크
	@GetMapping("/chkId")
	@ResponseBody
	public int checkId(String memberId) {
		//	DB에 저장된 중복값 유무 결과를 반환
		int checkResult = memberService.checkId(memberId);
		log.debug("중복값 검사 결과 : " + checkResult);
		return checkResult;
	}
}
