package dev.mvc.regionfood;

import java.util.ArrayList;
import java.util.HashMap;

public interface RegionfoodProcInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.gpa.GpaVO"
   * @param Recipe_stepVO
   * @return 등록한 레코드 갯수
   */
  public int create(RegionfoodVO regionfoodVO);
  
    /**
     * id로 회원 정보 조회
     * @param id
     * @return
     */
    public RegionfoodVO readById(String mid);
//    
//    \
    public RegionfoodVO read(int foodno);
    
    public ArrayList<RegionfoodVO> list();
    
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public ArrayList<RegionfoodVO> alist(int regiono);

    public ArrayList<RegionfoodVO> list_search_paging(String word, int now_page, int record_per_page);
    
    
    public int list_search_count(String word);

    
//    public String pagingBox(int boardno, int now_page, String word, String list_file, int search_count, 
//    int record_per_page, int page_per_block);
//    
    public String pagingBox(int regiono, int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
    
    
//    
//    public ArrayList<VideoVO> avgscore(int boardno);
//    
    public int update(RegionfoodVO regionfoodVO);
//    
    public int delete(int foodno);
}
