package dev.mvc.week;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.OverridesAttribute;

@Component("dev.mvc.week.WeekProc")
public class WeekProc implements WeekProcInter {
  @Autowired
  private WeekDAOInter weekDAO;

	@Override
	public int create(WeekVO weekVO) {
    int cnt = this.weekDAO.create(weekVO);
    return cnt;
	}

  @Override
  public ArrayList<WeekVO> list_wds(int accountno) {
    ArrayList<WeekVO> list = this.weekDAO.list_wds(accountno);
    return list;
  }

  @Override
  public ArrayList<WeekVO> list_all(HashMap<String, Object> map) {
    ArrayList<WeekVO> list = this.weekDAO.list_all(map);
    return list;
  }

  @Override
  public int delete(HashMap<String, Object> map) {
    int cnt = this.weekDAO.delete(map);
    return cnt;
  }

  @Override
	public int update(WeekVO weekVO) {
    int cnt = this.weekDAO.update(weekVO);
    return cnt;
	}
}