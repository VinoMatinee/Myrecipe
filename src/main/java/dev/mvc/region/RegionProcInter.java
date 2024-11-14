package dev.mvc.region;

import java.util.ArrayList;
import java.util.HashMap;

public interface RegionProcInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.gpa.GpaVO"
   * @param RegionfoodVO
   * @return 등록한 레코드 갯수
   */
  public int create(RegionVO regionVO);
  
    /**
     * id로 회원 정보 조회
     * @param id
     * @return
     */
    public RegionVO readById(String mid);
//    
//    
    public ArrayList<RegionVO> list();
    
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public RegionVO read(int regiono);

    public ArrayList<RegionVO> list_search_paging(String word, int now_page, int record_per_page);
    
    
    public int list_search_count(String word);

    
//    public String pagingBox(int boardno, int now_page, String word, String list_file, int search_count, 
//    int record_per_page, int page_per_block);
//    
    public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
    
    
//    
//    public ArrayList<VideoVO> avgscore(int boardno);
//    
    public int update(RegionVO regionVO);
//    
    public int delete(int regiono);
}
