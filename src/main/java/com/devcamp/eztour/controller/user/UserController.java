package com.devcamp.eztour.controller.user;

import com.devcamp.eztour.domain.user.UserDto;
import com.devcamp.eztour.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String testMain() {
        return "user/testMain.tiles";
    }

    @GetMapping("/join")
    public String join(Model model) {
        String msg = "정상접속";
        model.addAttribute("msg", msg);

        return "user/join.tiles";
    }

    @PostMapping("/join")
    public String join(UserDto user, RedirectAttributes rattr){
        try {

            System.out.println(user);
            int rowCnt = userService.insertUsr(user);

            if(rowCnt!=1)
                throw new Exception("user insert error");

            rattr.addFlashAttribute("msg", "REG_OK");

            return "redirect:/user";
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "REG_ERROR");

            return "redirect:/user/join";
        }
    }

    @GetMapping("/login")
    public String loginView() {
        return "user/login.tiles";
    }


    @PostMapping("/login")
    public String login(String usr_id, String pwd, boolean rememberId,
                        HttpSession session, HttpServletResponse response) throws Exception {

        System.out.println(usr_id);
        UserDto user = new UserDto();
        user = userService.selectUsr(usr_id);
        System.out.println("user = " + user);

        if(!(user!=null && user.getPwd().equals(pwd))) {
            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");

            return "redirect:/user/login?msg="+msg;
        }
        session.setAttribute("userId", user.getUsr_id());
        session.setAttribute("userName", user.getUsr_nm());

        if(rememberId) {
            Cookie cookie = new Cookie("id", usr_id);
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("id", usr_id);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

//        // 3. 이전에 눌렀던 url 이동 (마이페이지, 고객센터에 적용?)
//        toURL = toURL==null || toURL.equals("") ? "/" : toURL;
//        return "redirect:"+toURL;
        return "redirect:/user";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user";
    }

    @GetMapping("/usrMod")
    public String usrModView(UserDto userDto, HttpSession session, RedirectAttributes rattr, Model model){
        String usr_id = (String) session.getAttribute("userId");
        System.out.println(usr_id);
        try {
            userDto = userService.selectUsr(usr_id);
            System.out.println("========"+userDto);
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg","GET_ERROR");

            return "redirect:/user";
        }
        return "user/usrMod.tiles";
    }

    @PatchMapping ("/usrMod")
    public String usrMod(){

        return "redirect:/";
    }
}
