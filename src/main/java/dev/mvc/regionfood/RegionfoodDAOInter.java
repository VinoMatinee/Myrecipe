package dev.mvc.regionfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import dev.mvc.board.BoardVO;
import dev.mvc.spice.SpiceVO;





public interface RegionfoodDAOInter {

  
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
    
    public RegionfoodVO read(int foodno);
    
    
    public ArrayList<RegionfoodVO> list();
    
   
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public ArrayList<RegionfoodVO> alist(int regiono);
    
    
    public int list_search_count(String word);

    
   
    /**
     * 카테고리 검색 + 페이징
     * @param map
     * @return
     */
    public ArrayList<RegionfoodVO> list_search_paging(Map<String, Object> map);
   
    
   
    public int update(RegionfoodVO regionfoodVO);
    
    public int delete(int foodno);

}
