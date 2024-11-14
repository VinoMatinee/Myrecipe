package dev.mvc.video;

import java.util.ArrayList;
import java.util.HashMap;

public interface VideoProcInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.gpa.GpaVO"
   * @param RegionVO
   * @return 등록한 레코드 갯수
   */
  public int create(VideoVO videoVO);
  
    /**
     * id로 회원 정보 조회
     * @param id
     * @return
     */
    public VideoVO readById(String mid);
//    
//    
    public ArrayList<VideoVO> list();
    
    public String extractSrc(String iframeHtml);
    
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public VideoVO read(int videono);

    public ArrayList<VideoVO> list_search_paging(String word, int now_page, int record_per_page);
    
    
    
    public int list_search_count(String word);

    
//    public String pagingBox(int boardno, int now_page, String word, String list_file, int search_count, 
//    int record_per_page, int page_per_block);
//    
    public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
    
    
//    
//    public ArrayList<VideoVO> avgscore(int boardno);
//    
    public int update(VideoVO videoVO);
//    
    public int delete(int videono);
}
