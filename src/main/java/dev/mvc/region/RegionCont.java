package dev.mvc.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.ai.AiVO;
import dev.mvc.ai.Aiurl;
import dev.mvc.board.Board;
import dev.mvc.board.BoardVO;
import dev.mvc.manager.ManagerVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import dev.mvc.gpa.Gpa;
import dev.mvc.manager.ManagerProcInter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/region")
@Controller
public class RegionCont {

  
  @Autowired
  @Qualifier("dev.mvc.region.RegionProc")
  
//  @Autowired
//  @Qualifier("dev.mvc.manager.ManagerProc")
  
  
  
  private RegionProcInter regionproc;
 
  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;
  
  public RegionCont() {
    System.out.println("-> RegionCont created.");
  }
  
  @GetMapping(value = "/create")
    public String create( Model model, RegionVO regionVO){
//    gpaVO.setBoardno(boardno);
//    model.addAttribute("boardno", boardno);
    
    
    
    return "th/region/create"; 
        
  }
  
  

  
  @PostMapping(value="/create")
  public String createregion( Model model, RegionVO regionVO, HttpSession session, String mid, RedirectAttributes ra) {

   
      String sessionMid = (String) session.getAttribute("mid");

      if (sessionMid == null) {
        return "redirect:/account/login_need";
    }
      
      
      
     
//      gpaVO.setGpascore(star);
//      gpaVO.setAccountno(accountno);
//      gpaVO.setBoardno(boardno);
     
      int cnt = regionproc.create(regionVO);

      if (cnt == 1) {
        
        
        model.addAttribute("regionVO", regionVO);
        return "redirect:/region/list";
      /**  return "redirect:/gpa/list?boardno=" + boardVO.getBoardno(); **/
        /**http://localhost:9093/board/read?boardno=7&word=&now_page=1
        redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             
    
       **/
          } else {
          
          ra.addFlashAttribute("error", "지역 생성에 실패했습니다.");
          
          return "redirect:/errorPage";     }
  }
  
  
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model,ManagerVO managerVO , RegionVO regionVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page ) {

    ArrayList<RegionVO> list = this.regionproc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);
    
    int search_count = this.regionproc.list_search_count(word);
    String paging = this.regionproc.pagingBox(now_page, word, "/region/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("regionVO", regionVO);
    model.addAttribute("managerVO", managerVO);
    
    return "th/region/list"; // templates/member/list.html
  }

  // react listcont
//  @GetMapping("/list")
//  public ResponseEntity<Map<String, Object>> list(
//          @RequestParam(name="word", defaultValue = "") String word, 
//          @RequestParam(name="now_page", defaultValue = "1") int now_page) {
//
//      Map<String, Object> response = new HashMap<>();
//
//      ArrayList<RegionVO> list = this.regionproc.list_search_paging(word, now_page, this.record_per_page);
//      response.put("list", list);
//
//      int search_count = this.regionproc.list_search_count(word);
//      String paging = this.regionproc.pagingBox(now_page, word, "/region/list", search_count, this.record_per_page, this.page_per_blocK);
//      response.put("paging", paging);
//      response.put("word", word);
//      response.put("now_page", now_page);
//
//      return ResponseEntity.ok(response);
//  }

  
  
//  @GetMapping(value = "/list")
//  public String list(HttpSession session, Model model,  regionVO regionVO) {
//
//    
//    
//    ArrayList<regionVO> list = this.regionproc.list();
//    model.addAttribute("list", list);
//    model.addAttribute("regionVO", regionVO);
//    
//  
//    return "th/region/list"; // templates/member/list.html
//  }
//  
//  
//  @GetMapping(value = "/list_paging")
//  public String list_paging(HttpSession session, Model model, @RequestParam(value = "boardno", required = false) Integer boardno,
//      regionVO gpaVO, BoardVO boardVO,@RequestParam(name = "word", defaultValue = "") String word,
//      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
//
//    gpaVO.getBoardno();
//    
//    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
//    model.addAttribute("menu", menu);
//    
//    word = Tool.checkNull(word).trim();
//     
//    HashMap<String, Object> map = new HashMap<>();
//    map.put("boardno", boardno);
//    map.put("word", word);
//    map.put("now_page", now_page);
//    
//    ArrayList<regionVO> list = this.gpaproc.list_search_paging(map);
//    model.addAttribute("list", list);
//    model.addAttribute("word", word);
//    model.addAttribute("gpaVO", gpaVO);
//    model.addAttribute("boardVO", boardVO);
//    
//    int search_count = this.gpaproc.list_cno_search_count(map);
//    String paging = this.gpaproc.pagingBox(boardno, now_page, word, "/gpa/list_paging", search_count, Gpa.RECORD_PER_PAGE, Gpa.PAGE_PER_BLOCK);
//    model.addAttribute("paging", paging);
//    model.addAttribute("now_page", now_page);
//    model.addAttribute("search_count", search_count);
//    
//    int no = search_count - ((now_page - 1) * Board.RECORD_PER_PAGE);
//    model.addAttribute("no", no);
//    
//   
//    
//    return "th/gpa/list_search_paging"; // templates/member/list.html
//  }
//  

  @GetMapping(value="/list_cno_search") 
  public String list_cno_search_paging(Model model, RegionVO regionVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    word = Tool.checkNull(word).trim();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("word", word);

     // 페이징 목록
    ArrayList<RegionVO> list = this.regionproc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.regionproc.list_search_count(word);
    String paging = this.regionproc.pagingBox(now_page, word, "/region/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/region/list"; // /templates/spice/list_search.html
  }
 
  @GetMapping(value = "/update")
  public String update(Model model,
                                HttpSession session,
                                @RequestParam("regiono") int regiono,
                                
                                RedirectAttributes ra
                                
                                ) {
    
    RegionVO regionVO = new RegionVO();
    
    regionVO.setRegiono(regiono);
    
   
    model.addAttribute("regionVO", regionVO);

System.out.println(regiono);
    
    return "th/region/update";
  }
  
  @PostMapping(value = "/update")
  public String update_gpa(Model model,
                          HttpSession session,
                          
                          @ModelAttribute("regionVO") RegionVO regionVO,
                          RedirectAttributes ra) {
    
  
   
    
    regionproc.update(regionVO); 
    
      ra.addAttribute("regiono", regionVO.getRegiono());
     

      return "redirect:/region/list";
  }
  
  
  
  @GetMapping(value = "/delete")
  public String delete(@RequestParam("regiono") int regiono, HttpSession session, Model model, RedirectAttributes ra, RegionVO regionVO
                       ) {
    
    System.out.println("GET /delete, regiono: " + regiono);
    model.addAttribute("regiono", regiono);
    model.addAttribute("regionVO", regionVO);
    
    
    return "th/region/delete";
  }
  
  @PostMapping(value = "/delete")
  public String delete(@RequestParam("regiono") int regiono, RegionVO regionVO) {
    
//        
    this.regionproc.delete(regiono);
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("word", word);
//
//    ra.addAttribute("word", word);
//    ra.addAttribute("now_page", now_page);
    
    return "redirect:/region/list";
  }   
}