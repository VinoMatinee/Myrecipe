package dev.mvc.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.search.SearchProc")
public class SearchProc implements SearchProcInter {

  @Autowired
  private SearchDAOInter searchDAO;

  public SearchProc() {
    System.out.println("-> SearchProc created");
  }

  @Override
  public int search_word(String word) {
    
    
    SearchVO searchVO = new SearchVO();
    searchVO.setWord(word);

    return searchDAO.search_word(searchVO);
  }

}
