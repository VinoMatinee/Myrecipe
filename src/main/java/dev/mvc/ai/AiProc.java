package dev.mvc.ai;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.account.AccountVO;

@Service("dev.mvc.ai.AiProc")
public class AiProc implements AiProcInter {
    @Autowired
    
    private AiDAOInter AiDAO;
    
    public AiProc() {
        // System.out.println("-> AiProc created.");  
    }
    
    @Override
    public int create(AiVO aiVO) {
        int cnt = this.AiDAO.create(aiVO);
        return cnt;
    }
    
    
    
    @Override
    public ArrayList<AiVO> list() {
      ArrayList<AiVO> list = this.AiDAO.list();
      
      return list;
    }
    
    @Override
    public int delete(int searchno) {
        int cnt = this.AiDAO.delete(searchno);
        return cnt;
    }

    @Override
    public int update(AiVO aiVO) {
      int cnt = this.AiDAO.update(aiVO);
     return cnt;
    }

    @Override
    public ArrayList<AiVO> img(int accountno) {
      ArrayList <AiVO> img = this.AiDAO.img(accountno);
      return img;
    }

    @Override
    public ArrayList<AiVO> img_all() {
      ArrayList <AiVO> img_all = this.AiDAO.img_all();
      return img_all;
    }
    
}
