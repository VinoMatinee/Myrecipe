package dev.mvc.bookmark;

import java.util.ArrayList;
import java.util.HashMap;

public interface BookmarkProcInter {

  /**
   * 북마크 추가
   * @param bookmarkVO
   * @return
   */
  public int bm_insert(BookmarkVO bookmarkVO);

  /**
   * 북마크 삭제
   * @param bookmarkVO
   * @return
   */
  public int bm_delete(BookmarkVO bookmarkVO);

  /**
   * 북마크 검색 카운터
   * @param hashMap
   * @return
   */
  public int bm_list_count(HashMap<String, Object> hashMap);

  /**
   * 북마크 리스트
   * @param map
   * @return
   */
  public ArrayList<BookmarkVO> bm_list(HashMap<String, Object> map);

  /**
   * 페이징 박스
   * @param accountno
   * @param now_page
   * @param word
   * @param list_file
   * @param search_count
   * @param record_per_page
   * @param page_per_block
   * @return
   */
  public String pagingBox(int accountno, int now_page, String word, String list_file, int search_count, 
  int record_per_page, int page_per_block);
}
