package com.jojoldu.admin.web;

import com.jojoldu.admin.domain.post.Post;
import com.jojoldu.admin.domain.post.PostRepository;
import com.jojoldu.admin.web.dto.PostSaveRequestDto;
import com.jojoldu.admin.web.dto.PostUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    public PostControllerTest() {
    }

    @After
    public void tearDown() throws Exception{
        postRepository.deleteAll();
    }

    @Test
    public void post_save() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostSaveRequestDto requestDto
                = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("jy@naver.com")
                .build();
        String url = "http://localhost:"+port+"/api/post";

        //when
        ResponseEntity<Long> responseEntity
                = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isGreaterThan(0L);

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void post_update() throws Exception{
        //given
        Post savedPost = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .author ("jy@naver.com")
                .build());

        Long updateId = savedPost.getId();
        String updateTitle = "title2";
        String updateContent = "content2";
        PostUpdateRequestDto requestDto =
                PostUpdateRequestDto.builder()
                        .title(updateTitle)
                        .content (updateContent)
                        .build();

        String url = "http://localhost:" + port + "/api/post/"+ updateId;
        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity
                = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    public void BT(){
        //given
        LocalDateTime now = LocalDateTime.of(2020,2,4,0,0,0);
        postRepository.save(Post.builder()
        .title("title")
        .content("content")
        .author("jy@naver.com")
        .build());

        //when
        List<Post> all = postRepository.findAll();

        //then
        Post post = all.get(0);
        System.out.println(">>>>>>>>>> createDate = "+post.getCreatedDate()
                +", modifiedDate = "+post.getModifiedDate());
        assertThat(post.getCreatedDate().isAfter(now));
        assertThat(post.getModifiedDate().isAfter(now));
    }
}