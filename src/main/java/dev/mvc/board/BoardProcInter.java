package dev.mvc.board;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.crudcate.CrudcateVO;

public interface BoardProcInter {
  /**
   * 등록
   * @param boardVO
   * @return
   */
  public int create(BoardVO boardVO);

  /**
   * 전체 목록
   * @param word
   * @param now_page
   * @param record_per_page
   * @return
   */
  public ArrayList<BoardVO> list_all(String word, int now_page, int record_per_page);

  /**
   * 검색된 레코드 갯수
   * @param word
   * @return
   */
  public int list_all_sc(String word);

  /**
   * list_all 페이징 박스
   * @param now_page
   * @param word
   * @param list_file
   * @param search_count
   * @param record_per_page
   * @param page_per_block
   * @return
   */
  public String pagingBox_all(int now_page, String word, String list_file, int search_count, 
  int record_per_page, int page_per_block); 

  /**
   * 카테고리별 목록
   * @param crudcateno
   * @return
   */
  public ArrayList<BoardVO> list_cno(int crudcateno);

  /**
   * 조회
   * @param boardno
   * @return
   */
  public BoardVO read(int boardno);

  /**
   * 유튜브 삽입
   * @param map
   * @return
   */
  public int youtube(HashMap<String, Object> map);

  /**
   * 카테고리 검색
   * @param hashMap
   * @return
   */
  public ArrayList<BoardVO> list_cno_search(HashMap<String, Object> hashMap);

  /**
   * 카테고리별 검색된 레코드 갯수
   * @param hashMap
   * @return
   */
  public int list_cno_search_count(HashMap<String, Object> hashMap);

  /**
   * 카테고리 검색 + 페이징
   * @param map
   * @return
   */
  public ArrayList<BoardVO> list_cno_search_paging(HashMap<String, Object> map);

  /**
   * list_cno_search_paging 페이징 박스
   * @param crudcateno
   * @param now_page
   * @param word
   * @param list_file
   * @param search_count
   * @param record_per_page
   * @param page_per_block
   * @return
   */
  public String pagingBox(int crudcateno, int now_page, String word, String list_file, int search_count, 
  int record_per_page, int page_per_block);

  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 글 정보 수정
   * @param boardVO
   * @return 처리된 레코드 갯수
   */
  public int update_board(BoardVO boardVO);

  /**
   * 파일 정보 수정
   * @param boardVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(BoardVO boardVO);

  /**
   * 삭제
   * @param boardno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int boardno);
  public int delete_gpa(int boardno);
  public int delete_bookmark(int boardno);
  public int delete_breply(int boardno);
  public int delete_brereply(int boardno);
  
  /**
  * FK crudcateno 값이 같은 레코드 갯수 산출
  * @param crudcateno
  * @return
  */
  public int count_cno(int crudcateno);

  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param crudcateno
   * @return
   */
  public int delete_cno(int crudcateno);
  
  /**
  * FK accoutno, managerno 값이 같은 레코드 갯수 산출
  * @param accountno
  * @param managerno
  * @return
  */
  public int count_userno(int accountno, int managerno);

  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param accountno
   * @param managerno
   * @return
   */
  public int delete_userno(int accountno, int managerno);


}
