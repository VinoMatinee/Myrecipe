package dev.mvc.vocabulary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.board.BoardVO;

public interface VocabularyDAOInter {
  
  /**
   * 단어 등록
   * @param vocabularyVO
   * @return
   */
  public int create_voca(VocabularyVO vocabularyVO);
  
  /**
   * 단어 전체 목록
   * @return
   */
  public ArrayList <VocabularyVO> list();
  
  /**
   * 해당 단어 뜻 출력
   * @param voca
   * @return
   */
  public String list_mean(String voca);

  
  public ArrayList<VocabularyVO> list_search(HashMap<String, Object> hashMap);

  
  
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param hashMap
   * @return
   */
  public int list_search_count(String word);

  /**
   * 카테고리 검색 + 페이징
   * @param map
   * @return
   */
  public ArrayList<VocabularyVO>  list_search_paging(Map<String, Object> map);

  
  public int update(VocabularyVO vocabularyVO);
  
  /** 
   * 단어 삭제
   */
  public int delete(int vocano);
}
