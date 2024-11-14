package dev.mvc.bookmark;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public class BookmarkVO {

  /** 북마크 번호 */
  private int bookmarkno;

  /** 회원 번호 */
  private int accountno;

  /** 게시글 번호 */
  private int boardno;

  /** 북마크 url */
  private String bookmarkurl;
  
  /** 북마크 등록일 */
  private String bmdate;

  // join
  /** 게시글 제목 */
  private String btitle;

  /** 게시글 내용 */
  private String bcontent;
  
  /** 게시글 검색어 */
  private String btag;
}
