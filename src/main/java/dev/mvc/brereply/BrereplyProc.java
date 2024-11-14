package dev.mvc.brereply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.breply.BreplyMemberVO;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;

@Service("dev.mvc.brereply.BrereplyProc")
public class BrereplyProc implements BrereplyProcInter{
  @Autowired
  Security security;

  @Autowired
  private BrereplyDAOInter brereplyDAO;

  public BrereplyProc() {
    System.out.println("-> BrereplyProc created.");
  }

  @Override
  public int brereply_create(BrereplyVO brereplyVO) {
    String passwd = brereplyVO.getBrereplypasswd();
    String passwd_encoded = this.security.aesEncode(passwd);
    brereplyVO.setBrereplypasswd(passwd_encoded);

    int cnt = this.brereplyDAO.brereply_create(brereplyVO);
    return cnt;
  }

  // @Override
  // public ArrayList<BrereplyVO> brereply_list() {
  //   ArrayList<BrereplyVO> list = this.brereplyDAO.brereply_list();
  //   return list;
  // }

  @Override
  public List<BrereplyMemberVO> brereply_list(int breplyno) {
    List<BrereplyMemberVO> list = this.brereplyDAO.brereply_list(breplyno);
    String content = "";
    
    // 특수 문자 변경
    for (BrereplyMemberVO brereplyMemberVO:list) {
      content = brereplyMemberVO.getBrereplycont();
      content = Tool.convertChar(content);
      brereplyMemberVO.setBrereplycont(content);
    }
    
    return list;
  }

  public List<BrereplyMemberVO> brereply_list_300(int breplyno) {
    List<BrereplyMemberVO> list = this.brereplyDAO.brereply_list_300(breplyno);
    return list;
  }

  @Override
  public BrereplyVO brereply_read(int brereplyno) {
    BrereplyVO brereplyVO = this.brereplyDAO.brereply_read(brereplyno);
    return brereplyVO;
  }

  @Override
  public int brereply_update(BrereplyVO brereplyVO) {
    int cnt = this.brereplyDAO.brereply_update(brereplyVO);
    return cnt;
  }

  public int brereply_delete(int brereplyno) {
    int cnt = this.brereplyDAO.brereply_delete(brereplyno);
    return cnt;
  }

  @Override
  public int brereply_password_check(HashMap<String, Object> map) {
    String brereplypasswd = (String)map.get("brereplypasswd");
    System.out.println("-> passwd type: " + (String)map.get("brereplypasswd"));
    brereplypasswd = this.security.aesEncode(brereplypasswd);
    System.out.println(brereplypasswd);
    map.put("brereplypasswd", brereplypasswd);

    int cnt = this.brereplyDAO.brereply_password_check(map);
    return cnt;
  }

}