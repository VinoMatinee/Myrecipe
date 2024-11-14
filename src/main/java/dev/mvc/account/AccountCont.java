package dev.mvc.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.loginlog.LoginlogProcInter;
import dev.mvc.loginlog.LoginlogVO;
// import dev.mvc.contents.Contents;
import dev.mvc.manager.ManagerProcInter;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/account")
@Controller
public class AccountCont {
  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;

  @Autowired
  @Qualifier("dev.mvc.account.AccountProc")
  private AccountProcInter accountProc;
  
  @Autowired
  @Qualifier("dev.mvc.manager.ManagerProc")
  private ManagerProcInter managerProc;
  
  @Autowired
  @Qualifier("dev.mvc.loginlog.LoginlogProc")
  private LoginlogProcInter LoginlogProc;
  
  @Autowired
  Security security;
  
  /** 페이지당 출력할 레코드 갯수 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_blocK = 10;

  public AccountCont() {
    System.out.println("-> AccountCont created");
  }

  @GetMapping(value = "/checkID")
  @ResponseBody
  public String checkID(String aid) {
    System.out.println("-> id: " + aid);
    int cnt = this.accountProc.checkID_account(aid);

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);

    return obj.toString();

  }

  /**
   * react용 세션 불러오기
   * @param session
   * @return
   */
  
@GetMapping("/getsessiondata")
public ResponseEntity<Map<String, Object>> getSessionData(HttpSession session) {
    Map<String, Object> sessionData = new HashMap<>();
    Integer accountNo = (Integer) session.getAttribute("accountno");
    String aid = (String) session.getAttribute("aid");
    // 필요한 세션 데이터를 sessionData에 추가
    sessionData.put("accountno", accountNo);
    sessionData.put("aid", aid);

    System.out.println("sessiondata: " + sessionData);
    
    return ResponseEntity.ok(sessionData);
}
  
  
  /**
   * 회윈 가입 폼
   * @param model
   * @param accountVO
   * @return
   */
  @GetMapping(value = "/signin")
  public String signin_form(Model model, AccountVO accountVO) {

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    return "th/account/create";
  }

  @PostMapping(value = "/signin_account")
  public String signin_proc(Model model, AccountVO accountVO, RedirectAttributes ra) {
    int checkID_cnt = this.accountProc.checkID_account(accountVO.getAid());

    if (checkID_cnt == 0) {
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String aprofile_img = ""; // 원본 파일명 image
      String aprofile_imgsave = ""; // 저장된 파일명, image
      String aprofile_thum = ""; // preview image

      String upDir = Profiles.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF'
      // value='' placeholder="파일 선택">
      MultipartFile mf = accountVO.getAprofile_imgMF();

      aprofile_img = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 aprofile_img: " + aprofile_img);

      long aprofile_size = mf.getSize(); // 파일 크기
      if (aprofile_size > 0) { // 파일 크기 체크, 파일을 올리는 경우
        if (Tool.checkUploadFile(aprofile_img) == true) { // 업로드 가능한 파일인지 검사
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
          aprofile_imgsave = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(aprofile_imgsave)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            aprofile_thum = Tool.preview(upDir, aprofile_imgsave, 200, 150);
          }

          accountVO.setAprofile_img(aprofile_img); // 순수 원본 파일명
          accountVO.setAprofile_imgsave(aprofile_imgsave); // 저장된 파일명(파일명 중복 처리)
          accountVO.setAprofile_thum(aprofile_thum); // 원본이미지 축소판
          accountVO.setAprofile_size(aprofile_size); // 파일 크기

        } else { // 전송 못하는 파일 형식
          ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
          ra.addFlashAttribute("cnt", 0); // 업로드 실패
          ra.addFlashAttribute("url", "/contents/msg"); // msg.html, redirect parameter 적용
          return "redirect:/contents/msg"; // Post -> Get - param...
        }
      } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }

      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------


      accountVO.setAgrade(15); // 기본 회원 15
      int cnt = this.accountProc.signin_account(accountVO);

      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("aname", accountVO.getAname());
        model.addAttribute("aid", accountVO.getAid());

      } else {
        model.addAttribute("code", "create_fail");
      }

      model.addAttribute("cnt", cnt);
    } else { // id 중복
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }

    return "th/account/login"; // /templates/member/msg.html
  }
  
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model,
                       @RequestParam(name="word", defaultValue = "") String word, 
                       @RequestParam(name="now_page", defaultValue = "1") int now_page) {

                        
    if (this.managerProc.isAdmin(session)) {

      ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
      model.addAttribute("menu", menu);

      // 페이징 목록
      ArrayList<AccountVO> list = this.accountProc.list_account_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);
    
      // 페이징 버튼 목록
      int search_count = this.accountProc.list_account_search_count(word);
      String paging = this.accountProc.pagingBox(now_page, now_page, word, "/account/list", search_count, this.record_per_page, this.page_per_blocK);
      model.addAttribute("paging", paging);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      model.addAttribute("search_count", search_count);

    return "th/account/list"; // templates/member/list.html
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
  public String read(HttpSession session, Model model, int accountno) {

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

    // 회원은 회원 등급만 처리, 관리자: 1 ~ 10, 사용자: 11 ~ 20
    // int gradeno = this.memberProc.read(memberno).getGrade(); // 등급 번호
    String mgrade = (String) session.getAttribute("mgrade"); // 등급: admin, member, guest
    String agrade = (String) session.getAttribute("agrade"); // 등급: admin, member, guest
    
    Integer sessionAccountno = (Integer) session.getAttribute("accountno");
//    System.out.println("sessionAccountno: " + sessionAccountno);
//    System.out.println("accountno" + accountno);
    
    

    // 사용자: member && 11 ~ 20
    // if (grade.equals("member") && (gradeno >= 11 && gradeno <= 20) && memberno ==
    // (int)session.getAttribute("memberno")) {
    if (this.managerProc.isMember(session) && accountno == sessionAccountno) {
      // System.out.println("-> read memberno: " + memberno);

      AccountVO accountVO = this.accountProc.read(accountno);
      model.addAttribute("accountVO", accountVO);
      System.out.println("agrade: " + agrade);

      return "th/account/read"; // templates/member/read.html

    } else if (this.managerProc.isAdmin(session)) {
      AccountVO accountVO = this.accountProc.read(accountno);
      model.addAttribute("accountVO", accountVO);
      System.out.println("agrade: " + agrade);

      return "th/account/read"; // templates/member/read.html
    }
    else  {
      return "redirect:/account/login_need"; // redirect
    }

  }

  /**
   * 로그인
   * 
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value = "/login")
  public String login_form(Model model, String url, AccountVO accountVO) {
    model.addAttribute("url", url); // 로그인 후 이동할 주소

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

    return "th/account/login"; // templates/member/login.html
  }

  /**
   * 로그인 처리
   * 
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @PostMapping(value = "/login_account")
  public String login_proc(HttpSession session, HttpServletRequest request, Model model, String aid, String apasswd, LoginlogVO loginlogVO, String url) {
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("aid", aid);
    map.put("apasswd", this.security.aesEncode(apasswd));

    int cnt = this.accountProc.login_account(map);
    System.out.println("-> login_proc cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    
    String ip = request.getRemoteAddr(); // IP
    System.out.println("-> 접속 IP: " + ip);
    
    

    if (cnt == 1) {
      // id를 이용하여 회원 정보 조회
      AccountVO accountVO = this.accountProc.readById(aid);
      session.setAttribute("accountno", accountVO.getAccountno());
      session.setAttribute("aid", accountVO.getAid());
      session.setAttribute("aname", accountVO.getAname());
      session.setAttribute("agrade", accountVO.getAgrade());
      
      loginlogVO.setIp(ip);
      loginlogVO.setAccountno(accountVO.getAccountno());
      
      int log = this.LoginlogProc.account_log(loginlogVO);
      model.addAttribute("log", log);
      model.addAttribute("ip", ip);
      
      
      if (accountVO.getAgrade() >= 1 && accountVO.getAgrade() <= 10) {
        session.setAttribute("agrade", "admin");
      } else if (accountVO.getAgrade() >= 11 && accountVO.getAgrade() <= 20) {
        session.setAttribute("agrade", "account");
      } else if (accountVO.getAgrade() >= 21) {
        session.setAttribute("agrade", "guest");
      }

      if (url.length() > 0) {
        System.out.println("url: " + url);
        return "redirect:" + url;
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
  
  /**
   * 수정 처리
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/update_account")
  public String update_proc(Model model, AccountVO accountVO, RedirectAttributes ra) {
      AccountVO accountVO_old = accountProc.read(accountVO.getAccountno());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = accountVO_old.getAprofile_imgsave();  // 실제 저장된 파일명
      String thumb1 = accountVO_old.getAprofile_thum();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Profiles.getUploadDir(); // C:/kd/deploy/resort_v4sbm3c/contents/storage/
      
      // 파일을 변경할 때만 기존 이미지를 삭제하도록 수정
      MultipartFile mf = accountVO.getAprofile_imgMF();
      if (mf != null && !mf.isEmpty()) {
          Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
          Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      }
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      if (mf != null && !mf.isEmpty()) {
          file1 = mf.getOriginalFilename(); // 원본 파일명
          size1 = mf.getSize();  // 파일 크기
          
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(file1saved)) { // 이미지인지 검사
              // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
              thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
          }
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
          file1 = accountVO_old.getAprofile_img();
          file1saved = accountVO_old.getAprofile_imgsave();
          thumb1 = accountVO_old.getAprofile_thum();
          size1 = accountVO_old.getAprofile_size();
      }
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
      
      accountVO.setAprofile_img(file1);
      accountVO.setAprofile_imgsave(file1saved);
      accountVO.setAprofile_thum(thumb1);
      accountVO.setAprofile_size(size1);
    
    int cnt = this.accountProc.update_account(accountVO); // 수정
    
    if (cnt == 1) {
      
      model.addAttribute("code", "update_success");
      model.addAttribute("aname", accountVO.getAname());
      model.addAttribute("aid", accountVO.getAid());

      ra.addAttribute("accountno", accountVO.getAccountno());
      
      return "redirect:/account/read"; // request -> param으로 접근 전환
    } else {
      model.addAttribute("code", "update_fail");
    }
    
    model.addAttribute("cnt", cnt);
    
    return "th/account/msg"; // /templates/member/msg.html
  }
  
  /**
   * 삭제
   * @param model
   * @param accountno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/delete")
  public String delete(Model model, int accountno, HttpSession session) {

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);


    System.out.println("-> delete accountno: " + accountno);
    
 // 회원은 회원 등급만 처리, 관리자: 1 ~ 10, 사용자: 11 ~ 20
    // int gradeno = this.memberProc.read(memberno).getGrade(); // 등급 번호
    String mgrade = (String) session.getAttribute("mgrade"); // 등급: admin, member, guest
    String agrade = (String) session.getAttribute("agrade"); // 등급: admin, member, guest
    
    Integer sessionAccountno = (Integer) session.getAttribute("accountno");
    
    if (this.managerProc.isMember(session) && accountno == sessionAccountno) {
    AccountVO accountVO = this.accountProc.read(accountno);
    model.addAttribute("accountVO", accountVO);
    return "th/account/delete";  // templates/account/delete.html
    }else if (this.managerProc.isAdmin(session)) {
      AccountVO accountVO = this.accountProc.read(accountno);
      model.addAttribute("accountVO", accountVO);
      return "th/account/delete";  // templates/account/delete.html
    }
    return "redirect:/account/login_need";
  }
  
  /**
   * 회원 Delete process
   * @param model
   * @param accountno 삭제할 레코드 번호
   * @return
   */
  @PostMapping(value="/delete_account")
  public String delete_process(Model model, Integer accountno) {
    int cnt = this.accountProc.delete_account(accountno);
    
    if (cnt == 1) {
      return "redirect:/account/list";
    } else {
      model.addAttribute("code", "delete_fail");
      return "th/account/msg"; // /templates/member/msg.html
    }
  }
  
  
  
  @GetMapping(value = "/find_aid_form")
  public String find_aid_form(Model model) {

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

    return "th/account/find_aid_form";

  }
  
  @PostMapping(value = "/find_aid")
  public String find_aid_proc(String aname, String atel, Model model, AccountVO accountVO) {
    int cnt = this.accountProc.check_user(aname, atel);
    
    if (cnt == 1) {
      String aid = this.accountProc.find_aid(aname, atel);
      model.addAttribute("code", "find_success");
        model.addAttribute("aid", aid);
        model.addAttribute("aname", aname);
        
        return "th/account/find_aid";
    }else {
      model.addAttribute("code", "find_fail");
      return "th/account/find_aid";
    }
  }
  
  @GetMapping(value = "/find_passwd_form")
  public String find_passwd_form(Model model) {

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

    return "th/account/find_passwd_form";

  }
  
  @GetMapping(value = "/login_need")
  public String login_need(Model model) {

    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);

    return "th/account/login_need";
  }
  
}
