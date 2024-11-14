package dev.mvc.lunch;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequestMapping("/lunch")
@Controller
public class LunchCont {

  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;

  @Autowired
  @Qualifier("dev.mvc.lunch.LunchProc")
  private LunchProcInter lunchProc;

  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc")
  private ManagerProcInter managerProc;

  public LunchCont() {
    System.out.println("-> LunchCont created");
  }
  

  /**
  * http://localhost:9093/lunch/list
  * 리스트
  * @param session
  * @param model
  * @param lunchVO
  * @return
  */
  @GetMapping(value = "/list")
  public String lunch_list(HttpSession session, Model model, LunchVO lunchVO) {
  
      // 사용자 검증
      if (session.getAttribute("accountno") != null || session.getAttribute("managerno") != null) {

        ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
        model.addAttribute("menu", menu);
  
          // 매니저 검증
          if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
              ArrayList<LunchVO> list = this.lunchProc.list_all();
              model.addAttribute("list", list);

          } else { // 일반 사용자로 로그인한 경우
              Integer sessionAccountno = (Integer) session.getAttribute("accountno");
              ArrayList<LunchVO> list = this.lunchProc.list(sessionAccountno);
              model.addAttribute("list", list);
              model.addAttribute("accountno", sessionAccountno);
          }
          
          return "th/lunch/list";
      } else {
          // 로그인되지 않은 경우
          return "redirect:/account/login_need";
      }
  }
  
  /**
   * 삭제 처리
   * @param model
   * @param l_no
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(HttpSession session, Model model, Integer l_no) {
    if (this.managerProc.isMemberAdmin(session)) {
      int cnt = this.lunchProc.delete(l_no); // 삭제
      model.addAttribute("cnt" , cnt);
      if (cnt == 1) {
        return "redirect:/lunch/list";
      } else {
        model.addAttribute("code", "delete_fail");
        return "th/lunch/msg"; // /templates/lunch/msg.html
      }
    }
    return "redirect:/account/login_need"; // /account/login_need.html
  }

}
