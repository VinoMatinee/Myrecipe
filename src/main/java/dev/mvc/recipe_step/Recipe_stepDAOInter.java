package dev.mvc.recipe_step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import dev.mvc.board.BoardVO;
import dev.mvc.spice.SpiceVO;





public interface Recipe_stepDAOInter {

  
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.gpa.GpaVO"
   * @param Recipe_stepVO
   * @return 등록한 레코드 갯수
   */
    public int create(Recipe_stepVO Recipe_stepVO);
    
   
   public Integer selectMaxStepOrderByFoodno(int foodno); 
    
    /**
     * id로 회원 정보 조회
     * @param id
     * @return
     */
    public Recipe_stepVO readById(String mid);
    
    public Recipe_stepVO read(int foodno);
    
    public ArrayList<Recipe_stepVO> listfoodno (int foodno);
    
    
    public ArrayList<Recipe_stepVO> list();
    
   
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public ArrayList<Recipe_stepVO> alist(int step_no);
    
    
    public int list_search_count(String word);

    
   
    /**
     * 카테고리 검색 + 페이징
     * @param map
     * @return
     */
    public ArrayList<Recipe_stepVO> list_search_paging(Map<String, Object> map);
   
    
   
    public int update(Recipe_stepVO Recipe_stepVO);
    
    public int delete(int step_no);

}
