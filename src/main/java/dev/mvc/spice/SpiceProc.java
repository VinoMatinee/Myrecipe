package dev.mvc.spice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.spice.SpiceProc")
public class SpiceProc implements SpiceProcInter{

  @Autowired
  private SpiceDAOInter spiceDAO;

  public SpiceProc() {
    System.out.println("-> SpiceProc Created.");
  }

  @Override
  public int create(SpiceVO spiceVO) {
    int cnt = this.spiceDAO.create(spiceVO);
    return cnt;
  }

  @Override
  public ArrayList<SpiceVO> list() {
    ArrayList<SpiceVO> list = this.spiceDAO.list();
    return list;
  }

  @Override
  public SpiceVO read(int spiceno) {
    SpiceVO spiceVO = this.spiceDAO.read(spiceno);
    return spiceVO;
  }

  @Override
  public int update(SpiceVO spiceVO) {
    int cnt = this.spiceDAO.update(spiceVO);
    return cnt;
  }

  @Override
  public int delete(int spiceno) {
    int cnt = this.spiceDAO.delete(spiceno);
    return cnt;
  }

  @Override
  public int good_up(int spiceno) {
    int cnt = this.spiceDAO.good_up(spiceno);
    return cnt;
  }

  @Override
  public int good_down(int spiceno) {
    int cnt = this.spiceDAO.good_down(spiceno);
    return cnt;
  }

  @Override
  public ArrayList<SpiceVO> list_search_paging(String word, int now_page, int record_per_page) {

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

    ArrayList<SpiceVO> list=this.spiceDAO.list_search_paging(map);
    return list;
  }

  @Override
  public int list_search_count(String word) {
    int cnt = this.spiceDAO.list_search_count(word);
    return cnt;
  }

  @Override
  public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page,
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
  
}
