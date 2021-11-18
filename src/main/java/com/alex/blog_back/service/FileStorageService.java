package com.alex.blog_back.service;

import com.alex.blog_back.model.ProfilPic;
import com.alex.blog_back.repo.ProfilPicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileStorageService {

    private final ProfilPicRepository profilPicRepository;

    public ProfilPic store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ProfilPic FileDB = new ProfilPic(fileName, file.getContentType(), file.getBytes());

        return profilPicRepository.save(FileDB);
    }

    public ProfilPic getFile(String id) {
        return profilPicRepository.findById(id).get();
    }

    public Stream<ProfilPic> getAllFiles() {
        return profilPicRepository.findAll().stream();
    }
}