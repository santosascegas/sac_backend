package com.sac.backend.files;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.util.EnumUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {

    private final FileRepository fileRepository;
    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public Boolean acceptedExtension(String fileName) {
        String ext = getFileExtension(fileName);
        for (FileExtensionEnum e : FileExtensionEnum.class.getEnumConstants()) {
            if (e.name().equals(ext.replaceAll("\\.", "").toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public FileStorage generateFS(MultipartFile file) {
        try {
            Random salt = new Random();
            FileStorage f = new FileStorage();
            if (!Objects.equals(file.getOriginalFilename(), "")) {
                String fileName = new Date().getTime() + salt.nextInt(10) + "-file." + getFileExtension(file.getOriginalFilename());
                Files.copy(file.getInputStream(), this.root.resolve(fileName));
                f.setFileName(fileName);
                f.setPath(this.root.resolve(fileName).toString());
            }
            return f;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOnDisk(MultipartFile file) {
        try {
            String fileName = new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());
            FileStorage f = new FileStorage();
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            f.setFileName(fileName);
            f.setPath(this.root.resolve(fileName).toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(MultipartFile file) {
        try {
            String fileName = new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());
            FileStorage f = new FileStorage();
            Files.copy(file.getInputStream(), this.root.resolve(fileName));
            f.setFileName(fileName);
            f.setPath(this.root.resolve(fileName).toString());
            fileRepository.save(f);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource load(Long id) {
        try {
            Path file = null;
            try {
                file = root.resolve(fileRepository.getById(id).getFileName());
            } catch (NullPointerException e) {
                return new UrlResource("404");
            }
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possivel ler o arquivo!");
            }
        } catch (MalformedURLException e) {
            log.error("ARQUIVO NÃO ENCONTRADO!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public boolean delete(Long id) {
        if (fileRepository.existsById(id)) {
            try {
                Files.delete(Path.of(fileRepository.getById(id).getPath()));
                fileRepository.deleteById(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }


}
