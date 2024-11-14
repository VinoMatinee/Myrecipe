package dev.mvc.regionfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import dev.mvc.recipe_step.Recipe_stepProcInter;
import dev.mvc.recipe_step.Recipe_stepVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVO;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import dev.mvc.vocabulary.VocabularyProcInter;
import dev.mvc.vocabulary.VocabularyVO;
import dev.mvc.gpa.Gpa;
import dev.mvc.gpa.GpaVO;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.region.RegionProcInter;
import dev.mvc.region.RegionVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/regionfood")
@Controller
public class RegionfoodCont {

  
  @Autowired
  @Qualifier("dev.mvc.regionfood.RegionfoodProc")
  private RegionfoodProcInter regionfoodproc;
  
  @Autowired
  @Qualifier("dev.mvc.region.RegionProc")
  private RegionProcInter regionProc;

  @Autowired
  @Qualifier("dev.mvc.recipe_step.Recipe_stepProc")
  private Recipe_stepProcInter recipe_stepproc;

  
//  @Autowired
//  @Qualifier("dev.mvc.manager.ManagerProc")
  
  
  
  
 
  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;
  
  public RegionfoodCont() {
    System.out.println("-> RegionfoodCont created.");
  }
  
  @GetMapping(value = "/create")
    public String create( Model model, RegionfoodVO regionfoodVO){
//    gpaVO.setBoardno(boardno);
//    model.addAttribute("boardno", boardno);
    
    
    
    return "th/regionfood/create"; 
        
  }
  
  

  
  @PostMapping(value="/create")
  public String createregionfood(HttpServletRequest request, Model model, RegionfoodVO regionfoodVO, HttpSession session, String mid, RedirectAttributes ra, @RequestParam("regiono") int regiono) {

    
    String file1 = ""; // 원본 파일명 image
    String file1saved = ""; // 저장된 파일명, image
    String thumb1 = ""; // preview image

    String upDir = Board.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    
    MultipartFile mf = regionfoodVO.getFphotoNF();
    
    file1 = mf.getOriginalFilename();
    System.out.println("-> 원본 파일명 산출 fphoto: " + file1);

    long size1 = mf.getSize();
    if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
      if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1saved, 200, 150);
        }
    
        
        regionfoodVO.setFoodimg_url(file1);
        regionfoodVO.setFoodimg_urlsaved(file1saved);
        regionfoodVO.setFthumb(thumb1);
        regionfoodVO.setFsize(size1);
      }else { // 전송 못하는 파일 형식
        ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/board/msg"); // msg.html, redirect parameter 적용
        return "redirect:/board/msg"; // Post -> Get - param...
      } } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }

      
      String sessionMid = (String) session.getAttribute("mid");

      if (sessionMid == null) {
        return "redirect:/account/login_need";
    }
      
      
      
     regionfoodVO.setRegiono(regiono);
//      gpaVO.setGpascore(star);
//      gpaVO.setAccountno(accountno);
//      gpaVO.setBoardno(boardno);
     
      int cnt = regionfoodproc.create(regionfoodVO);

      if (cnt == 1) {
        
        
        model.addAttribute("regionfoodVO", regionfoodVO);
        ra.addAttribute("regiono", regionfoodVO.getRegiono());
        return "redirect:/regionfood/list?regiono=" + regionfoodVO.getRegiono();
      /**  return "redirect:/gpa/list?boardno=" + boardVO.getBoardno(); **/
        /**http://localhost:9093/board/read?boardno=7&word=&now_page=1
        redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             
    
       **/
          } else {
          
          ra.addFlashAttribute("error", "지역 생성에 실패했습니다.");
          
          return "redirect:/errorPage";     }
  }
  
  
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model,ManagerVO managerVO , RegionfoodVO regionfoodVO,RegionVO regionVO,  @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page,@RequestParam int regiono ) {

    ArrayList <RegionfoodVO> alist = this.regionfoodproc.alist(regiono);
    model.addAttribute("alist", alist);
    
    return "th/regionfood/list"; // templates/member/list.html
  }

  
  @GetMapping(value="/list_cno_search") 
  public String list_cno_search_paging(Model model, RegionfoodVO regionfoodVO, @RequestParam(name="word", defaultValue = "") String word,@RequestParam(name="regiono", defaultValue = "1") Integer regiono, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    word = Tool.checkNull(word).trim();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("word", word);

     // 페이징 목록
    ArrayList<RegionfoodVO> list = this.regionfoodproc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.regionfoodproc.list_search_count(word);
    String paging = this.regionfoodproc.pagingBox(regiono,now_page, word, "/regionfood/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/regionfood/list"; // /templates/spice/list_search.html
  }
  
  @GetMapping(value = "/read")
  public String read(Model model, @RequestParam("foodno") int foodno
  ) {

   
    RegionfoodVO regionfoodVO = this.regionfoodproc.read(foodno);
    ArrayList<Recipe_stepVO> recipe_stepVO = this.recipe_stepproc.listfoodno(foodno);
    long fsize = regionfoodVO.getFsize();
    String fsize_label = Tool.unit(fsize);
    regionfoodVO.setFsize_label(fsize_label);
    model.addAttribute("regionfoodVO", regionfoodVO);
    model.addAttribute("recipe_stepVO", recipe_stepVO);    // 조회에서 화면 하단에 출력
    // ArrayList<ReplyVO> reply_list = this.replyProc.list_board(boardno);
    // mav.addObject("reply_list", reply_list);

  

    return "th/regionfood/read";
  }
  
 
  @GetMapping(value = "/update")
  public String update(Model model,
                                HttpSession session,
                                @RequestParam("foodno") int foodno,
                                
                                RedirectAttributes ra
                                
                                ) {
    
    RegionfoodVO regionfoodVO = new RegionfoodVO();
    
    regionfoodVO.setRegiono(foodno);
    
   
    model.addAttribute("regionfoodVO", regionfoodVO);

System.out.println(foodno);
    
    return "th/regionfood/update";
  }
  
  @PostMapping(value = "/update")
  public String update_gpa(Model model,
                          HttpSession session,
                          
                          @ModelAttribute("regionfoodVO") RegionfoodVO regionfoodVO,
                          RedirectAttributes ra) {
    
  
   
    
    regionfoodproc.update(regionfoodVO); 
    
      ra.addAttribute("foodno", regionfoodVO.getFoodno());
     

      return "redirect:/regionfood/list";
  }
  
  
  
  @GetMapping(value = "/delete")
  public String delete(@RequestParam("foodno") int foodno, HttpSession session, Model model, RedirectAttributes ra, RegionfoodVO regionfoodVO
                       ) {
    
    System.out.println("GET /delete, regiono: " + foodno);
    model.addAttribute("foodno", foodno);
    model.addAttribute("regionfoodVO", regionfoodVO);
    
    
    return "th/regionfood/delete";
  }
  
  @PostMapping(value = "/delete")
  public String delete(@RequestParam("foodno") int foodno, RegionfoodVO regionfoodVO) {
    
//        
    this.regionfoodproc.delete(foodno);
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("word", word);
//
//    ra.addAttribute("word", word);
//    ra.addAttribute("now_page", now_page);
    
    return "redirect:/regionfood/list";
  }   
  
  
  
}