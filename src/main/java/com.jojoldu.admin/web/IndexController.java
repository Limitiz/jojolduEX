package com.jojoldu.admin.web;

import com.jojoldu.admin.config.auth.dto.SessionUser;
import com.jojoldu.admin.service.PostService;
import com.jojoldu.admin.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("post", postService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user!=null)
            model.addAttribute("userName", user.getName());
        return "index";
    }

    @GetMapping("/post/save")
    public String postSave(){
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model){
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "post-update";
    }
}
