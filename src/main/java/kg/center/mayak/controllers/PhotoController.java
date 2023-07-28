package kg.center.mayak.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Замени URL на адрес своего React frontend
public class PhotoController {

    // Получение пути к директории для загруженных фотографий из application.properties
    @Value("${upload.path}")
    private String uploadPath;

    // Список для хранения загруженных фотографий (здесь не используем БД для простоты примера)
    private List<String> photos = new ArrayList<>();

    @GetMapping("/api/getAllPhotos")
    public List<String> getAllPhotos() {
        // Верни список всех фотографий
        return photos;
    }

    @PostMapping("/api/photo")
    public String uploadPhoto(@RequestParam("file") MultipartFile file) throws IOException {
        // Проверка, что файл не пустой
        if (file.isEmpty()) {
            return "Ошибка: Файл пустой!";
        }

        // Генерация уникального имени файла для избежания коллизий
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Получение текущей рабочей директории проекта
        String projectDir = System.getProperty("user.dir");

        // Получение абсолютного пути к директории uploaded-photos внутри проекта
        String uploadDir = projectDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "uploaded-photos";

        // Загрузка файла в указанную директорию (абсолютный путь)
        File dest = new File(uploadDir + File.separator + fileName);
        file.transferTo(dest);

        // Добавление имени файла в список
        photos.add(fileName);

        return "Файл успешно загружен: " + fileName;
    }

}
