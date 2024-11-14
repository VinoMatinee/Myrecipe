package dev.mvc.bookmark;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.board.BoardVO;

public interface BookmarkDAOInter {

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
   * 북마크 리스트 + 검색, 페이징
   * @param map
   * @return
   */
  public ArrayList<BookmarkVO> bm_list(HashMap<String, Object> map);
}

