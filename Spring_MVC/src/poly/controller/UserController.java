package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.UserDTO;
import poly.service.IUserService;
import poly.util.CmmUtil;


@Controller
public class UserController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "UserService")
	private IUserService userService;
	
	@RequestMapping(value="user/userLogin")
	public String userLogin(HttpServletRequest request, ModelMap model) {
		log.info(this.getClass() + "user/userLogin start!!");
		log.info(this.getClass() + "user/userLogin end!!");
		
		return "/user/userLogin";
	}
	
	@RequestMapping(value="user/userLoginProc.do")
	public String userLoginProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception{
		log.info(this.getClass() + "user/userLoginProc start!!");
		
		String id = CmmUtil.nvl(request.getParameter("id"));
		String pwd = CmmUtil.nvl(request.getParameter("pwd"));

		UserDTO uDTO = new UserDTO();
		
		uDTO.setId(id);
		uDTO.setPwd(pwd);
		
		uDTO = userService.getLoginInfo(uDTO);
		
		log.info("uDTO null?" + (uDTO == null));
		String msg = "";
		String url = "";
		
		if(uDTO == null) {
			msg = "로그인 실패";
		} else {
			log.info("uDTO ID : " + uDTO.getId());
			log.info("uDTO PWD : " + uDTO.getPwd());
			log.info("uDTO NAME : " + uDTO.getName());
			msg = "로그인 성공";
			session.setAttribute("id", uDTO.getId());
			session.setAttribute("name", uDTO.getName());
		}
		
		url = "/";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		log.info(this.getClass() + "user/userLoginProc end!!");
		
		return "/user/redirect";
	}
	
	@RequestMapping(value="user/logOut.do")
	public String logOut(HttpSession session, Model model) throws Exception{
		log.info(this.getClass() + "user/logOut start!!");

		String msg = "";
		String url = "";
		msg = "로그아웃 성공";
		url = "/";
		
		session.invalidate(); // 세션 정보 초기화
		
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		log.info(this.getClass() + "user/loginOut end!!");
		
		return "/user/redirect";
	}		
}
