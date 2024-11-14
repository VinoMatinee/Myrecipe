package dev.mvc.chat;

import java.util.UUID;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.mvc.account.AccountProcInter;
import dev.mvc.manager.ManagerProcInter;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {

    @Autowired
    @Qualifier("dev.mvc.account.AccountProc")
    private AccountProcInter accountProc;

    @Autowired
    @Qualifier("dev.mvc.manager.ManagerProc")
    private ManagerProcInter managerProc;

    @GetMapping("/chat")
    public String chatGet(Model model, HttpSession session) {

        if (session.getAttribute("accountno") != null || session.getAttribute("managerno") != null) {


        log.info("ChatController Test");

        String aid = (String) session.getAttribute("aid");
        String mid = (String) session.getAttribute("mid");
        model.addAttribute("aid", aid);
        model.addAttribute("mid", mid);

        System.out.println(aid);
        System.out.println(mid);

        model.addAttribute("name", UUID.randomUUID().toString());
        return "th/chat/chat";
        }
    return "redirect:/account/login_need"; // account/login_need.html
    } 
}
