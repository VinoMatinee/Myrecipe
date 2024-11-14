package dev.mvc.breply;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.board.BoardProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/breply")
@Controller
public class BreplyCont {
  @Autowired
  @Qualifier("dev.mvc.breply.BreplyProc")
  private BreplyProcInter breplyProc;

  @Autowired
  @Qualifier("dev.mvc.board.BoardProc")
  private BoardProcInter boardProc;

  public BreplyCont() {
    System.out.println("-> BreplyCont created.");
  }

  @GetMapping(value="/msg")
  public String msg(Model model, String url){

    return url;
  }

  @PostMapping(value = "/create")
  public String create(HttpSession session, Model model, BreplyVO breplyVO, RedirectAttributes ra, @RequestParam(value = "file1MF", required = false) MultipartFile file1MF) {
    // System.out.println("-> 수신 데이터:" + breplyVO.toString());

    if (session.getAttribute("accountno") != null || session.getAttribute("managerno") != null) {
      if (session.getAttribute("accountno") != null) {
          Integer accountno = (Integer) session.getAttribute("accountno");
          breplyVO.setAccountno(accountno);
          // System.out.println("accountno: " + accountno);
      } else if (session.getAttribute("managerno") != null) {
          Integer managerno = (Integer) session.getAttribute("managerno");
          breplyVO.setManagerno(managerno);
          // System.out.println("managerno: " + managerno);
      }
      if (file1MF != null && !file1MF.isEmpty()) {
          String uploadedFileName = Upload.saveFileSpring(file1MF, Breply.getUploadDir());
          breplyVO.setBreplyimg(file1MF.getOriginalFilename());
          breplyVO.setBreplysaved(uploadedFileName);
          breplyVO.setBreplythumb(Tool.preview(Breply.getUploadDir(), uploadedFileName, 200, 150));
          breplyVO.setBreplysize(file1MF.getSize());
      }

      int cnt = this.breplyProc.replycreate(breplyVO);

      JSONObject json = new JSONObject();
      json.put("res", cnt);

      return json.toString();
    } else {
      return "redirect:/account/login";
    }
  }

  /**
   * 댓글 목록
   * @param model
   * @param breplyVO
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list")
  public String reply_list(Model model, int boardno) {

    List<BreplyMemberVO> list = this.breplyProc.reply_list_300(boardno);

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    // System.out.println("->obj.toString(): " + obj.toString());

    return obj.toString();
  }

  @GetMapping(value = "/read_reply")
  public String read(Model model, int breplyno) {
    
    BreplyVO breplyVO = this.breplyProc.read(breplyno);
    model.addAttribute("breplyVO", breplyVO);

    return "th/breply/read";
  }


  /**
   * 댓글 조회
   */
  @ResponseBody
  @GetMapping(value = "/read", produces = "application/json")
  public String read(int breplyno) {
    BreplyVO breplyVO = this.breplyProc.read(breplyno);
    // System.out.println(breplyVO);

    JSONObject row = new JSONObject();
    row.put("breplyno", breplyVO.getBreplyno());
    row.put("boardno", breplyVO.getBoardno());
    row.put("accountno", breplyVO.getAccountno());
    row.put("managerno", breplyVO.getManagerno());
    row.put("breplycont", breplyVO.getBreplycont());
    row.put("breplyimg", breplyVO.getBreplyimg());

    JSONObject obj = new JSONObject();
    obj.put("res", row);

    // System.out.println("obj: " + obj);
    
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
  @PostMapping(value = "/update")
  @ResponseBody
  public String update(HttpSession session, BreplyVO breplyVO,
                      @RequestParam(value = "breplyno") int breplyno,
                      @RequestParam(value = "breplycont") String breplycont,
                      @RequestParam(value = "breplypasswd") String breplypasswd,
                      @RequestParam(value = "file1MF", required = false) MultipartFile file1MF) {

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;
    int accountno = session.getAttribute("accountno") != null ? (int) session.getAttribute("accountno") : 0;

    System.out.println("breplyno: " + breplyno + ", breplycont: " + breplycont + ", breplypasswd: " + breplypasswd + ", file1MF: " + file1MF);

    JSONObject obj = new JSONObject();

    // BreplyVO 객체 생성 및 설정
    breplyVO = new BreplyVO();
    breplyVO.setBreplyno(breplyno);
    breplyVO.setBreplycont(breplycont);
    breplyVO.setBreplypasswd(breplypasswd);
    breplyVO.setManagerno(managerno);
    breplyVO.setAccountno(accountno);

    int cnt = 0;
    if (managerno == breplyVO.getManagerno() || accountno == breplyVO.getAccountno()) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("breplyno", breplyno);
        map.put("breplypasswd", breplypasswd);

        int check = this.breplyProc.password_check(map);

        if (check == 1) {
          BreplyVO breplyVO_old = this.breplyProc.read(breplyno);
          String file1saved_old = breplyVO_old.getBreplysaved();  // 이전에 저장된 파일명
          String thumb_old = breplyVO_old.getBreplythumb();

          if (file1saved_old != null && !file1saved_old.isEmpty()) {
            String upDir = Breply.getUploadDir(); // 파일이 저장된 디렉토리 경로
            Tool.deleteFile(upDir, file1saved_old);  // 이전에 저장된 파일 삭제
            Tool.deleteFile(upDir, thumb_old);       // 이전에 저장된 preview 이미지 삭제
          }

          if (file1MF != null && !file1MF.isEmpty()) {
            String uploadedFileName = Upload.saveFileSpring(file1MF, Breply.getUploadDir());
            breplyVO.setBreplyimg(file1MF.getOriginalFilename());
            breplyVO.setBreplysaved(uploadedFileName);
            breplyVO.setBreplythumb(Tool.preview(Breply.getUploadDir(), uploadedFileName, 200, 150));
            breplyVO.setBreplysize(file1MF.getSize());
            Tool.deleteFile(uploadedFileName);
          }
          cnt = this.breplyProc.update(breplyVO);
          obj.put("res", cnt);
        } else {
          obj.put("res", 2);
        }
    }
    return obj.toString();
  }

  @ResponseBody
  @PostMapping(value = "delete")
  public String delete(HttpSession session, @RequestBody BreplyVO breplyVO) {

    int managerno = session.getAttribute("managerno") != null ? (int) session.getAttribute("managerno") : 0;
    int accountno = session.getAttribute("accountno") != null ? (int) session.getAttribute("accountno") : 0;

    int cnt = 0;

    if (managerno != breplyVO.getManagerno() || accountno != breplyVO.getAccountno()) {
      BreplyVO breplyVO_old = this.breplyProc.read(breplyVO.getBreplyno());
      String file1saved_old = breplyVO_old.getBreplysaved();  // 이전에 저장된 파일명
      String thumb_old = breplyVO_old.getBreplythumb();

      if (file1saved_old != null && !file1saved_old.isEmpty()) {
        String upDir = Breply.getUploadDir(); // 파일이 저장된 디렉토리 경로
        Tool.deleteFile(upDir, file1saved_old);  // 이전에 저장된 파일 삭제
        Tool.deleteFile(upDir, thumb_old);       // 이전에 저장된 preview 이미지 삭제
      }

      cnt = this.breplyProc.delete(breplyVO.getBreplyno());
    }

    JSONObject obj = new JSONObject();
    obj.put("res", cnt);
      
    return obj.toString();
  }
  
}