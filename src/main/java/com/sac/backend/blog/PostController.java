package com.sac.backend.blog;

import com.sac.backend.files.FileService;
import com.sac.backend.files.FileStorage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @GetMapping(value = "/todos")
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Post>> getAllPublic() {
        return ResponseEntity.ok(postService.findAllPublic());
    }

    @GetMapping(value = "/admin/{id}")
    public ResponseEntity<?> getByIdAdmin(@PathVariable Long id) {
        Optional<Post> result = postService.findByIdAdmin(id);
        return result.isPresent() ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Post> result = postService.findById(id);
        return result.isPresent() ? ResponseEntity.ok(result) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(value = "/")
    public ResponseEntity<Post> post(@ModelAttribute PostDTO obj) {
        MultipartFile file = obj.getImage();
        MultipartFile audio = obj.getAudio();
        Post p = new Post();
        FileStorage f = fileService.generateFS(file);
        FileStorage a = fileService.generateFS(audio);

        p.setMessage(obj.getMessage());
        p.setName(obj.getName());
        p.setPhone(obj.getPhone());
        p.setImage(f);
        p.setAudio(a);
        p.setQuestion_1(obj.getQuestion_1());
        p.setQuestion_2(obj.getQuestion_2());

        return ResponseEntity.ok(postService.create(p, file, audio));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody Post obj) {
        return postService.update(obj) ? ResponseEntity.ok(obj) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return postService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
