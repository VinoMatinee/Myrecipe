package dev.mvc.crudcate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.account.AccountProcInter;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/crudcate")
@Controller
public class CrudcateCont {
  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;

  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 5;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;

  public CrudcateCont() {
    System.out.println("-> CrudCateCont created.");  
  }

    @Autowired
    @Qualifier("dev.mvc.account.AccountProc")
    private AccountProcInter accountProc;

    @Autowired
    @Qualifier("dev.mvc.manager.ManagerProc")
    private ManagerProcInter managerProc;

  /**
   * Create 폼 처리
   * @param model
   * @param crudcateVO
   * @param result
   * @param word
   * @param now_page
   * @return
   */
  @PostMapping(value="/list_search")
  public String create_process(HttpSession session, Model model, CrudcateVO crudcateVO, BindingResult result, 
                                @RequestParam(name="word", defaultValue = "") String word, 
                                @RequestParam(name="now_page", defaultValue = "1") int now_page) {
      if (session.getAttribute("managerno") != null) {
          // 페이징 목록
          ArrayList<CrudcateVO> list = this.crudcateProc.list_search_paging(word, now_page, this.record_per_page);
          model.addAttribute("list", list);
  
          // 페이징 버튼 목록
          int search_count = this.crudcateProc.list_search_count(word);
  
          // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
          int no = (search_count - (now_page - 1) * this.record_per_page);
          model.addAttribute("no", no);
          
          String paging = this.crudcateProc.pagingBox(now_page, word, "/crudcate/list_search", search_count, this.record_per_page, this.page_per_blocK);
          model.addAttribute("paging", paging);
          
          model.addAttribute("word", word);
          model.addAttribute("now_page", now_page);
          model.addAttribute("search_count", search_count);
  
          int cnt = this.crudcateProc.create(crudcateVO); 
          model.addAttribute("cnt", cnt);
          
          if (cnt == 1) {
              model.addAttribute("code", "create_success");
              model.addAttribute("name", crudcateVO.getName());
              model.addAttribute("namesub", crudcateVO.getNamesub());
              model.addAttribute("namesubsub", crudcateVO.getNamesubsub());
              model.addAttribute("seqno", crudcateVO.getSeqno());
          } else {
              model.addAttribute("code", "create_fail");
          }
          
          // create 작업 후 list_search 페이지로 리다이렉트
          return "redirect:/crudcate/list_search";
      }
      
      return "redirect:/account/login_need"; // /account/login_need.html로 리다이렉트
  }
  
    

  /**
   * http://localhost:9093/crudcate/read/1?word=&now_page=1
   * Read 폼
   * @param model
   * @param crudcateno
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/read/{crudcateno}")
  public String read(HttpSession session, Model model, @PathVariable("crudcateno") Integer crudcateno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
      CrudcateVO crudcateVO = this.crudcateProc.read(crudcateno);
      model.addAttribute("crudcateVO", crudcateVO);

      ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
      model.addAttribute("menu", menu);

      // 페이징 목록
      ArrayList<CrudcateVO> list = this.crudcateProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.crudcateProc.list_search_count(word);
      String paging = this.crudcateProc.pagingBox(now_page, word, "/crudcate/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/crudcate/read";
    }
    return "redirect:/account/login_need"; // /account/login_need.html
  }
  
  /**
   * http://localhost:9093/crudcate/update/1?word=&now_page=1
   * Update 폼
   * @param model
   * @param crudcateno
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/update/{crudcateno}")
  public String update(HttpSession session, Model model, @PathVariable("crudcateno") Integer crudcateno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
      CrudcateVO crudcateVO = this.crudcateProc.read(crudcateno);
      model.addAttribute("crudcateVO", crudcateVO);

      ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
      model.addAttribute("menu", menu);

      // 페이징 목록
      ArrayList<CrudcateVO> list = this.crudcateProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.crudcateProc.list_search_count(word);
      String paging = this.crudcateProc.pagingBox(now_page, word, "/crudcate/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/crudcate/update";
    }    
    return "redirect:/account/login_need"; // /account/login_need.html
  }


  /**
   * Update 폼 처리
   * @param model
   * @param crudcateVO
   * @param result
   * @param word
   * @param now_page
   * @return
   */
  @PostMapping(value="/update")
  public String update_process(HttpSession session, Model model, CrudcateVO crudcateVO, BindingResult result, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
      if (result.hasErrors()) {

      // 페이징 목록
      ArrayList<CrudcateVO> list = this.crudcateProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.crudcateProc.list_search_count(word);
      String paging = this.crudcateProc.pagingBox(now_page, word, "/crudcate/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/crudcate/update"; // 다시 수정 폼으로 이동
      }
        
      int cnt = this.crudcateProc.update(crudcateVO); // 수정
      if (cnt == 1) {
        return "redirect:/crudcate/update/" + crudcateVO.getCrudcateno() + "?word=" + Tool.encode(word) + "&now_page=" + now_page;
      } else {
        model.addAttribute("code", "update_fail");
        return "th/crudcate/msg"; // /templates/crudcate/msg.html
      }
    }
    return "redirect:/account/login_need"; // /account/login_need.html
  }

  /**
   * http://localhost:9093/crudcate/delete/19?word=&now_page=3
   * Delete 폼
   * @param model
   * @param crudcateno
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/delete/{crudcateno}")
  public String delete(HttpSession session, Model model, @PathVariable("crudcateno") Integer crudcateno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
      CrudcateVO crudcateVO = this.crudcateProc.read(crudcateno);
      model.addAttribute("crudcateVO", crudcateVO);

      ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
      model.addAttribute("menu", menu);

      // 페이징 목록
      ArrayList<CrudcateVO> list = this.crudcateProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.crudcateProc.list_search_count(word);
      String paging = this.crudcateProc.pagingBox(now_page, word, "/crudcate/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      return "th/crudcate/delete";
    }    
    return "redirect:/account/login_need"; // /account/login_need.html
  }


  /**
   * Delete 폼 처리
   * @param model
   * @param crudcateno
   * @param word
   * @param now_page
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(HttpSession session, Model model, Integer crudcateno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
      CrudcateVO crudcateVO = this.crudcateProc.read(crudcateno);
      model.addAttribute("crudcateVO", crudcateVO);

      // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야함.
      int search_cnt = this.crudcateProc.list_search_count(word);
      if (search_cnt % this.record_per_page == 0) {
        now_page = now_page - 1;
        if (now_page < 1) {
          now_page = 1;
        }
      }
        
      int cnt = this.crudcateProc.delete(crudcateno); // 삭제
      model.addAttribute("cnt" , cnt);
      if (cnt == 1) {
        return "redirect:/crudcate/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
      } else {
        model.addAttribute("code", "delete_fail");
        return "th/crudcate/msg"; // /templates/crudcate/msg.html
      }
    }   
     return "redirect:/account/login_need"; // /account/login_need.html
  }

  /**
   * 출력 순서 조절
   * @param model
   * @param crudcateno
   * @param crudcateVO
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/seqno_forward/{crudcateno}")
  public String seqno_forward(Model model, @PathVariable("crudcateno") Integer crudcateno, CrudcateVO crudcateVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    this.crudcateProc.seqno_forward(crudcateno);

    return "redirect:/crudcate/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
  }

  /**
   * 출력 순서 조절
   * @param model
   * @param crudcateno
   * @param crudcateVO
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/seqno_backward/{crudcateno}")
  public String seqno_backward(Model model, @PathVariable("crudcateno") Integer crudcateno, CrudcateVO crudcateVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    this.crudcateProc.seqno_backward(crudcateno);

    return "redirect:/crudcate/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
  }

  /**
   * 출력 보이기
   * @param model
   * @param crudcateno
   * @param crudcateVO
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/visible_y/{crudcateno}")
  public String visible_y(Model model, @PathVariable("crudcateno") Integer crudcateno, CrudcateVO crudcateVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    this.crudcateProc.visible_y(crudcateno);

    return "redirect:/crudcate/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
  }

  /**
   * 출력 숨기기
   * @param model
   * @param crudcateno
   * @param crudcateVO
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/visible_n/{crudcateno}")
  public String visible_n(Model model, @PathVariable("crudcateno") Integer crudcateno, CrudcateVO crudcateVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    this.crudcateProc.visible_n(crudcateno);

    return "redirect:/crudcate/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
  }

  /**
   * http://localhost:9093/crudcate/list_search
   * 전체 리스트
   * @param session
   * @param model
   * @param crudcateVO
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/list_search") 
  public String list_search_paging(HttpSession session, Model model, CrudcateVO crudcateVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    
    if (session.getAttribute("managerno") != null) { // 관리자로 로그인한 경우
      ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
      model.addAttribute("menu", menu);

      word = Tool.checkNull(word).trim();

      Map<String, Object> map = new HashMap<String, Object>();
      map.put("word", word);

      // 페이징 목록
      ArrayList<CrudcateVO> list = this.crudcateProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.crudcateProc.list_search_count(word);
      String paging = this.crudcateProc.pagingBox(now_page, word, "/crudcate/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/crudcate/list_search"; // /templates/crudcate/list_search.html
    }
    return "redirect:/account/login_need"; // /account/login_need.html
  }
}
