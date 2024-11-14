package dev.mvc.manager;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.account.AccountVO;
import jakarta.servlet.http.HttpSession;


public interface ManagerProcInter {
  
  /**
   * 중복 아이디 검사
   * @param mid
   * @return 중복 아이디 갯수
   */
  public int checkID_manager(String mid);
  
  /**
   * 회원 가입
   * @param accountVO
   * @return 추가한 레코드 갯수
   */
  public int signin_manager(ManagerVO managerVO);
  
  /**
   * 로그인 처리
   */
  public int login_manager(HashMap<String, Object> map);
  
  
  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public ManagerVO readById(String mid);
  
  /**
   * 로그인된 회원 계정인지 검사.
   * @param session
   * @return true: 사용자
   */
  public boolean isMember(HttpSession session);
  
  /**
   * 로그인된 회원+관리자 계정인지 검사.
   * @param session
   * @return true: 사용자
   */
  public boolean isMemberAdmin(HttpSession session);

  /**
   * 로그인된 회원 관리자 계정인지 검사.
   * @param session
   * @return true: 사용자
   */
  public boolean isAdmin(HttpSession session);
  
  
  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<ManagerVO> list();
  
  /**
   * managerno로 회원 정보 조회
   * @param managerno
   * @return
   */
  public ManagerVO read(int managerno);
  
  /**
   * 수정 처리
   * @param accountVO
   * @return
   */
  public int update_manager(ManagerVO managerVO);
  
  /**
   * 회원 삭제 처리
   * @param accountno
   * @return
   */
  public int delete_manager(int managerno);
  


  
}




