package dev.mvc.loginlog;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Security;

@Component("dev.mvc.loginlog.LoginlogProc")
public class LoginlogProc implements LoginlogProcInter{
  
  @Autowired
  private LoginlogDAOInter loginlogDAO;
  
  
  public LoginlogProc(){
     System.out.println("-> LoginlogProc created.");
  }


  @Override
  public int account_log(LoginlogVO loginlogVO) {
    int cnt = this.loginlogDAO.account_log(loginlogVO);
    return cnt;
  }
  
  
}
