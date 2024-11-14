package dev.mvc.recipe_step;

import java.util.ArrayList;
import java.util.HashMap;

public interface Recipe_stepProcInter {
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
//    
//    \
    public Recipe_stepVO read(int step_no);
    
    public ArrayList<Recipe_stepVO> list();
    
    /**
     * 비디오 조회
     * @param spiceno
     * @return
     */
    public ArrayList<Recipe_stepVO> alist(int step_no);

    public ArrayList<Recipe_stepVO> listfoodno (int foodno);
    
    
    public ArrayList<Recipe_stepVO> list_search_paging(String word, int now_page, int record_per_page);
    
    
    public int list_search_count(String word);

    
//    public String pagingBox(int boardno, int now_page, String word, String list_file, int search_count, 
//    int record_per_page, int page_per_block);
//    
    public String pagingBox(int step_no, int now_page, String word, String list_file, int search_count, int record_per_page, int page_per_blocK);
    
    
//    
//    public ArrayList<VideoVO> avgscore(int boardno);
//    
    public int update(Recipe_stepVO Recipe_stepVO);
//    
    public int delete(int step_no);
}
