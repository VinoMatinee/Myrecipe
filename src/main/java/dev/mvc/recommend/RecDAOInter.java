package dev.mvc.recommend;

import java.util.HashMap;

public interface RecDAOInter {

  public int good(RecVO recVO);

  public int good_cnt(HashMap<String, Object> map);

  public int good_cancel(HashMap<String, Object> map);
  
}
