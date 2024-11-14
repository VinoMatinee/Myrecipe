package dev.mvc.brereply;

import java.util.HashMap;
import java.util.List;


// import java.util.ArrayList;
// import java.util.HashMap;

public interface BrereplyDAOInter {
  /**
   * 대댓글 등록
   * insert id="brereply_create" parameterType="dev.mvc.brereply.BrereplyVO"
   * @param brereplyVO
   * @return
   */
  public int brereply_create(BrereplyVO brereplyVO);


  // /**
  //  * 댓글 목록
  //  * @return
  //  */
  // public ArrayList<BrereplyVO> brereply_list();

  /**
   * 댓글 목록
   * @return
   */
  public List<BrereplyMemberVO> brereply_list(int breplyno);

  /**
   * 댓글 목록
   * @return
   */
  public List<BrereplyMemberVO> brereply_list_300(int breplyno);

  /**
   * 대댓글 조회
   * @param brereplyVO
   * @return
   */
  public BrereplyVO brereply_read(int brereplyVO);

  /**
   * 대댓글 수정
   * @param brereplyVO
   * @return
   */
  public int brereply_update(BrereplyVO brereplyVO);

  /**
   * 대댓글 삭제
   * @param brereplyVO
   * @return
   */
  public int brereply_delete(int brereplyno);

  /**
   * 비밀번호 확인
   */
  public int brereply_password_check(HashMap<String, Object> hashMap);
}
