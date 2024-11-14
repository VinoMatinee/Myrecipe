package dev.mvc.team3;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.board.BoardProcInter;
import dev.mvc.board.BoardVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVO;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.gpa.GpaProcInter;
import dev.mvc.gpa.GpaVO;
import dev.mvc.region.RegionVO;
import dev.mvc.region.RegionProcInter;
import dev.mvc.regionfood.RegionfoodVO;

import dev.mvc.regionfood.RegionfoodProcInter;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.video.VideoProcInter;
import dev.mvc.video.VideoVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeCont {
  @Autowired
  @Qualifier("dev.mvc.crudcate.CrudcateProc")
  private CrudcateProcInter crudcateProc;
  
//  @Autowired
//  private Security security;
  
  public HomeCont() {
    System.out.println("-> HomeCont created.");
  }
  
  @Autowired
  @Qualifier("dev.mvc.board.BoardProc")
  private BoardProcInter boardProc;
  
  @Autowired
  @Qualifier("dev.mvc.video.VideoProc")
  private VideoProcInter videoProc;
  
  @Autowired
  @Qualifier("dev.mvc.gpa.GpaProc")
  private GpaProcInter gpaProc;
  
  @Autowired
  @Qualifier("dev.mvc.region.RegionProc")
  private RegionProcInter regionProc;
  
  @Autowired
  @Qualifier("dev.mvc.regionfood.RegionfoodProc")
  private RegionfoodProcInter regionfoodProc;
  
  @GetMapping(value="/") 
  public String home(Model model, HttpSession session, @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) { // 파일명 return
//    if (this.security != null) {
//      System.out.println("-> 객체 고유 코드: " + security.hashCode());
//      System.out.println(security.aesEncode("1234"));
//    }
    
    int crudcateno = 4;
    
    System.out.println("-> http://localhost:9093");
    
    ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
    model.addAttribute("menu", menu);
    
    CrudcateVO crudcateVO = this.crudcateProc.read(crudcateno);
    model.addAttribute("crudcateVO", crudcateVO);
    
    HashMap<String, Object> map = new HashMap<>();
    map.put("crudcateno", crudcateno);
    map.put("word", word);
    map.put("now_page", now_page);
    
    ArrayList<VideoVO> videos = this.videoProc.list();
    videos.forEach(video -> {
      String iframeHtml = video.getUrl(); // 데이터베이스에서 가져온 iframe HTML
      String src = videoProc.extractSrc(iframeHtml); // src 속성 추출
      video.setUrl(src); // url을 src 값으로 대체
  });
    model.addAttribute("videos",videos);
    
   
    
    ArrayList<BoardVO> list = this.boardProc.list_cno_search_paging(map);
    model.addAttribute("list", list);
    
    HashMap<Integer, ArrayList<GpaVO>> avggpaMap = new HashMap<>();
    list.forEach(boardlist -> {
      ArrayList<GpaVO> avggpa = this.gpaProc.avgscore(boardlist.getBoardno()); 
      avggpaMap.put(boardlist.getBoardno(), avggpa);
    });
    model.addAttribute("avggpaMap", avggpaMap);
    
    ArrayList<RegionVO> regions = this.regionProc.list();
    model.addAttribute("regions", regions);

    HashMap<Integer, ArrayList<RegionfoodVO>> regionFoodMap = new HashMap<>();
    regions.forEach(region -> {
        ArrayList<RegionfoodVO> regionFoods = this.regionfoodProc.alist(region.getRegiono());
        regionFoodMap.put(region.getRegiono(), regionFoods);
    });
    model.addAttribute("regionFoodMap", regionFoodMap);
//    model.addAttribute("search_count", search_count);
//    
    return "th/index"; // /templates/index.html  
  }
  
}






