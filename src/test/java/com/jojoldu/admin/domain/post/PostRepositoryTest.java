package com.jojoldu.admin.domain.post;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postRepository.save(Post.builder()
        .title(title)
        .content(content)
        .author("jy@naver.com")
        .build());

        //when
        List<Post> postsList = postRepository.findAll();

        //then
        Post posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

   @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2020, 2, 24, 0, 0, 0);
        postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Post> postsList = postRepository.findAll();

        //then
        Post post = postsList.get(0);

        System.out.println(">>>>>>>>> createDate=" + post.getCreatedDate() + ", modifiedDate=" + post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}