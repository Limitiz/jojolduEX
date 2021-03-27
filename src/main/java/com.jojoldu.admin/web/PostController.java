package com.jojoldu.admin.web;

import com.jojoldu.admin.service.PostService;
import com.jojoldu.admin.web.dto.PostSaveRequestDto;
import com.jojoldu.admin.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public Long save(@RequestBody PostSaveRequestDto requestDto){
        return postService.save(requestDto);
    }

    @PutMapping("api/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto){
        return postService.update(id, requestDto);
    }

    @GetMapping("api/post/list")
    public String list(Model model){
        model.addAttribute("post",postService.findAllDesc());
        return "index";
    }

    @DeleteMapping
    public Long delete(@PathVariable Long id){
        postService.delete(id);
        return id;
    }
}