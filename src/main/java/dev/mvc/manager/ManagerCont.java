package dev.mvc.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.reflection.SystemMetaObject;

//import java.util.ArrayList;
//import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
// import dev.mvc.contents.Contents;
//import dev.mvc.cate.CateProcInter;
//import dev.mvc.cate.CateVOMenu;
import dev.mvc.tool.Security;
//import dev.mvc.tool.Tool;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/manager")
@Controller
public class ManagerCont {
  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc")
  private ManagerProcInter managerProc;
  
  
  @Autowired
  Security security;

  public ManagerCont() {
    System.out.println("-> ManagerCont created");
  }
  
  @GetMapping(value = "/checkID")
  @ResponseBody
  public String checkID(String mid) {
    System.out.println("-> id: " + mid);
    int cnt = this.managerProc.checkID_manager(mid);
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);

    return obj.toString();
  }
  
  /**
   * 회원 가입 폼
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/signin") // http://localhost:9091/account/create
  public String create_form(Model model, ManagerVO managerVO) {
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    
    return "th/manager/create";    // /template/member/create.html
  }
  
  /**
   * 회원가입 실행
   * @param model
   * @param managerVO
   * @return
   */
  @PostMapping(value="/signin_manager")
  public String create_proc(Model model, ManagerVO managerVO) {
    int checkID_cnt = this.managerProc.checkID_manager(managerVO.getMid());
    
    if (checkID_cnt == 0) {
      managerVO.setMgrade(1); // 관리자 등급 번호 1
      int cnt = this.managerProc.signin_manager(managerVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("mname", managerVO.getMname());
        model.addAttribute("mid", managerVO.getMid());
      } else {
        model.addAttribute("code", "create_fail");
      }
      
      model.addAttribute("cnt", cnt);
    } else { // id 중복
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "th/manager/create"; // /templates/member/msg.html
  }

  /**
   * 로그인
   * 
   * @param model
   * @param managerno 관리자 번호
   * @return 관리자 정보
   */
  @GetMapping(value = "/login")
  public String login_form(Model model) {
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    
    return "th/manager/login"; // templates/member/login.html
  }

  @GetMapping("/getsessiondata")
  public ResponseEntity<Map<String, Object>> getSessionData(HttpSession session) {
      Map<String, Object> sessionData = new HashMap<>();
      Integer accountNo = (Integer) session.getAttribute("managerno");
      String aid = (String) session.getAttribute("mid");
      // 필요한 세션 데이터를 sessionData에 추가
      sessionData.put("accountno", accountNo);
      sessionData.put("mid", aid);

      System.out.println("sessiondata: " + sessionData);
      
      return ResponseEntity.ok(sessionData);
  }
  
  /**
   * 로그인 처리
   * 
   * @param model
   * @param managerno 관리자 번호
   * @return 관리자 정보
   */
  @PostMapping(value = "/login_manager")
  public String login_proc(HttpSession session, Model model, String mid, String mpasswd) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("mid", mid);
    map.put("mpasswd", this.security.aesEncode(mpasswd));

    int cnt = this.managerProc.login_manager(map);
    System.out.println("-> login_proc cnt: " + cnt);

    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
      // id를 이용하여 회원 정보 조회
      ManagerVO managerVO = this.managerProc.readById(mid);
      session.setAttribute("managerno", managerVO.getManagerno());
      session.setAttribute("mid", managerVO.getMid());
      session.setAttribute("mname", managerVO.getMname());
      session.setAttribute("mgrade", managerVO.getMgrade());
      
      if (managerVO.getMgrade() >= 1 && managerVO.getMgrade() <= 10) {
        session.setAttribute("mgrade", "admin");
      } else if (managerVO.getMgrade() >= 11 && managerVO.getMgrade() <= 20) {
        session.setAttribute("mgrade", "account");
      } else if (managerVO.getMgrade() >= 21) {
        session.setAttribute("mgrade", "guest");
      }

      return "redirect:/";
    } else {
      model.addAttribute("code", "login_fail");
      return "th/account/msg";
    }

  }
  
  /**
   * 로그아웃
   * @param model
   * @param accountno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/logout")
  public String logout(HttpSession session, Model model) {
    session.invalidate();  // 모든 세션 변수 삭제
    return "redirect:/";
  }
  
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model) {
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    
    if (this.managerProc.isMemberAdmin(session)) {
      
    ArrayList<ManagerVO> list = this.managerProc.list();

    model.addAttribute("list", list);

    return "th/manager/list"; // templates/member/list.html
  }else {
    return "redirect:/account/login_need";  // redirect
  } 
  }

  /**
   * 조회
   * 
   * @param model
   * @param accountno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value = "/read")
  public String read(HttpSession session, Model model, int managerno) {
    
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    // 회원은 회원 등급만 처리, 관리자: 1 ~ 10, 사용자: 11 ~ 20
    // int gradeno = this.memberProc.read(memberno).getGrade(); // 등급 번호
    String mgrade = (String) session.getAttribute("mgrade"); // 등급: admin, member, guest

    // 사용자: member && 11 ~ 20
    // if (grade.equals("member") && (gradeno >= 11 && gradeno <= 20) && memberno ==
    // (int)session.getAttribute("memberno")) {
    if (mgrade.equals("admin") && managerno == (int) session.getAttribute("managerno")) {
      // System.out.println("-> read memberno: " + memberno);

      ManagerVO managerVO = this.managerProc.read(managerno);
      model.addAttribute("managerVO", managerVO);
      System.out.println("mgrade: " + mgrade);

      return "th/manager/read"; // templates/member/read.html

    } else if (mgrade.equals("admin")) {
      // System.out.println("-> read memberno: " + memberno);

      ManagerVO managerVO = this.managerProc.read(managerno);
      model.addAttribute("managerVO", managerVO);

      return "th/manager/read"; // templates/account/read.html
    } else {
      return "redirect:/member/login_form_need"; // redirect
    }

  }
  
  /**
   * 수정 처리
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/update_manager")
  public String update_proc(Model model, ManagerVO managerVO, RedirectAttributes ra) {
    
    
    int cnt = this.managerProc.update_manager(managerVO);
    if (cnt == 1) {
      
      model.addAttribute("code", "update_success");
      model.addAttribute("mname", managerVO.getMname());

      ra.addAttribute("managerno", managerVO.getManagerno());
      
      return "redirect:/manager/read"; // request -> param으로 접근 전환
    } else {
      model.addAttribute("code", "update_fail");
    }
    
    model.addAttribute("cnt", cnt);
    
    return "th/manager/msg"; // /templates/member/msg.html
  }
  
  /**
   * 삭제
   * @param model
   * @param manager 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/delete")
  public String delete(Model model, int managerno) {
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    
    System.out.println("-> delete managerno: " + managerno);
    
    ManagerVO managerVO = this.managerProc.read(managerno);
    model.addAttribute("managerVO", managerVO);
    
    return "th/manager/delete";  // templates/member/delete.html
  }
  
  /**
   * 관리자 Delete process
   * @param model
   * @param managerno 삭제할 레코드 번호
   * @return
   */
  @PostMapping(value="/delete_manager")
  public String delete_process(Model model, Integer managerno) {
    int cnt = this.managerProc.delete_manager(managerno);
    
    if (cnt == 1) {
      return "redirect:/manager/list";
    } else {
      model.addAttribute("code", "delete_fail");
      return "th/manager/msg"; // /templates/member/msg.html
    }
  }

}
