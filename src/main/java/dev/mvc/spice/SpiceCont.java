package dev.mvc.spice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.recommend.RecProcInter;
import dev.mvc.recommend.RecVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/spice")
@Controller
public class SpiceCont {

  @Autowired
  @Qualifier("dev.mvc.spice.SpiceProc")
  private SpiceProcInter spiceProc;

  @Autowired
  @Qualifier("dev.mvc.recommend.RecProc")
  private RecProcInter recProc;

  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 9;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;

  public SpiceCont() {
    System.out.println("-> SpiceCont Created.");
  }

  @PostMapping(value="/create")
  public String create_process(Model model, SpiceVO spiceVO, HttpSession session, RedirectAttributes ra, BindingResult result, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;

    if (managerno != spiceVO.getManagerno()) {
      spiceVO.setManagerno(managerno);

      if (result.hasErrors()) {
        // 페이징 목록
        ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
        model.addAttribute("list", list);
  
        // 페이징 버튼 목록
        int search_count = this.spiceProc.list_search_count(word);
        String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
        model.addAttribute("paging", paging);
        model.addAttribute("word", word);
        model.addAttribute("now_page", now_page);
  
        // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
        int no = (search_count - (now_page - 1) * this.record_per_page);
        model.addAttribute("no", no);   
  
        return "th/spice/list_search";
      }
  
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 시작
        // ------------------------------------------------------------------------------
        String file1 = ""; // 원본 파일명 image
        String file1saved = ""; // 저장된 파일명, image
        String thumb1 = ""; // preview image
  
        String upDir = Spice.getUploadDir(); // 파일을 업로드할 폴더 준비
        System.out.println("-> upDir: " + upDir);
  
        // 전송 파일이 없어도 file1MF 객체가 생성됨.
        // <input type='file' class="form-control" name='file1MF' id='file1MF'
        // value='' placeholder="파일 선택">
        MultipartFile mf = spiceVO.getFileMF();
  
        file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
        System.out.println("-> 원본 파일명 산출: " + file1);
  
        long size1 = mf.getSize(); // 파일 크기
        if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
          if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
            // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
            file1saved = Upload.saveFileSpring(mf, upDir);
  
            if (Tool.isImage(file1saved)) { // 이미지인지 검사
              // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
              thumb1 = Tool.preview(upDir, file1saved, 200, 150);
            }
  
            spiceVO.setSpiceimg(file1); // 순수 원본 파일명
            spiceVO.setSpicesaved(file1saved); // 저장된 파일명(파일명 중복 처리)
            spiceVO.setSpicethumb(thumb1); // 원본이미지 축소판
            spiceVO.setSpicesize(size1); // 파일 크기
  
          } else { // 전송 못하는 파일 형식
            ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
            ra.addFlashAttribute("cnt", 0); // 업로드 실패
            ra.addFlashAttribute("url", "/spice/msg"); // msg.html, redirect parameter 적용
            return "redirect:/spice/msg"; // Post -> Get - param...
          }
        } else { // 글만 등록하는 경우
          System.out.println("-> 글만 등록");
        }
  
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
  
      
      int cnt = this.spiceProc.create(spiceVO);
  
      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("spicename", spiceVO.getSpicename());
        model.addAttribute("spicecont", spiceVO.getSpicecont());
        model.addAttribute("spiceprice", spiceVO.getSpiceprice());
        model.addAttribute("url", spiceVO.getUrl());

        return "redirect:/spice/list_search";
      } else {
        model.addAttribute("code", "create_fail");
      }
      
      model.addAttribute("cnt", cnt);
      return "th/spice/msg";
    } else {
      return "redirect:/manager/login";
    }
  }

  // @GetMapping(value = "/list")
  // public String list(Model model, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
  //   ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
  //   model.addAttribute("list", list);


  //   // 페이징 버튼 목록
  //   int search_count = this.spiceProc.list_search_count(word);
  //   String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list", search_count, this.record_per_page, this.page_per_blocK);
  //   model.addAttribute("paging", paging);
  //   model.addAttribute("word", word);
  //   model.addAttribute("now_page", now_page);
  //   return "th/spice/list";
  // }

  @GetMapping(value = "/list")
  public String list_cno_search_paging(HttpSession session, Model model,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

    // ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    // model.addAttribute("menu", menu);

    // SpiceVO spiceVO = this.spiceProc.read(spiceno);
    // model.addAttribute("spiceVO", spiceVO);

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("word", word);
    map.put("now_page", now_page);

    ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, record_per_page);

    model.addAttribute("list", list);

    // System.out.println("-> size: " + list.size());
    model.addAttribute("word", word);

    int search_count = this.spiceProc.list_search_count(word);
    String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list", search_count,
        Spice.record_per_page, Spice.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * Spice.record_per_page);
    model.addAttribute("no", no);

    return "th/spice/list";
  }
  

  @GetMapping(value="/read/{spiceno}")
  public String read(Model model, @PathVariable("spiceno") Integer spiceno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    SpiceVO spiceVO = this.spiceProc.read(spiceno);
    model.addAttribute("spiceVO", spiceVO);

    // 페이징 목록
    ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);


    // 페이징 버튼 목록
    int search_count = this.spiceProc.list_search_count(word);
    String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/spice/read";
  }

  @GetMapping(value="/cno_read")
  public String cno_read(Model model, int spiceno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    SpiceVO spiceVO = this.spiceProc.read(spiceno);
    model.addAttribute("spiceVO", spiceVO);

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("spiceno", spiceno);
    map.put("word", word);
    map.put("now_page", now_page);

    // 페이징 목록
    ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.spiceProc.list_search_count(word);
    String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/spice/cno_read";
  }

  @GetMapping(value="/update/{spiceno}")
  public String update(Model model, HttpSession session, @PathVariable("spiceno") Integer spiceno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;

    if (managerno != 0) {
      SpiceVO spiceVO = this.spiceProc.read(spiceno);
      model.addAttribute("spiceVO", spiceVO);

      // 페이징 목록
      ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.spiceProc.list_search_count(word);
      String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/spice/update";
    } else {
      return "redirect:/manager/login";
    }
  }

  /**
   * Update 폼 처리
   * @param model
   * @param spiceVO
   * @param result
   * @param word
   * @param now_page
   * @return
   */
  @PostMapping(value="/update")
  public String update_process(Model model, SpiceVO spiceVO, HttpSession session, RedirectAttributes ra, BindingResult result, 
                              @RequestParam(name="word", defaultValue = "") String word, 
                              @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (result.hasErrors()) {
      // 페이징 목록
      ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.spiceProc.list_search_count(word);
      String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/spice/update"; // 다시 수정 폼으로 이동
    }

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;

    if (managerno != 0) {
      String file1saved_old = "";

      SpiceVO spiceVO_old = this.spiceProc.read(spiceVO.getSpiceno());
      file1saved_old = spiceVO_old.getSpicesaved();  // 이전에 저장된 파일명
      String thumb_old = spiceVO_old.getSpicethumb();

      // 기존 파일 삭제
      MultipartFile mf = spiceVO.getFileMF();
      if (mf != null && !mf.isEmpty()) {
        String upDir = Spice.getUploadDir(); // 파일이 저장된 디렉토리 경로
        if (file1saved_old != null && !file1saved_old.isEmpty()) {
          Tool.deleteFile(upDir, file1saved_old);  // 이전에 저장된 파일 삭제
          Tool.deleteFile(upDir, thumb_old);       // 이전에 저장된 preview 이미지 삭제
        }

        // 파일 전송 시작
        String file1 = mf.getOriginalFilename(); // 원본 파일명
        long size1 = mf.getSize();  // 파일 크기
        String file1saved = Upload.saveFileSpring(mf, upDir); 
        String thumb1 = Tool.isImage(file1saved) ? Tool.preview(upDir, file1saved, 250, 200) : "";

        spiceVO.setSpiceimg(file1);
        spiceVO.setSpicesaved(file1saved);
        spiceVO.setSpicethumb(thumb1);
        spiceVO.setSpicesize(size1);
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        spiceVO.setSpiceimg(spiceVO_old.getSpiceimg());
        spiceVO.setSpicesaved(spiceVO_old.getSpicesaved());
        spiceVO.setSpicethumb(spiceVO_old.getSpicethumb());
        spiceVO.setSpicesize(spiceVO_old.getSpicesize());
      }

      int cnt = this.spiceProc.update(spiceVO); // 수정
      if (cnt == 1) {
        return "redirect:/spice/update/" + spiceVO.getSpiceno() + "?word=" + Tool.encode(word) + "&now_page=" + now_page;
      } else {
        model.addAttribute("code", "update_fail");
        return "th/spice/msg"; // /templates/spice/msg.html
      }
    } else {
      return "redirect:/manager/login";
    }
}

  /**
   * Delete 폼
   * @param model
   * @param spiceno
   * @param word
   * @param now_page
   * @return
   */
  @GetMapping(value="/delete/{spiceno}")
  public String delete(Model model, HttpSession session, @PathVariable("spiceno") Integer spiceno, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;

    if (managerno != 0) {
      SpiceVO spiceVO = this.spiceProc.read(spiceno);
      model.addAttribute("spiceVO", spiceVO);

      // 페이징 목록
      ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.spiceProc.list_search_count(word);
      String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
  ;
      return "th/spice/delete";
    } else {
      return "redirect:/manager/login";
    }
  }

  /**
   * Delete 폼 처리
   * @param model
   * @param spiceno
   * @param word
   * @param now_page
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(Model model, Integer spiceno, HttpSession session, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    SpiceVO spiceVO = this.spiceProc.read(spiceno);
    model.addAttribute("spiceVO", spiceVO);

    // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야함.
    int search_cnt = this.spiceProc.list_search_count(word);
    if (search_cnt % this.record_per_page == 0) {
      now_page = now_page - 1;
      if (now_page < 1) {
        now_page = 1;
      }
    }

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;

    if (managerno != 0) {
        SpiceVO spiceVO_old = this.spiceProc.read(spiceVO.getSpiceno());
        String file1saved_old = spiceVO_old.getSpicesaved();  // 이전에 저장된 파일명
        String thumb_old = spiceVO_old.getSpicethumb();

        if (file1saved_old != null && !file1saved_old.isEmpty()) {
          String upDir = Spice.getUploadDir(); // 파일이 저장된 디렉토리 경로
          Tool.deleteFile(upDir, file1saved_old);  // 이전에 저장된 파일 삭제
          Tool.deleteFile(upDir, thumb_old);       // 이전에 저장된 preview 이미지 삭제
        }

      int cnt = this.spiceProc.delete(spiceVO.getSpiceno());
      model.addAttribute("cnt" , cnt);
      if (cnt == 1) {
        return "redirect:/spice/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
      } else {
        model.addAttribute("code", "delete_fail");
        return "th/spice/msg"; // /templates/spice/msg.html
      }
    } else {
      return "redirect:/manager/login";
    }
  }

  @PostMapping(value="/good")
  @ResponseBody
  public String good(HttpSession session, @RequestBody RecVO recVO) {

    int spiceno = recVO.getSpiceno();

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("spiceno", recVO.getSpiceno());
    map.put("accountno", recVO.getAccountno());
      
    int cnt = 0;
    cnt = this.recProc.good_cnt(map);

    if (cnt == 0) {
      this.spiceProc.good_up(spiceno);
      this.recProc.good(recVO);
    } else {
      this.spiceProc.good_down(spiceno);
      this.recProc.good_cancel(map);
    }

    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 좋아요 누름, 0: 좋아요 안누름
    
    return json.toString();
  }

  @PostMapping(value="/good_cnt")
  @ResponseBody
  public String good_cnt(@RequestBody RecVO recVO) {

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("spiceno", recVO.getSpiceno());
    map.put("accountno", recVO.getAccountno());
      
    int cnt = 0;
    cnt = this.recProc.good_cnt(map);

    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 좋아요 누름, 0: 좋아요 안누름
    
    return json.toString();
  }

  @GetMapping(value="/list_search") 
  public String list_search_paging(Model model, HttpSession session, SpiceVO spiceVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;
    if (managerno != 0) {
      word = Tool.checkNull(word).trim();

      Map<String, Object> map = new HashMap<String, Object>();
      map.put("word", word);

      // 페이징 목록
      ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);

      // 페이징 버튼 목록
      int search_count = this.spiceProc.list_search_count(word);
      String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

      // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
      int no = (search_count - (now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);

      return "th/spice/list_search"; // /templates/spice/list_search.html
    } else {
      return "redirect:/manager/login";
    }
  }

  @GetMapping(value="/list_cno_search") 
  public String list_cno_search_paging(Model model, SpiceVO spiceVO, @RequestParam(name="word", defaultValue = "") String word, @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    word = Tool.checkNull(word).trim();

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("word", word);

     // 페이징 목록
    ArrayList<SpiceVO> list = this.spiceProc.list_search_paging(word, now_page, this.record_per_page);
    model.addAttribute("list", list);

    // 페이징 버튼 목록
    int search_count = this.spiceProc.list_search_count(word);
    String paging = this.spiceProc.pagingBox(now_page, word, "/spice/list_search", search_count, this.record_per_page, this.page_per_blocK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);

    // 일련 번호 생성: 레코드 갯수 - ((현재 페이지수) - 1) * 페이지당 레코드 수)
    int no = (search_count - (now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);

    return "th/spice/list"; // /templates/spice/list_search.html
  }
  
}