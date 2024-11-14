package dev.mvc.brereply;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.account.AccountProcInter;
import dev.mvc.breply.Breply;
import dev.mvc.breply.BreplyProcInter;
import dev.mvc.breply.BreplyVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/brereply")
@Controller
public class BrereplyCont {
  @Autowired
  @Qualifier("dev.mvc.brereply.BrereplyProc")
  private BrereplyProcInter brereplyProc;

  @Autowired
  @Qualifier("dev.mvc.breply.BreplyProc")
  private BreplyProcInter breplyProc;

  @Autowired
  @Qualifier("dev.mvc.account.AccountProc")
  private AccountProcInter AccountProc;

  public BrereplyCont() {
    System.out.println("-> BreplyCont created.");
  }

  @PostMapping(value = "/brereply_create")
  public String brereply_create(HttpSession session, Model model, BrereplyVO brereplyVO, RedirectAttributes ra, @RequestParam(value = "fileMF", required = false) MultipartFile fileMF) {
    if (session.getAttribute("accountno") != null || session.getAttribute("managerno") != null) {
      int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;
      brereplyVO.setManagerno(managerno);

      int accountno = session.getAttribute("accountno") != null ? (int) session.getAttribute("accountno") : 0;
      brereplyVO.setAccountno(accountno);

      if (fileMF != null && !fileMF.isEmpty()) {
          String uploadedFileName = Upload.saveFileSpring(fileMF, Brereply.getUploadDir());
          brereplyVO.setBrereplyimg(fileMF.getOriginalFilename());
          brereplyVO.setBrereplysave(uploadedFileName);
          brereplyVO.setBrereplythumb(Tool.preview(Brereply.getUploadDir(), uploadedFileName, 200, 150));
          brereplyVO.setBrereplysize(fileMF.getSize());
      }

      int cnt = this.brereplyProc.brereply_create(brereplyVO);

      JSONObject obj = new JSONObject();
      obj.put("res", cnt);

      return obj.toString();
    } else {
      return "redirect:/account/login";
    }
  }
  

  /**
   * 대댓글 목록
   * @param model
   * @param brereplyVO
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/brereply_list")
  public String brereply_list(Model model, int breplyno) {
    
    List<BrereplyMemberVO> list = this.brereplyProc.brereply_list_300(breplyno);
    
    JSONObject obj = new JSONObject();
    obj.put("res", list);

    return obj.toString();
  }

  @ResponseBody
  @GetMapping(value = "/brereply_read", produces = "application/json")
  public String brereply_read(int brereplyno) {
    BrereplyVO brereplyVO = this.brereplyProc.brereply_read(brereplyno);
    // System.out.println(brereplyVO.toString());

    JSONObject row = new JSONObject();
    row.put("brereplyno", brereplyVO.getBrereplyno());
    row.put("breplyno", brereplyVO.getBreplyno());
    row.put("accountno", brereplyVO.getAccountno());
    row.put("managerno", brereplyVO.getManagerno());
    row.put("brereplycont", brereplyVO.getBrereplycont());
    row.put("brereplyimg", brereplyVO.getBrereplyimg());

    JSONObject obj = new JSONObject();
    obj.put("res", row);

    return obj.toString();
  }
  
  /**
   * 댓글 수정
   * @param session
   * @param breplyno
   * @param breplycont
   * @param breplypasswd
   * @param file1MF
   * @return
   */
  @PostMapping(value = "/brereply_update")
  @ResponseBody
  public String brereply_update(HttpSession session, BrereplyVO brereplyVO,
                                @RequestParam(value = "brereplyno") int brereplyno,
                                @RequestParam(value = "brereplycont") String brereplycont,
                                @RequestParam(value = "brereplypasswd") String brereplypasswd,
                                @RequestParam(value = "fileMF", required = false) MultipartFile fileMF) {

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;
    int accountno = session.getAttribute("accountno") != null ? (int) session.getAttribute("accountno") : 0;

    JSONObject obj = new JSONObject();

    // BreplyVO 객체 생성 및 설정
    brereplyVO = new BrereplyVO();
    brereplyVO.setBrereplyno(brereplyno);
    brereplyVO.setBrereplycont(brereplycont);
    brereplyVO.setBrereplypasswd(brereplypasswd);
    brereplyVO.setManagerno(managerno);
    brereplyVO.setAccountno(accountno);

    int cnt = 0;
    if (managerno == brereplyVO.getManagerno() || accountno == brereplyVO.getAccountno()) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("brereplyno", brereplyno);
        map.put("brereplypasswd", brereplypasswd);

        int check = this.brereplyProc.brereply_password_check(map);

        System.out.println("brereplyno: " + brereplyno);
        System.out.println("check: " + check);

        if (check == 1) {
          BrereplyVO brereplyVO_old = this.brereplyProc.brereply_read(brereplyno);
          String file1saved_old = brereplyVO_old.getBrereplysave();  // 이전에 저장된 파일명
          String thumb_old = brereplyVO_old.getBrereplythumb();

          if (file1saved_old != null && !file1saved_old.isEmpty()) {
            String upDir = Brereply.getUploadDir(); // 파일이 저장된 디렉토리 경로
            Tool.deleteFile(upDir, file1saved_old);  // 이전에 저장된 파일 삭제
            Tool.deleteFile(upDir, thumb_old);       // 이전에 저장된 preview 이미지 삭제
          }

          if (fileMF != null && !fileMF.isEmpty()) {
            String uploadedFileName = Upload.saveFileSpring(fileMF, Brereply.getUploadDir());
            brereplyVO.setBrereplyimg(fileMF.getOriginalFilename());
            brereplyVO.setBrereplysave(uploadedFileName);
            brereplyVO.setBrereplythumb(Tool.preview(Brereply.getUploadDir(), uploadedFileName, 200, 150));
            brereplyVO.setBrereplysize(fileMF.getSize());
            Tool.deleteFile(uploadedFileName);
          }
          cnt = this.brereplyProc.brereply_update(brereplyVO);
          obj.put("res", cnt);
        } else {
          obj.put("res", 2);
        }
    }
    return obj.toString();
  }
  
  @ResponseBody
  @PostMapping(value = "brereply_delete")
  public String brereply_delete(HttpSession session, @RequestBody BrereplyVO brereplyVO) {

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;
    int accountno = session.getAttribute("accountno") != null ? (int) session.getAttribute("accountno") : 0;

    int cnt = 0;

    if (managerno != brereplyVO.getManagerno() || accountno != brereplyVO.getAccountno()) {
      BrereplyVO brereplyVO_old = this.brereplyProc.brereply_read(brereplyVO.getBrereplyno());
      String file1saved_old = brereplyVO_old.getBrereplysave();  // 이전에 저장된 파일명
      String thumb_old = brereplyVO_old.getBrereplythumb();

      if (file1saved_old != null && !file1saved_old.isEmpty()) {
        String upDir = Brereply.getUploadDir(); // 파일이 저장된 디렉토리 경로
        Tool.deleteFile(upDir, file1saved_old);  // 이전에 저장된 파일 삭제
        Tool.deleteFile(upDir, thumb_old);       // 이전에 저장된 preview 이미지 삭제
      }

      cnt = this.brereplyProc.brereply_delete(brereplyVO.getBrereplyno());
    }

    JSONObject obj = new JSONObject();
    obj.put("res", cnt);
      
    return obj.toString();
  }
}