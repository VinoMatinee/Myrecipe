package dev.mvc.ai;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.account.AccountVO;
import dev.mvc.ai.AiVO;

public interface AiDAOInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.ai.AiVO"
   * @param AiVO
   * @return 등록한 레코드 갯수
   */
  public int create(AiVO aiVO);
  
  public ArrayList<AiVO> list();
  
  public int update(AiVO aiVO);
  
  public int delete(int searchno);
  
  public ArrayList <AiVO> img(int accountno);
  
  public ArrayList <AiVO> img_all();
}








