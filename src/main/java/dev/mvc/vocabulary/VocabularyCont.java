package dev.mvc.vocabulary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.ai.AiVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.gpa.GpaVO;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;

import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "vocabulary")
@Controller
public class VocabularyCont {
  @Autowired
  @Qualifier("dev.mvc.vocabulary.VocabularyProc")
  private VocabularyProcInter vocabularyProc;

  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc")
  private ManagerProcInter managerProc;

  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;
  
  
  public VocabularyCont() {
    System.out.println("-> VocabularyCont created.");
  }

  @GetMapping(value = "/create")
  public String create(Model model, HttpSession session) {
    if (this.managerProc.isAdmin(session)) {
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

//    int managerno = (int) session.getAttribute("managerno");
//    System.out.println("->managerno: " + managerno);
    return "th/vocabulary/create";
    }
    return "redirect:/account/login_need";
   
  }

  @PostMapping(value = "/create")
  public String create_proc(VocabularyVO vocabularyVO, Model model, HttpSession session) {
    int cnt = this.vocabularyProc.create_voca(vocabularyVO);
    System.out.println("-> cnt: " + cnt);
    if (cnt == 1) {

      model.addAttribute("code", "create_success");
      model.addAttribute("voca", vocabularyVO.getVoca());
    } else {
      model.addAttribute("code", "create_fail");
    }

    return "th/vocabulary/msg";
  }

  @GetMapping(value = "/list")
  public String list(Model model, HttpSession session, VocabularyVO vocabularyVO, 
      @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page ) {
    
    if (this.managerProc.isAdmin(session)) {
      ArrayList<VocabularyVO> list = this.vocabularyProc.list_search_paging(word, now_page, this.record_per_page);
      System.out.println("list: " + list);
      model.addAttribute("list", list);
      
      int search_count = this.vocabularyProc.list_search_count(word);
      String paging = this.vocabularyProc.pagingBox(now_page, word, "/vocabulary/list", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("vocabularyVO", vocabularyVO);
      
      return "th/vocabulary/list";
    }
    return "redirect:/account/login_need";
   
  }
  
  
  @GetMapping(value="/list_cno_search") 
  public String list_cno_search_paging(Model model, VocabularyVO vocabularyVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    word = Tool.checkNull(word).trim();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("word", word);

     // 페이징 목록
    ArrayList<VocabularyVO> list = this.vocabularyProc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.vocabularyProc.list_search_count(word);
    String paging = this.vocabularyProc.pagingBox(now_page, word, "/vocabulary/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/vocabulary/list"; // /templates/spice/list_search.html
  }
  
//  @GetMapping(value = "/list")
//  public String list(Model model, HttpSession session) {
//    if (this.managerProc.isAdmin(session)) {
//      ArrayList<VocabularyVO> list = this.vocabularyProc.list();
//      System.out.println("list: " + list);
//      model.addAttribute("list", list);
//      return "th/vocabulary/list";
//    }
//    return "redirect:/account/login_need";
//   
//  }

  /**
   * 단어 수정
   * @param model
   * @param session
   * @param gpano
   * @param ra
   * @return
   */
  @GetMapping(value = "/update")
  public String update(Model model,
                                HttpSession session,
                                @RequestParam("vocano") int vocano,
                                
                                RedirectAttributes ra
                                
                                ) {
    
    VocabularyVO vocabularyVO = new VocabularyVO();
    
    vocabularyVO.setVocano(vocano);
    
   
    model.addAttribute("vocabularyVO", vocabularyVO);

System.out.println(vocano);
    
    return "th/vocabulary/update";
  }
  
  
  @PostMapping(value = "/update")
  public String update_gpa(Model model,
                          HttpSession session,
                          
                          @ModelAttribute("vocabularyVO") VocabularyVO vocabularyVO,
                          RedirectAttributes ra) {
      // 여기서는 간단히 데이터를 수정하는 로직을 가정합니다
      // 실제로는 데이터를 수정하는 서비스나 DAO 계층을 호출해야 합니다
   
  
    
  
    
    vocabularyProc.update(vocabularyVO); // 예를 들어, aiService.updateAi 메서드를 호출하여 데이터를 업데이트합니다

      ra.addAttribute("vocano", vocabularyVO.getVocano());
      ra.addAttribute("mean", vocabularyVO.getMean());
      

      return "redirect:/vocabulary/list";
  }
  
  
  /**
   * 단어 삭제
   * @param gpano
   * @param session
   * @param model
   * @param ra
   * @param gpaVO
   * @return
   */
  
  @GetMapping(value = "/delete")
  public String delete(@RequestParam("vocano") int vocano, HttpSession session, Model model, RedirectAttributes ra, VocabularyVO vocabularyVO
                       ) {
    
    System.out.println("GET /delete, vocano: " + vocano);
    model.addAttribute("vocano", vocano);
    model.addAttribute("vocabularyVO", vocabularyVO);
    
    
    return "th/vocabulary/delete";
  }
  
  @PostMapping(value = "/delete")
  public String delete(@RequestParam("vocano") int vocano, VocabularyVO vocabularyVO) {
    
//        
    this.vocabularyProc.delete(vocano);
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("word", word);
//
//    ra.addAttribute("word", word);
//    ra.addAttribute("now_page", now_page);
    
    return "redirect:/vocabulary/list";
  }   
  

}
