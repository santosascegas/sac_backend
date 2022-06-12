package com.sac.backend.files;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try {
//            if (!fileService.acceptedExtension(file.getOriginalFilename())) {
//                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Tipo de arquivo recusado!");
//            }
            fileService.save(file);
            return ResponseEntity.status(HttpStatus.OK).body("Arquivo armazenado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Não foi possivel armazenar o arquivo!");
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id, HttpServletRequest request) throws FileNotFoundException {
        Resource file = fileService.load(id);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
