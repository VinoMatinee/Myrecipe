package dev.mvc.answer;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.ai.AiProcInter;
import dev.mvc.ai.AiVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.search.SearchProcInter;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/answer")
@Controller
public class AnswerCont {
  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;

  
  @Autowired
  @Qualifier("dev.mvc.answer.AnswerProc")
  private AnswerProcInter answerProc;
  
  @Autowired
  @Qualifier("dev.mvc.ai.AiProc")
  private AiProcInter aiProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc")
  private ManagerProcInter managerProc;
  
  @Autowired
  @Qualifier("dev.mvc.search.SearchProc")
  private SearchProcInter searchProc;
  
  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 5;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;
  
  public AnswerCont() {
    System.out.println("-> AnswerCont created");
  }
  
  @GetMapping(value = "/list")
  public String answer_list(HttpSession session, Model model, AiVO aiVO, 
                                 @RequestParam(name="word", defaultValue = "") String word, 
                                 @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    
    Integer accountno = (Integer) session.getAttribute("accountno");
    System.out.println(accountno);
    
/** 검색 기록 저장 및 검색 빈도 확인
 * 
 */
    if (word != null && !word.trim().isEmpty()) {
    this.searchProc.search_word(word);
    }
    
    if (this.managerProc.isAdmin(session)) {
      
      ArrayList <AnswerVO> list = this.answerProc.list_all_search_paging(word, now_page, this.record_per_page);
     
      
      model.addAttribute("list", list) ;
      
      int search_count = this.answerProc.list_all_search_count(word);
      String paging = this.answerProc.pagingBox(now_page, now_page, word, "/answer/list", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);
      
    }else if(this.managerProc.isMember(session)){
      ArrayList <AnswerVO> list = this.answerProc.list_search_paging(accountno, word, now_page, this.record_per_page);
      
      model.addAttribute("list", list) ;
      
      HashMap<String, Object> map = new HashMap<>();
      map.put("accountno", accountno);
      map.put("word", word);
      
      int search_count = this.answerProc.list_search_count(map);
      String paging = this.answerProc.userpagingBox(accountno, now_page, word, "/answer/list", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

    }else {
      return "redirect:/account/login_need";
    } 
    return "th/answer/list";
  }

}
