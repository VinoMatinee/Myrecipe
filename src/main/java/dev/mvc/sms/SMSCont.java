package dev.mvc.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.account.AccountProcInter;
import dev.mvc.account.AccountVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.tool.Security;

@Controller
public class SMSCont {
  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;
  
  @Autowired
  @Qualifier("dev.mvc.account.AccountProc")
  private AccountProcInter accountProc;

  @Autowired
  Security security;

  public SMSCont() {
    System.out.println("-> SMSCont created.");
  }

  @PostMapping(value = "/find_passwd")
  public String find_passwd_proc(HttpSession session, String aname, String aid, AccountVO accountVO, Model model,
      RedirectAttributes ra) {
    int cnt = this.accountProc.check_user_passwd(aid, aname);

    if (cnt == 1) {
      model.addAttribute("aid", accountVO.getAid());
      model.addAttribute("aname", accountVO.getAname());

//    System.out.println(aid);
//    System.out.println(aname);

      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("aid", aid);
      map.put("aname", aname);
      int no = this.accountProc.accountno_return(map);
//    System.out.println("no: " + no);
      model.addAttribute("no", no);

      session.setAttribute("no", no);

      return "sms/form";
    } else {
      model.addAttribute("code", "find_fail");
      return "th/account/find_aid";
    }
  }

  // http://localhost:9091/sms/form.do
  /**
   * 사용자의 전화번호 입력 화면
   * 
   * @return
   */
  @RequestMapping(value = { "/sms/form.do" }, method = RequestMethod.GET)
  public ModelAndView form(HttpSession session) {

    int no = (int) session.getAttribute("no");
    System.out.println("->no: " + no);

    ModelAndView mav = new ModelAndView();
    mav.setViewName("/sms/form"); // /WEB-INF/views/sms/form.jsp

    return mav;
  }

  // http://localhost:9091/sms/proc.do
  /**
   * 사용자에게 인증 번호를 생성하여 전송
   * 
   * @param session
   * @param request
   * @return
   */
  @RequestMapping(value = { "/sms/proc.do" }, method = RequestMethod.POST)
  public ModelAndView proc(HttpSession session, HttpServletRequest request) {

    int no = (int) session.getAttribute("no");
    System.out.println("->->no: " + no);

    ModelAndView mav = new ModelAndView();

    // ------------------------------------------------------------------------------------------------------
    // 0 ~ 9, 번호 6자리 생성
    // ------------------------------------------------------------------------------------------------------
    String auth_no = "";
    Random random = new Random();
    for (int i = 0; i <= 5; i++) {
      auth_no = auth_no + random.nextInt(10); // 0 ~ 9, 번호 6자리 생성
    }
    session.setAttribute("auth_no", auth_no); // 생성된 번호를 비교를위하여 session 에 저장
    // System.out.println(auth_no);
    // ------------------------------------------------------------------------------------------------------

    System.out.println("-> IP:" + request.getRemoteAddr()); // 접속자의 IP 수집

    // 번호, 전화 번호, ip, auth_no, 날짜 -> SMS Oracle table 등록, 문자 전송 내역 관리 목적으로 저장(필수 아니나
    // 권장)

    String msg = "[www.resort.co.kr] [" + auth_no + "]을 인증번호란에 입력해주세요.";
    System.out.print(msg);

    mav.addObject("msg", msg); // request.setAttribute("msg")
    mav.setViewName("/sms/proc"); // /WEB-INF/views/sms/proc.jsp

    return mav;
  }

  // http://localhost:9091/sms/proc_next.do
  /**
   * 사용자가 수신받은 인증번호 입력 화면
   * 
   * @return
   */
  @RequestMapping(value = { "/sms/proc_next.do" }, method = RequestMethod.GET)
  public ModelAndView proc_next(HttpSession session) {
    
    

    int no = (int) session.getAttribute("no");
    System.out.println("->->->no: " + no);

    ModelAndView mav = new ModelAndView();
    mav.setViewName("/sms/proc_next"); // /WEB-INF/views/sms/proc_next.jsp

    return mav;
  }

  // http://localhost:9091/sms/confirm.do
  /**
   * 문자로 전송된 번호와 사용자가 입력한 번호를 비교한 결과 화면
   * 
   * @param session 사용자당 할당된 서버의 메모리
   * @param auth_no 사용자가 입력한 번호
   * @return
   */
  @RequestMapping(value = { "/sms/confirm.do" }, method = RequestMethod.POST)
  public ModelAndView confirm(HttpSession session, String auth_no, Model model) {
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

    int no = (int) session.getAttribute("no");
    System.out.println("->->->->no: " + no);

    ModelAndView mav = new ModelAndView();

    String session_auth_no = (String) session.getAttribute("auth_no"); // 사용자에게 전송된 번호 session에서 꺼냄

    String msg = "";

    if (session_auth_no.equals(auth_no)) {
      msg = "ID공개 페이지나 패스워드 분실시 새로운 패스워드 입력 화면으로 이동합니다.<br><br>";
      msg += "패스워드 수정 화면등 출력";

      mav.setViewName("th/account/update_passwd_form"); // /WEB-INF/views/sms/confirm.jsp

      return mav;

    } else {
      msg = "입력된 번호가 일치하지않습니다. 다시 인증 번호를 요청해주세요.";
      msg += "<br><br><A href='./form.do'>인증번호 재요청</A>";
    }

    mav.addObject("msg", msg); // request.setAttribute("msg")
    mav.setViewName("/sms/confirm"); // /WEB-INF/views/sms/confirm.jsp

    return mav;
  }

  @PostMapping(value = "/update_passwd")
  public String update_passwd_proc(HttpSession session, Model model, String current_apasswd, String apasswd) {

    int no = (int)session.getAttribute("no");
//    System.out.println("->->->->->accountno: " + accountno);
//    System.out.println("->current_apasswd" + current_apasswd);

//    System.out.println(map);
    

  
//    System.out.println("->>cnt: " + cnt);
    


      HashMap<String, Object> map_new_passwd = new HashMap<String, Object>();
      map_new_passwd.put("accountno", no);
      map_new_passwd.put("apasswd", this.security.aesEncode(apasswd)); // 새로운 패스워드
      
      int passwd_change_cnt = this.accountProc.passwd_update(map_new_passwd);
      if(passwd_change_cnt == 1) {
        model.addAttribute("code", "find_success");
      }else {
        model.addAttribute("code", "find_passwd_fail");
      } 
    return "th/account/update_passwd";
  }
  
  
}
