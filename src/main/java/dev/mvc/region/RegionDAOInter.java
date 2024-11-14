package dev.mvc.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.board.BoardVO;
import dev.mvc.spice.SpiceVO;





public interface RegionDAOInter {

  
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
    
    
    public ArrayList<RegionVO> list();
    
   
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public RegionVO read(int regiono);
    
    
    public int list_search_count(String word);

    
   
    /**
     * 카테고리 검색 + 페이징
     * @param map
     * @return
     */
    public ArrayList<RegionVO> list_search_paging(Map<String, Object> map);
   
    
   
    public int update(RegionVO regionVO);
    
    public int delete(int regiono);

}
