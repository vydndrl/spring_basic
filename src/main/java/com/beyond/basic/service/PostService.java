package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResDto> postList() {
        List<PostResDto> postResDtoList =  new ArrayList<>();
        List<Post> postList = postRepository.findAll();
        for (Post post : postList) {
            postResDtoList.add(post.fromEntity());
            System.out.println("저자의 이름은 : " + post.getMember().getName());
        }
        return postResDtoList;
    }
}
