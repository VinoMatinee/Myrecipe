package dev.mvc.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.crudcate.CrudcateVO;
import dev.mvc.tool.Security;

@Component("dev.mvc.board.BoardProc")
public class BoardProc implements BoardProcInter {
  @Autowired
  Security security;

  @Autowired // BoardDAOInter interface를 구현한 클래스의 객체를 만들어 자동 할당
  private BoardDAOInter boardDAO;

  @Override // 추상 메소드를 구현했음.
  public int create(BoardVO boardVO) {
    // -------------------------------------------------------------------
    String passwd = boardVO.getBpasswd();
    String passwd_encoded = this.security.aesEncode(passwd);
    boardVO.setBpasswd(passwd_encoded);
    // -------------------------------------------------------------------
    
    int cnt = this.boardDAO.create(boardVO);
    return cnt;
  }


  @Override
  public ArrayList<BoardVO> list_all(String word, int now_page, int record_per_page) {

    //1 페이지 시작 rownum: now_page = 1, (1 - 1) * 10 --> 0 
    int begin_of_page = (now_page - 1) * record_per_page;
    // 시작 rownum 결정
    int start_num = begin_of_page + 1;
    //  종료 rownum
    int end_num = begin_of_page + record_per_page;   

    Map <String, Object> map = new HashMap<String, Object>();
    map.put("word", word);
    map.put("start_num", start_num);
    map.put("end_num", end_num);

    ArrayList<BoardVO> list=this.boardDAO.list_all(map);
    return list;
  }

  @Override
  public ArrayList<BoardVO> list_cno(int crudcateno) {
    ArrayList<BoardVO> list = this.boardDAO.list_cno(crudcateno);
    return list;
  }

  @Override
  public BoardVO read(int boardno) {
    BoardVO boardVO = this.boardDAO.read(boardno);
    return boardVO;
  }

  @Override
  public int youtube(HashMap<String, Object> map) {
    int cnt = this.boardDAO.youtube(map);
    return cnt;
  }

  @Override
  public ArrayList<BoardVO> list_cno_search(HashMap<String, Object> hashMap) {
    ArrayList<BoardVO> list = this.boardDAO.list_cno_search(hashMap);
    return list;
  }

  @Override
  public int list_cno_search_count(HashMap<String, Object> hashMap) {
    int cnt = this.boardDAO.list_cno_search_count(hashMap);
    return cnt;
  }

  @Override
  public ArrayList<BoardVO> list_cno_search_paging(HashMap<String, Object> map) {

    int begin_of_page = ((int)map.get("now_page") - 1) * Board.RECORD_PER_PAGE;
    // 시작 rownum 결정
    int start_num = begin_of_page + 1;
    // 종료 rownum
    int end_num = begin_of_page + Board.RECORD_PER_PAGE;

    map.put("start_num", start_num);
    map.put("end_num", end_num);

    ArrayList<BoardVO> list = this.boardDAO.list_cno_search_paging(map);

    return list;
  }


  @Override
  public String pagingBox(int crudcateno, int now_page, String word, String list_file, int search_count, int record_per_page,
      int page_per_block) {
    // 전체 페이지 수: (double)1/10 -> 0.1 -> 1 페이지, (double)12/10 -> 1.2 페이지 -> 2 페이지
    int total_page = (int) (Math.ceil((double) search_count / record_per_page));
    // 전체 그룹 수: (double)1/10 -> 0.1 -> 1 그룹, (double)12/10 -> 1.2 그룹-> 2 그룹
    int total_grp = (int) (Math.ceil((double) total_page / page_per_block));
    // 현재 그룹 번호: (double)13/10 -> 1.3 -> 2 그룹
    int now_grp = (int) (Math.ceil((double) now_page / page_per_block));

    // 1 group: 1, 2, 3 ... 9, 10
    // 2 group: 11, 12 ... 19, 20
    // 3 group: 21, 22 ... 29, 30
    int start_page = ((now_grp - 1) * page_per_block) + 1; // 특정 그룹의 시작 페이지
    int end_page = (now_grp * page_per_block); // 특정 그룹의 마지막 페이지

    StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름

    // style이 java 파일에 명시되는 경우는 로직에 따라 css가 영향을 많이 받는 경우에 사용하는 방법
    str.append(" <div class='col-12'>");
    str.append(" <div class='pagination d-flex justify-content-center mt-5'>");

    // 이전 10개 페이지로 이동
    // now_grp: 1 (1 ~ 10 page)
    // now_grp: 2 (11 ~ 20 page)
    // now_grp: 3 (21 ~ 30 page)
    // 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
    // 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
    int _now_page = (now_grp - 1) * page_per_block;
    if (now_grp >= 2) { // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
      str.append("<span><A class='rounded' href='" + list_file + "?crudcateno="+crudcateno+"&word=" + word + "&now_page=" + _now_page
          + "'>이전</A></span>");
    }

    // 중앙의 페이지 목록
    for (int i = start_page; i <= end_page; i++) {
      if (i > total_page) { // 마지막 페이지를 넘어갔다면 페이 출력 종료
        break;
      }

      if (now_page == i) { // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
        str.append("<span><A class='active rounded'>" + i + "</A></span>"); // 현재 페이지, 강조
      } else {
        // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
        str.append("<span><A class='rounded' href='" + list_file + "?crudcateno="+crudcateno +"&word=" + word + "&now_page=" + i + "'>" + i
            + "</A></span>");
      }
    }

    // 10개 다음 페이지로 이동
    // nowGrp: 1 (1 ~ 10 page), nowGrp: 2 (11 ~ 20 page), nowGrp: 3 (21 ~ 30 page)
    // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
    // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
    // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
    _now_page = (now_grp * page_per_block) + 1; // 최대 페이지수 + 1
    if (now_grp < total_grp) {
      str.append("<span><A class='rounded' href='" + list_file + "?crudcateno="+crudcateno +"&word=" + word + "&now_page=" + _now_page
          + "'>다음</A></span>");
    }
    str.append("</DIV>");
    str.append("</DIV>");
    

    return str.toString();
  }

  @Override
  public int list_all_sc(String word) {
      int cnt = this.boardDAO.list_all_sc(word);
      return cnt;
  }
  

  @Override
  public String pagingBox_all(int now_page, String word, String list_file, int search_count, int record_per_page,
  int page_per_block) {
    // 전체 페이지 수: (double)1/10 -> 0.1 -> 1 페이지, (double)12/10 -> 1.2 페이지 -> 2 페이지
    int total_page = (int)(Math.ceil((double)search_count / record_per_page));
    // 전체 그룹  수: (double)1/10 -> 0.1 -> 1 그룹, (double)12/10 -> 1.2 그룹-> 2 그룹
    int total_grp = (int)(Math.ceil((double)total_page / page_per_block)); 
    // 현재 그룹 번호: (double)13/10 -> 1.3 -> 2 그룹
    int now_grp = (int)(Math.ceil((double)now_page / page_per_block));  
    
    // 1 group: 1, 2, 3 ... 9, 10
    // 2 group: 11, 12 ... 19, 20
    // 3 group: 21, 22 ... 29, 30
    int start_page = ((now_grp - 1) * page_per_block) + 1; // 특정 그룹의 시작 페이지  
    int end_page = (now_grp * page_per_block);               // 특정 그룹의 마지막 페이지   
    
    StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름 
    
    // style이 java 파일에 명시되는 경우는 로직에 따라 css가 영향을 많이 받는 경우에 사용하는 방법
    str.append(" <div class='col-12'>");
    str.append(" <div class='pagination d-flex justify-content-center mt-5'>");

    // 이전 10개 페이지로 이동
    // now_grp: 1 (1 ~ 10 page)
    // now_grp: 2 (11 ~ 20 page)
    // now_grp: 3 (21 ~ 30 page) 
    // 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
    // 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
    int _now_page = (now_grp - 1) * page_per_block;  
    if (now_grp >= 2){ // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성 
      str.append("<span><A class='rounded' href='"+list_file+"?word="+word+"&now_page="+_now_page+"'>이전</A></span>"); 
    } 
 
    // 중앙의 페이지 목록
    for(int i=start_page; i<=end_page; i++){ 
      if (i > total_page){ // 마지막 페이지를 넘어갔다면 페이 출력 종료
        break; 
      } 
  
      if (now_page == i){ // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
        str.append("<span><A class='active rounded'>" + i + "</A></span>"); // 현재 페이지, 강조 
      }else{
        // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
        str.append("<span><A class='rounded' href='"+list_file+"?word="+word+"&now_page="+i+"'>"+i+"</A></span>");   
      } 
    } 
 
    // 10개 다음 페이지로 이동
    // nowGrp: 1 (1 ~ 10 page),  nowGrp: 2 (11 ~ 20 page),  nowGrp: 3 (21 ~ 30 page) 
    // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
    // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
    // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
    _now_page = (now_grp * page_per_block)+1; //  최대 페이지수 + 1 
    if (now_grp < total_grp){ 
      str.append("<span class='span_box_1'><A href='"+list_file+"?word="+word+"&now_page="+_now_page+"'>다음</A></span>"); 
    } 
    str.append("</DIV>"); 
    return str.toString(); 
  }
  
  @Override
  public int password_check(HashMap<String, Object> map) {
    String passwd = (String)map.get("bpasswd");
    passwd = this.security.aesEncode(passwd);
    map.put("bpasswd", passwd);
    
    int cnt = this.boardDAO.password_check(map);
    return cnt;
  }

  @Override
  public int update_board(BoardVO boardVO) {
    int cnt = this.boardDAO.update_board(boardVO);
    return cnt;
  }

  @Override
  public int update_file(BoardVO boardVO) {
    int cnt = this.boardDAO.update_file(boardVO);
    return cnt;
  }

  @Override
  public int delete(int boardno) {
    int cnt = this.boardDAO.delete(boardno);
    return cnt;
  }

  @Override
  public int delete_gpa(int boardno) {
    int cnt = this.boardDAO.delete_gpa(boardno);
    return cnt;
  }


  @Override
  public int delete_bookmark(int boardno) {
    int cnt = this.boardDAO.delete_bookmark(boardno);
    return cnt;
  }

  
  @Override
  public int delete_breply(int boardno) {
    int cnt = this.boardDAO.delete_breply(boardno);
    return cnt;
  }

  @Override
  public int delete_brereply(int boardno) {
    int cnt = this.boardDAO.delete_brereply(boardno);
    return cnt;
  }

  @Override
  public int count_cno(int crudcateno) {
    int cnt = this.boardDAO.count_cno(crudcateno);
    return cnt;
  }

  @Override
  public int delete_cno(int crudcateno) {
    int cnt = this.boardDAO.delete_cno(crudcateno);
    return cnt;
  }

  @Override
  public int count_userno(int accountno, int managerno) {
    int cnt = this.boardDAO.count_userno(accountno, managerno);
    return cnt;
  }

  @Override
  public int delete_userno(int accountno, int managerno) {
    int cnt = this.boardDAO.delete_userno(accountno, managerno);
    return cnt;
  }
  

}
