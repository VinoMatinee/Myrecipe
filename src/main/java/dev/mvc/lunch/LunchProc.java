package dev.mvc.lunch;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.lunch.LunchProc")
public class LunchProc implements LunchProcInter {
  
  @Autowired
  private LunchDAOInter lunchDAO;

  public LunchProc() {
    System.out.println("-> LunchProc created.");
  }

  @Override
  public ArrayList<LunchVO> list(int accountno) {
    ArrayList <LunchVO> list = this.lunchDAO.list(accountno);
    return list;
  }

  @Override
  public ArrayList<LunchVO> list_all() {
    ArrayList <LunchVO> list = this.lunchDAO.list_all();
    return list;
  }

  @Override
  public int delete(int l_no) {
    int cnt = this.lunchDAO.delete(l_no);
    return cnt;
  }
}
