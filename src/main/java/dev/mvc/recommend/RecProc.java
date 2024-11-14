package dev.mvc.recommend;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.tool.Security;

@Service("dev.mvc.recommend.RecProc")
public class RecProc implements RecProcInter{
  @Autowired
  Security security;

  @Autowired
  private RecDAOInter RecDAO;

  public RecProc() {
    System.out.println("-> RecProc created.");
  }

  @Override
  public int good(RecVO recVO) {
    int cnt = this.RecDAO.good(recVO);
    return cnt;
  }

  @Override
  public int good_cnt(HashMap<String, Object> map) {
    int cnt = this.RecDAO.good_cnt(map);
    return cnt;
  }

  @Override
  public int good_cancel(HashMap<String, Object> map) {
    int cnt = this.RecDAO.good_cancel(map);
    return cnt;
  }

}