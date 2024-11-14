package dev.mvc.recipe_step;

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


@RequestMapping("/recipe_step")
@Controller
public class Recipe_stepCont {

  
  @Autowired
  @Qualifier("dev.mvc.recipe_step.Recipe_stepProc")
  private Recipe_stepProcInter recipe_stepproc;
  
  @Autowired
  @Qualifier("dev.mvc.region.RegionProc")
  private RegionProcInter regionProc;


  
//  @Autowired
//  @Qualifier("dev.mvc.manager.ManagerProc")
  
  
  
  
 
  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;
  
  public Recipe_stepCont() {
    System.out.println("-> Recipe_stepCont created.");
  }
  
  @GetMapping(value = "/create")
    public String create( Model model, Recipe_stepVO recipe_stepVO){
//    gpaVO.setBoardno(boardno);
//    model.addAttribute("boardno", boardno);
    
    
    
    return "th/recipe_step/create"; 
        
  }
  
  

  
  @PostMapping(value="/create")
  public String createrecipe_step( Model model, Recipe_stepVO recipe_stepVO, HttpSession session, String mid, RedirectAttributes ra, @RequestParam("foodno") int foodno) {

    
    String file1 = ""; // 원본 파일명 image
    String file1saved = ""; // 저장된 파일명, image
    String thumb1 = ""; // preview image

    String upDir = Board.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    
    MultipartFile mf = recipe_stepVO.getSphotoNF();
    
    file1 = mf.getOriginalFilename();
    System.out.println("-> 원본 파일명 산출 sphoto: " + file1);

    long size1 = mf.getSize();
    if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
      if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb1 = Tool.preview(upDir, file1saved, 200, 150);
        }
    
        recipe_stepVO.setStep_img(upDir);
        recipe_stepVO.setStep_imgsaved(file1saved);
        recipe_stepVO.setThumb(thumb1);
        recipe_stepVO.setSsize(size1);
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
      
      
      
     recipe_stepVO.setFoodno(foodno);
//      gpaVO.setGpascore(star);
//      gpaVO.setAccountno(accountno);
//      gpaVO.setBoardno(boardno);
     
     
     Integer maxStepOrder = recipe_stepproc.selectMaxStepOrderByFoodno(foodno);
    
     if (maxStepOrder == null) {
       maxStepOrder = 0;
   }
     
     recipe_stepVO.setStep_order(maxStepOrder + 1);
     recipe_stepVO.setFoodno(foodno);
    
      int cnt = recipe_stepproc.create(recipe_stepVO);

      if (cnt == 1) {
        
        
        model.addAttribute("recipe_stepVO", recipe_stepVO);
        return "redirect:/regionfood/read?foodno=" + recipe_stepVO.getFoodno();
      /**  return "redirect:/gpa/list?boardno=" + boardVO.getBoardno(); **/
        /**http://localhost:9093/board/read?boardno=7&word=&now_page=1
        redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             
    
       **/
          } else {
          
          ra.addFlashAttribute("error", "지역 생성에 실패했습니다.");
          
          return "redirect:/errorPage";     }
  }
  
  
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model,ManagerVO managerVO , Recipe_stepVO recipe_stepVO,RegionVO regionVO,  @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page,@RequestParam int step_no ) {

    ArrayList <Recipe_stepVO> alist = this.recipe_stepproc.alist(step_no);
    model.addAttribute("alist", alist);
    
    return "th/recipe_step/list"; // templates/member/list.html
  }

  
  @GetMapping(value="/list_cno_search") 
  public String list_cno_search_paging(Model model, Recipe_stepVO recipe_stepVO, @RequestParam(name="word", defaultValue = "") String word,@RequestParam(name="step_no", defaultValue = "1") Integer step_no, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    word = Tool.checkNull(word).trim();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("word", word);

     // 페이징 목록
    ArrayList<Recipe_stepVO> list = this.recipe_stepproc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.recipe_stepproc.list_search_count(word);
    String paging = this.recipe_stepproc.pagingBox(step_no,now_page, word, "/recipe_step/list", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/recipe_step/list"; // /templates/spice/list_search.html
  }
  
  @GetMapping(value = "/read")
  public String read(Model model, @RequestParam("step_no") int step_no
  ) {

   
    Recipe_stepVO recipe_stepVO = this.recipe_stepproc.read(step_no);
    long ssize = recipe_stepVO.getSsize();
    String ssize_label = Tool.unit(ssize);
    recipe_stepVO.setSsize_label(ssize_label);
    model.addAttribute("recipe_stepVO", recipe_stepVO);
    
    // 조회에서 화면 하단에 출력
    // ArrayList<ReplyVO> reply_list = this.replyProc.list_board(boardno);
    // mav.addObject("reply_list", reply_list);

  

    return "th/recipe_step/read";
  }
  
 
  @GetMapping(value = "/update")
  public String update(Model model,
                                HttpSession session,
                                @RequestParam("step_no") int step_no,
                                
                                RedirectAttributes ra
                                
                                ) {
    
    Recipe_stepVO recipe_stepVO = new Recipe_stepVO();
    
    recipe_stepVO.setStep_no(step_no);
    
   
    model.addAttribute("recipe_stepVO", recipe_stepVO);

    
    return "th/recipe_step/update";
  }
  
  @PostMapping(value = "/update")
  public String update_gpa(Model model,
                          HttpSession session,
                          
                          @ModelAttribute("recipe_stepVO") Recipe_stepVO recipe_stepVO,
                          RedirectAttributes ra) {
    
  
   
    
    recipe_stepproc.update(recipe_stepVO); 
    
      ra.addAttribute("step_no", recipe_stepVO.getStep_no());
     

      return "redirect:/recipe_step/list";
  }
  
  
  
  @GetMapping(value = "/delete")
  public String delete(@RequestParam("step_no") int step_no, HttpSession session, Model model, RedirectAttributes ra, Recipe_stepVO recipe_stepVO
                       ) {
    
    
    model.addAttribute("step_no", step_no);
    model.addAttribute("recipe_stepVO", recipe_stepVO);
    
    
    return "th/recipe_step/delete";
  }
  
  @PostMapping(value = "/delete")
  public String delete(@RequestParam("step_no") int step_no, Recipe_stepVO recipe_stepVO) {
    
//        
    this.recipe_stepproc.delete(step_no);
//    
//    HashMap<String, Object> hashMap = new HashMap<String, Object>();
//    hashMap.put("word", word);
//
//    ra.addAttribute("word", word);
//    ra.addAttribute("now_page", now_page);
    
    return "redirect:/recipe_step/list";
  }   
  
  
  
}