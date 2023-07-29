package kg.center.mayak.service;

import kg.center.mayak.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {
    List<Photo> getAllPhotos();
    void uploadPhoto(MultipartFile file) throws IOException;
    Photo getPhotoById(Long id);
}
