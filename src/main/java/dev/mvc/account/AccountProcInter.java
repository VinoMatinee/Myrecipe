package dev.mvc.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public interface AccountProcInter {
  /**
   * 중복 아이디 검사
   * @param aid
   * @return 중복 아이디 갯수, 1: 중복, 0: 중복 없음
   */
  public int checkID_account(String aid);
  
  /**
   * 회원 가입
   * @param accountVO
   * @return
   */
  public int signin_account(AccountVO accountVO);
  
  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<AccountVO> list();
  
  /**
   * accountno로 회원 정보 조회
   * @param accountno
   * @return
   */
  public AccountVO read(int accountno);
  
  /**
   * id로 회원 정보 조회
   * @param aid
   * @return
   */
  public AccountVO readById(String aid);
  
  /**
   * 로그인 처리
   */
  public int login_account(HashMap<String, Object> map);
  
  /**
   * 수정 처리
   * @param accountVO
   * @return
   */
  public int update_account(AccountVO accountVO);
  
  /**
   * 회원 삭제 처리
   * @param accountno
   * @return
   */
  public int delete_account(int accountno);
  
  public ArrayList<AccountVO> list_account_search(String word);
  
  public ArrayList<AccountVO> list_account_search_paging(String word, int now_page, int record_per_page);
  
  public int list_account_search_count(String word);
  
  public String pagingBox(int movieno, int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);

  /**
   * 아이디 찾기(유저 확인)
   * @param aid
   * @param atel
   * @return 일치하는 id 정보 있는지 확인
   */
  public int check_user(String aname, String atel);
  
  /**
   * 아이디 출력
   * @param aid
   * @return 일치하는 id 정보 있는지 확인
   */
  public String find_aid(String aname, String atel);
  
  /**
   * 회원 번호 찾기
   * @param map
   * @return
   */
  public int accountno_return(HashMap<String, Object> map);
  
  /**
   * 비밀번호 찾기(유저 확인)
   * @param aid
   * @param aname
   * @param atel
   * @return
   */
  public int check_user_passwd(String aid, String aname);
  
  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  public int passwd_check(HashMap<String, Object> map);
  
  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  public int passwd_update(HashMap<String, Object> map);
  
}




