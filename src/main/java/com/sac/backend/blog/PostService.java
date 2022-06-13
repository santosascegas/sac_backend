package com.sac.backend.blog;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(Post obj, MultipartFile file, MultipartFile audio) {
        System.out.println(audio.getName());
        obj.setIsPublic(true);
        postRepository.save(obj);
        return obj;
    }

    public Optional<Post> findByIdAdmin(Long id) {
        return postRepository.findById(id);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findByIdPublic(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public boolean update(Post obj) {
        if (postRepository.existsById(obj.getId())) {
            postRepository.save(obj);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Post> findAllPublic() {
        return postRepository.listAllPublic();
    }
}
