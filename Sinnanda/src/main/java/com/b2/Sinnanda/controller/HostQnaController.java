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

import com.b2.Sinnanda.commons.DL;
import com.b2.Sinnanda.service.HostQnaService;
import com.b2.Sinnanda.vo.HostQna;
import com.b2.Sinnanda.vo.Qna;
import com.b2.Sinnanda.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HostQnaController {
	@Autowired
	private HostQnaService hostQnaService;
	@Autowired
	private DL dl;
	
	// 페이징용 상수
	private final int ROW_PER_PAGE = 10;
	
/* 1. 조회 */
	
	// [이승준] "사업자문의 목록" | 관리자페이지
	@GetMapping("/admin/hostQnaList")
	public String hostQnaListForAdmin(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int currentPage, 
			@RequestParam(defaultValue = "전체") String hostQnaCategory) {
		log.debug("[Debug] \"START\" HostQnaController.hostQnaListForHost() | Get");
		log.debug(" ├[param] currentPage : "+currentPage);
		log.debug(" ├[param] hostQnaCategory : "+hostQnaCategory);
		
		// 1. 출력을 시작하는 행 구하기 수식
		int beginRow = (currentPage * ROW_PER_PAGE) - ROW_PER_PAGE; 
		
		// 2. 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 2-1. 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
			return "redirect:/index";
		}
		
		// 3. 답변이 없는 Host QnA 목록 조회(userLevel, hostQnaCategory, currentPage, ROW_PER_PAGE)
		Map<String, Object> map = hostQnaService.getNoCommentsHostQnaListForAdmin(loginUser.getUserLevel(), hostQnaCategory, beginRow, ROW_PER_PAGE);
		
		// 4. 10개의 page 번호를 출력하기 위한 변수
		int pageNo = ((beginRow / 100) * 10 + 1);
		log.debug(" ├[param] pageNo : " + pageNo);
		
		// 4. 모델 추가
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("hostQnaCategory", hostQnaCategory);	// 선택된 QnA 카테고리
		model.addAttribute("hostQnaList", map.get("hostQnaList"));	// QnA 목록 정보
		model.addAttribute("beginRow", beginRow);
		model.addAttribute("ROW_PER_PAGE", ROW_PER_PAGE);
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지
		model.addAttribute("pageNo", pageNo);	// 페이지 번호를 출력하기 위한 변수
		
		return "admin/hostQnaList";
	}
	
	// [이승준] "사업자문의 목록" 조회 | 사업자페이지
	@GetMapping("/host/myHostQnaList")
	public String hostQnaListForHost(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int currentPage, 
			@RequestParam(defaultValue = "전체") String hostQnaCategory) {
		log.debug("[Debug] \"START\" HostQnaController.hostQnaListForHost() | Get");
		log.debug(" ├[param] currentPage : "+currentPage);
		log.debug(" ├[param] hostQnaCategory : "+hostQnaCategory);
		
		// 1. 출력을 시작하는 행 구하기 수식
		int beginRow = (currentPage * ROW_PER_PAGE) - ROW_PER_PAGE; 
		
		// 2. 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 2-1. 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
			return "redirect:/index";
		}
		
		// 3. Host QnA 목록 조회(userLevel, hostNo, hostQnaCategory, currentPage, ROW_PER_PAGE)
		Map<String, Object> map = hostQnaService.getHostQnaList(loginUser.getUserLevel(), loginUser.getHost().getHostNo(), hostQnaCategory, beginRow, ROW_PER_PAGE);
		
		// 4. 10개의 page 번호를 출력하기 위한 변수
		int pageNo = ((beginRow / 100) * 10 + 1);
		log.debug(" ├[param] pageNo : " + pageNo);
		
		// 4. 모델 추가
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("hostQnaCategory", hostQnaCategory);	// 선택된 QnA 카테고리
		model.addAttribute("hostQnaList", map.get("hostQnaList"));	// QnA 목록 정보
		model.addAttribute("beginRow", beginRow);
		model.addAttribute("ROW_PER_PAGE", ROW_PER_PAGE);
		model.addAttribute("lastPage", map.get("lastPage"));	// 마지막 페이지(페이징용)
		model.addAttribute("currentPage", currentPage);	// 현재 페이지
		model.addAttribute("pageNo", pageNo);	// 페이지 번호를 출력하기 위한 변수
		
		return "host/myHostQnaList";
	}
	
	// [이승준] "사업자문의 상세" 조회 | 사업자페이지
	@GetMapping("/host/myHostQnaOne")
	public String hostQnaOne(HttpServletRequest request, Model model, int hostQnaNo) {
		log.debug("[Debug] \"START\" HostQnaController.hostQnaOne() | Get");
		log.debug(" ├[param] hostQnaNo : "+hostQnaNo);
		
		// Host QnA 상세 조회
		HostQna hostQna = hostQnaService.getHostQnaOne(hostQnaNo);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		model.addAttribute("hostQna", hostQna);	// 선택된 QnA 상세 정보 */
		
		return "host/myHostQnaOne";
	}
	
	
	
/* 2. 삽입 */
	
	// [이승준] "사업자문의" 삽입 | 사업자페이지
	@GetMapping("/host/addHostQna")
	public String addHostQna(HttpServletRequest request, Model model) {
		log.debug("[Debug] \"START\" HostQnaController.addHostQna() | Get");
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
		} else {
			log.debug(" ├[param] loginUser : Null");
		}
		
		/* 모델 추가 */
		model.addAttribute("loginUser", loginUser);	// 로그인 세선 정보
		
		return "host/addHostQna";
	}
	@PostMapping("/host/addHostQna")
	public String addHostQna(HostQna hostQna) {
		log.debug("[Debug] \"START\" HostQnaController.addHostQna() | Post");
		log.debug(" ├[param] hostQna : "+hostQna.toString());
		
		hostQnaService.addHostQna(hostQna);
		
		return "redirect:/host/myHostQnaOne?hostQnaNo="+hostQna.getHostQnaNo();
	}
	
	
	
/* 3. 수정 */
	
	// [이승준] "사업자문의" 수정 | 사업자페이지
	@GetMapping("/host/modifyMyHostQna")
	public String modifyMyHostQna(HttpServletRequest request, Model model, int hostQnaNo) {
		log.debug("[Debug] \"START\" HostQnaController.modifyHostQna() | Get");
		log.debug(" ├[param] hostQnaNo : "+hostQnaNo);
		
		// 수정 전 기존 값 조회
		HostQna hostQna = hostQnaService.getHostQnaOne(hostQnaNo);
		model.addAttribute(hostQna);
		
		// 로그인 세션 조회
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		// 로그인 세션 디버깅
		if(loginUser != null) {
			log.debug(" ├[param] loginUser : "+loginUser.toString());
			// 오직 글을 작성한 사업자만 접근 가능
			if((loginUser.getUserLevel() != 2) && (loginUser.getHost().getHostNo() != hostQna.getHostNo())) {
				return "redirect:/host/hostQnaList";
			}
		} else {
			log.debug(" ├[param] loginUser : Null");
			return "redirect:/host/hostQnaList";
		}
		
		return "host/modifyMyHostQna";
	}
	@PostMapping("/host/modifyMyHostQna")
	public String modifyQna(HostQna hostQna) {
		log.debug("[Debug] \"START\" HostQnaController.modifyMyHostQna() | Post");
		log.debug(" ├[param] hostQna : "+hostQna.toString());
		
		hostQnaService.modifyHostQna(hostQna);
		
		return "redirect:/host/myHostQnaOne?hostQnaNo="+hostQna.getHostQnaNo();
	}
	
	
	
	
/* 4. 삭제 */
	
	// [이승준] "사업자문의" 삭제 | 사업자페이지
	@GetMapping("/host/removeMyHostQna")
	public String removeMyHostQna(int hostQnaNo) {
		dl.p("HostQnaController", "removeHostQna() | Get", "시작");
		dl.p("removeHostQna()", "hostQnaNo", hostQnaNo);
		
		hostQnaService.removeHostQna(hostQnaNo);
		
		return "redirect:/host/hostPage";
	}
	
	
	
}
