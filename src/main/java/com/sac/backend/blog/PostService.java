package com.sac.backend.blog;

import com.sac.backend.appointment.Appointment;
import com.sac.backend.appointment.AppointmentRepository;
import com.sac.backend.files.FileService;
import com.sac.backend.interfaces.ServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AppointmentRepository appointmentRepository;
    private final FileService fileService;

    public Post create(Post obj, MultipartFile file, MultipartFile audio) {
        System.out.println(audio.getName());
        Optional<Appointment> appointment = appointmentRepository.clientExists(obj.getPhone());
        if (appointment.isPresent()) {
            obj.setIsActive(true);
        }
        postRepository.save(obj);
        return obj;
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
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
}
