package kg.center.mayak.service;

import kg.center.mayak.model.Photo;
import kg.center.mayak.repository.PhotoRepository;
import kg.center.mayak.service.PhotoService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
    @Override
    public Photo getPhotoById(Long id) {
        Optional<Photo> photoOptional = photoRepository.findById(id);
        return photoOptional.orElse(null);
    }
    @Override
    public void uploadPhoto(MultipartFile file) throws IOException {
        // Проверка, что файл не пустой
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Ошибка: Файл пустой!");
        }

        // Генерация уникального имени файла для избежания коллизий
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Получение текущей рабочей директории проекта
        String projectDir = System.getProperty("user.dir");

        // Получение абсолютного пути к директории uploaded-photos внутри проекта
        String uploadDir = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "uploaded-photos";

        // Создание нового файла в директории uploaded-photos
        File dest = new File(uploadDir, fileName);

        // Считывание данных из MultipartFile в байтовый массив
        byte[] imageData = file.getBytes();

        // Сохранение данных изображения в новый файл
        Files.write(dest.toPath(), imageData);

        // Создание сущности Photo и сохранение ее в базу данных
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setImageData(imageData);
        photoRepository.save(photo);
    }
    @Override
    public ResponseEntity<String> deletePhoto(Long photoId) {
        try {
            Photo photo = photoRepository.findById(photoId).orElse(null);
            if (photo == null) {
                return new ResponseEntity<>("Фотография не найдена", HttpStatus.NOT_FOUND);
            }

            String projectDir = System.getProperty("user.dir");
            String uploadDir = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "uploaded-photos";
            String fileName = photo.getFileName();
            File file = new File(uploadDir, fileName);

            if (!file.exists()) {
                return new ResponseEntity<>("Файл фотографии не найден", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (file.delete()) {
                photoRepository.delete(photo);
                return new ResponseEntity<>("Фотография успешно удалена", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Ошибка при удалении файла фотографии", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка при удалении фотографии: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
