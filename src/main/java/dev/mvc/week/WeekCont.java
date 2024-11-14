package dev.mvc.week;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.account.AccountProcInter;
import dev.mvc.board.Board;
import dev.mvc.board.BoardVO;
import dev.mvc.crudcate.CrudcateProcInter;
import dev.mvc.crudcate.CrudcateVO;
import dev.mvc.crudcate.CrudcateVOMenu;
import dev.mvc.gpa.GpaProcInter;
import dev.mvc.manager.ManagerProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping(value = "/week")
@Controller
public class WeekCont {
@Autowired
@Qualifier("dev.mvc.crudcate.CrudcateProc")
private CrudcateProcInter crudcateProc;

@Autowired
@Qualifier("dev.mvc.account.AccountProc")
private AccountProcInter accountProc;

@Autowired
@Qualifier("dev.mvc.manager.ManagerProc")
private ManagerProcInter managerProc;

@Autowired
@Qualifier("dev.mvc.gpa.GpaProc")
private GpaProcInter gpaProc;

@Autowired
@Qualifier("dev.mvc.week.WeekProc")
private WeekProcInter weekProc;

    public WeekCont() {
        System.out.println("-> WeekCont created.");
    }

    /**
     * Create 폼
     * http://localhost:9093/week/create
     * @param session
     * @param model
     * @param weekVO
     * @param accountno
     * @return
     */
    @GetMapping(value = "/create")
    public String create(HttpSession session, Model model, WeekVO weekVO, Integer accountno) {
        if (session.getAttribute("accountno") != null) {
            ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
            model.addAttribute("menu", menu);

            // 세션에서 accountno 가져오기
            accountno = (Integer) session.getAttribute("accountno");
            model.addAttribute("accountno", accountno);

            return "th/week/create";
        }      // 로그인되지 않은 경우
        return "redirect:/account/login_need";
    }

    /**
     * Create 폼 처리
     * @param request
     * @param session
     * @param model
     * @param weekVO
     * @param ra
     * @param accountno
     * @return
     */
    @PostMapping(value = "/create")
    public String create_process(HttpServletRequest request, HttpSession session, Model model, WeekVO weekVO, RedirectAttributes ra, Integer accountno) {
        if (session.getAttribute("accountno") != null) { // 회원으로 로그인 했을 경우
            int cnt = this.weekProc.create(weekVO);
            if (cnt == 1) {
                ra.addAttribute("accountno", accountno); // controller -> controller: O
                // System.out.println("saved accountno: " + accountno);
                return "redirect:/week/list_combined";
            } else {
                ra.addFlashAttribute("code", "create_fail"); // DBMS 등록 실패
                ra.addFlashAttribute("cnt", 0); // 업로드 실패
                ra.addFlashAttribute("url", "/week/msg"); // msg.html, redirect parameter 적용
                return "redirect:/week/msg"; // Post -> Get - param...
            }
        }
        return "redirect:/account/login_need"; // /member/login_cookie_need.html
    }

    /**
     * 주간 식단 선택 + 조회
     * @param session
     * @param model
     * @param weekdates
     * @return
     */
    @GetMapping(value = "/list_combined")
    public String list_combined(HttpSession session, Model model, @RequestParam(value = "weekdates", required = false) String weekdates) {

        Integer sessionAccountno = (Integer) session.getAttribute("accountno");

        // 메뉴
        ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
        model.addAttribute("menu", menu);

        if (sessionAccountno != null) { // 세션에 저장된 계정 번호가 있을 때
            // 주어진 계정 번호에 해당하는 주간 메뉴의 weekdates 목록을 가져옵니다.
            ArrayList<WeekVO> weekDatesList = this.weekProc.list_wds(sessionAccountno);
            model.addAttribute("weekDatesList", weekDatesList);

            if (weekdates != null) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("accountno", sessionAccountno);
                map.put("weekdates", weekdates);

                // 선택한 주간 식단표 데이터 가져오기
                ArrayList<WeekVO> weekList = weekProc.list_all(map);
                model.addAttribute("weekList", weekList);

                WeekVO weekVO = new WeekVO(); // 예시로 객체 생성하셨다면 실제 데이터로 객체를 생성해야 합니다.
                model.addAttribute("weekVO", weekVO); // 모델에 weekVO 객체를 전달
                model.addAttribute("weekdates", weekdates); // 선택한 weekdates 값을 모델에 추가
            }

            model.addAttribute("accountno", sessionAccountno);
            return "th/week/list_combined";
        } else {
            return "redirect:/account/login_need";
        }
    }

    /**
     * 삭제 처리
     * @param model
     * @param accountno
     * @param weekdates
     * @param redirectAttributes
     * @return
     */
    @PostMapping(value = "/delete")
    public String delete_process(Model model, @RequestParam("accountno") Integer accountno, @RequestParam("weekdates") String weekdates, RedirectAttributes redirectAttributes) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("accountno", accountno);
        map.put("weekdates", weekdates);
        
        int cnt = this.weekProc.delete(map); // 삭제
        model.addAttribute("cnt" , cnt);
        if (cnt == 1) {
            return "redirect:/week/list_combined";
        } else {
            model.addAttribute("code", "delete_fail");
            return "th/week/msg";
        }
    }

    /**
     * 수정 폼
     * @param session
     * @param model
     * @param accountno
     * @param weekdates
     * @return
     */
    @GetMapping(value = "/update")
    public String update(HttpSession session, Model model, @RequestParam(value = "accountno") Integer accountno, @RequestParam(value = "weekdates") String weekdates) {
        ArrayList<CrudcateVOMenu> menu = this.crudcateProc.menu();
        model.addAttribute("menu", menu);
    
        // 주어진 accountno와 weekdates에 해당하는 주간 식단표를 가져옵니다.
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("accountno", accountno);
        map.put("weekdates", weekdates);
        ArrayList<WeekVO> weekList = weekProc.list_all(map);
    
        // 주간 식단표가 존재하는 경우에만 모델에 추가합니다.
        if (!weekList.isEmpty()) {
            WeekVO weekVO = weekList.get(0); // 주간 식단표가 여러 개인 경우에는 적절히 처리해야 합니다.
            model.addAttribute("weekList", weekList); // 모델에 weekList 객체를 전달
        }
    
        model.addAttribute("weekdates", weekdates); // 선택한 weekdates 값을 모델에 추가
        model.addAttribute("accountno", accountno);
    
        return "th/week/update";
    }

    /**
     * 수정 처리
     * @param request
     * @param session
     * @param model
     * @param weekVO
     * @param ra
     * @param accountno
     * @param weekdates
     * @return
     */
    @PostMapping(value = "/update")
    public String update_process(HttpServletRequest request, HttpSession session, Model model, WeekVO weekVO, RedirectAttributes ra, Integer accountno, String weekdates) {
        if (session.getAttribute("accountno") != null || session.getAttribute("managerno") != null) { // 회원으로 로그인 했을 경우
            int cnt = this.weekProc.update(weekVO);
            if (cnt == 1) {
                ra.addAttribute("accountno", accountno); // controller -> controller: O
                ra.addAttribute("weekdates", weekdates);
                // System.out.println("saved accountno: " + accountno);
                return "redirect:/week/list_combined";
            } else {
                ra.addFlashAttribute("code", "create_fail"); // DBMS 등록 실패
                ra.addFlashAttribute("cnt", 0); // 업로드 실패
                ra.addFlashAttribute("url", "/week/msg"); // msg.html, redirect parameter 적용
                return "redirect:/week/msg"; // Post -> Get - param...
            }
        }
        return "redirect:/account/login_need"; // /member/login_cookie_need.html
    }

}