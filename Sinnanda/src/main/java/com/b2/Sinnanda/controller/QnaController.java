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

import com.b2.Sinnanda.service.QnaService;
import com.b2.Sinnanda.vo.Qna;
import com.b2.Sinnanda.vo.QnaComment;
import com.b2.Sinnanda.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class QnaController {
	@Autowired
	private QnaService qnaService;
	
	// [이승준] QnA 목록 페이징용 상수
	private final int ROW_PER_PAGE = 10;
	
	// [이승준] QnA 답변 삭제
	@GetMapping("/removeQnaComment")
	public String removeQnaComment(HttpServletRequest request, int qnaNo) {
		log.debug("[Debug] \"START\" QnaController.removeQnaComment() | Get");
		log.debug(" ├[param] qnaNo : "+qnaNo);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		// 답변 삽입
		qnaService.removeQnaComment(loginUser, qnaNo);
		
		return "redirect:/qnaOne?qnaNo="+qnaNo;
	}
	
	// [이승준] QnA 답변 삽입
	@GetMapping("/addQnaComment")
	public String addQnaComment() {
		log.debug("[Debug] \"START\" QnaController.addQnaComment() | Get");
		
		return "addQnaComment";
	}
	@PostMapping("/addQnaComment")
	public String addQnaComment(HttpServletRequest request, QnaComment qnaComment) {
		log.debug("[Debug] \"START\" QnaController.addQnaComment() | Post");
		log.debug(" ├[param] qnaComment : "+qnaComment.toString());
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		// 답변 삽입
		qnaService.addQnaComment(qnaComment);
		
		return "redirect:/qnaOne?qnaNo="+qnaComment.getQnaNo();
	}
	
	// [이승준] QnA 삭제
	@GetMapping("/removeQna")
	public String removeQna(HttpServletRequest request, int qnaNo) {
		log.debug("[Debug] \"START\" QnaController.removeQna() | Get");
		log.debug(" ├[param] qnaNo : "+qnaNo);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		// 수정 전 기존 값 출력
		qnaService.removeQna(qnaNo, loginUser.getMember().getMemberNo());
		
		return "redirect:/qnaList";
	}
	
	// [이승준] QnA 수정
	@GetMapping("/modifyQna")
	public String modifyQna(Model model, int qnaNo) {
		log.debug("[Debug] \"START\" QnaController.modifyQna() | Get");
		log.debug(" ├[param] qnaNo : "+qnaNo);
		
		// 수정 전 기존 값 출력
		Qna qna = qnaService.getQnaOne(qnaNo);
		model.addAttribute(qna);
		
		return "modifyQna";
	}
	@PostMapping("/modifyQna")
	public String modifyQna(Qna qna) {
		log.debug("[Debug] \"START\" QnaController.modifyQna() | Post");
		log.debug(" ├[param] qna : "+qna.toString());
		
		qnaService.modifyQna(qna);
		
		return "redirect:/qnaOne?qnaNo="+qna.getQnaNo();
	}
	
	// [이승준] QnA 삽입
	@GetMapping("/addQna")
	public String addQna(HttpServletRequest request, Model model) {
		log.debug("[Debug] \"START\" QnaController.addQna() | Get");
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		
		return "addQna";
	}
	@PostMapping("/addQna")
	public String addQna(Qna qna) {
		log.debug("[Debug] \"START\" QnaController.addQna() | Post");
		log.debug(" ├[param] qna : "+qna.toString());
		
		qnaService.addQna(qna);
		
		return "redirect:/qnaOne?qnaNo="+qna.getQnaNo();
	}
	
	// [이승준] QnA 상세 조회
	@GetMapping("/qnaOne")
	public String qnaOne(HttpServletRequest request, Model model, int qnaNo) {
		log.debug("[Debug] \"START\" QnaController.qnaOne() | Get");
		log.debug(" ├[param] qnaNo : "+qnaNo);
		
		// QnA 상세 조회
		Qna qna = qnaService.getQnaOne(qnaNo);
		
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
				log.info(" ├[info] qnaOne 접근 불가 : 비회원 or 사업자");
				return "redirect:/qnaList";
			}
			log.debug(" ├[param] 작성자 memberNo : "+qna.getMemberNo());
			log.debug(" ├[param] 접근자 Level : "+loginUser.getUserLevel());
			
			// 2. 멤버인 경우, 작성자 No + 세션 No = 일치 X -> qnaList
			if(loginUser.getUserLevel() == 1) {
				log.debug(" ├[param] 세션 memberNo : "+loginUser.getMember().getMemberNo());
				if(qna.getMemberNo() != loginUser.getMember().getMemberNo()) {
					return "redirect:/qnaList";
				}
			}
			log.info(" ├[info] qnaOne 접근 허용");
		}
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute(qna);	// 선택된 QnA 상세 정보
		
		return "qnaOne";
	}
	
	// [이승준] QnA 목록 조회
	@GetMapping("/qnaList")
	public String qnaList(HttpServletRequest request, Model model, 
			@RequestParam(defaultValue = "1") int currentPage, 
			@RequestParam(required = false) String qnaCategory) {
		log.debug("[Debug] \"START\" QnaController.qnaList() | Get");
		log.debug(" ├[param] currentPage : "+currentPage);
		
		// QnA 목록 조회
		Map<String, Object> map = qnaService.getQnaListByQnaCategory(qnaCategory, currentPage, ROW_PER_PAGE);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("qnaCategory", map.get("qnaCategory"));	// 선택된 QnA 카테고리
		model.addAttribute("qnaList", map.get("qnaList"));	// QnA 목록 정보
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지
		
		return "qnaList";
	}
}
