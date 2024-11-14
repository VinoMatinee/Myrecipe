package dev.mvc.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.board.BoardVO;
import dev.mvc.spice.SpiceVO;





public interface VideoDAOInter {

  
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
    
    
    public ArrayList<VideoVO> list();
    
   
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public VideoVO read(int videono);
    
    
    public int list_search_count(String word);

    
   
    /**
     * 카테고리 검색 + 페이징
     * @param map
     * @return
     */
    public ArrayList<VideoVO> list_search_paging(Map<String, Object> map);
   
    
   
    public int update(VideoVO videoVO);
    
    public int delete(int videono);

}
