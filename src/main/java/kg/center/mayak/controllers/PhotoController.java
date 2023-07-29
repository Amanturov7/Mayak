package kg.center.mayak.controllers;

import kg.center.mayak.model.Photo;
import kg.center.mayak.repository.PhotoRepository;
import kg.center.mayak.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("/api/getAllPhotos")
    public ResponseEntity<List<Photo>> getAllPhotos() {
        List<Photo> photos = photoService.getAllPhotos();
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/api/photos/{id}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable Long id) {
        Photo photo = photoService.getPhotoById(id);

        if (photo != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Или другой MediaType в зависимости от типа изображений
            return new ResponseEntity<>(photo.getImageData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/api/photo")
    public String uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        photoService.uploadPhoto(file);
        return "Файл успешно загружен";
    }
}
