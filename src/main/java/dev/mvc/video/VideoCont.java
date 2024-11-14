package dev.mvc.video;

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


@RequestMapping("/video")
@Controller
public class VideoCont {

  
  @Autowired
  @Qualifier("dev.mvc.video.VideoProc")
  
//  @Autowired
//  @Qualifier("dev.mvc.manager.ManagerProc")
  
  
  
  private VideoProcInter videoproc;
 
  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;
  
  public VideoCont() {
    System.out.println("-> VideoCont created.");
  }
  
  @GetMapping(value = "/create")
    public String create( Model model, VideoVO videoVO){
//    gpaVO.setBoardno(boardno);
//    model.addAttribute("boardno", boardno);
    
    
    
    return "th/video/create"; 
        
  }
  
  

  
  @PostMapping(value="/create")
  public String createvideo( Model model, VideoVO videoVO, HttpSession session, String mid, RedirectAttributes ra) {

   
      String sessionMid = (String) session.getAttribute("mid");

      if (sessionMid == null) {
        return "redirect:/account/login_need";
    }
      
      VideoVO videoVO2 = videoproc.readById(sessionMid);
      int managerno = videoVO2.getManagerno();

      System.out.println(managerno);
      
      
      videoVO.setManagerno(managerno);
//      gpaVO.setGpascore(star);
//      gpaVO.setAccountno(accountno);
//      gpaVO.setBoardno(boardno);
     
      int cnt = videoproc.create(videoVO);

      if (cnt == 1) {
        
        
        model.addAttribute("videoVO", videoVO);
        return "redirect:/video/list";
      /**  return "redirect:/gpa/list?boardno=" + boardVO.getBoardno(); **/
        /**http://localhost:9093/board/read?boardno=7&word=&now_page=1
        redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             
    
       **/
          } else {
          
          ra.addFlashAttribute("error", "video 생성에 실패했습니다.");
          
          return "redirect:/errorPage";     }
  }
  
  
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model,ManagerVO managerVO , VideoVO videoVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page ) {

    ArrayList<VideoVO> list = this.videoproc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);
    
    int search_count = this.videoproc.list_search_count(word);
    String paging = this.videoproc.pagingBox(now_page, word, "/video/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("videoVO", videoVO);
    model.addAttribute("managerVO", managerVO);
    
    return "th/video/list"; // templates/member/list.html
  }

  
  
  
//  @GetMapping(value = "/list")
//  public String list(HttpSession session, Model model,  VideoVO videoVO) {
//
//    
//    
//    ArrayList<VideoVO> list = this.videoproc.list();
//    model.addAttribute("list", list);
//    model.addAttribute("videoVO", videoVO);
//    
//  
//    return "th/video/list"; // templates/member/list.html
//  }
//  
//  
//  @GetMapping(value = "/list_paging")
//  public String list_paging(HttpSession session, Model model, @RequestParam(value = "boardno", required = false) Integer boardno,
//      VideoVO gpaVO, BoardVO boardVO,@RequestParam(name = "word", defaultValue = "") String word,
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
//    ArrayList<VideoVO> list = this.gpaproc.list_search_paging(map);
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
  public String list_cno_search_paging(Model model, VideoVO videoVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    word = Tool.checkNull(word).trim();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("word", word);

     // 페이징 목록
    ArrayList<VideoVO> list = this.videoproc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.videoproc.list_search_count(word);
    String paging = this.videoproc.pagingBox(now_page, word, "/video/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/video/list"; // /templates/spice/list_search.html
  }
 
  @GetMapping(value = "/update")
  public String update(Model model,
                                HttpSession session,
                                @RequestParam("videono") int videono,
                                
                                RedirectAttributes ra
                                
                                ) {
    
    VideoVO videoVO = new VideoVO();
    
    videoVO.setVideono(videono);
    
   
    model.addAttribute("videoVO", videoVO);

System.out.println(videono);
    
    return "th/video/update";
  }
  
  @PostMapping(value = "/update")
  public String update_gpa(Model model,
                          HttpSession session,
                          
                          @ModelAttribute("videoVO") VideoVO videoVO,
                          RedirectAttributes ra) {
    
  
   
    
    videoproc.update(videoVO); 
    
      ra.addAttribute("videono", videoVO.getVideono());
     

      return "redirect:/video/list";
  }
  
  
  
  @GetMapping(value = "/delete")
  public String delete(@RequestParam("videono") int videono, HttpSession session, Model model, RedirectAttributes ra, VideoVO videoVO
                       ) {
    
    System.out.println("GET /delete, videono: " + videono);
    model.addAttribute("videono", videono);
    model.addAttribute("videoVO", videoVO);
    
    
    return "th/video/delete";
  }
  
  @PostMapping(value = "/delete")
  public String delete(@RequestParam("videono") int videono, VideoVO videoVO) {
    
//        
    this.videoproc.delete(videono);
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("word", word);
//
//    ra.addAttribute("word", word);
//    ra.addAttribute("now_page", now_page);
    
    return "redirect:/video/list";
  }   
}