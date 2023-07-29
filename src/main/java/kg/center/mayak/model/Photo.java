package kg.center.mayak.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Lob
    private byte[] imageData;

    // Конструкторы, геттеры, сеттеры и другие методы

    // Конструктор без аргументов для JPA
    public Photo() {}

    // Конструктор для создания объекта Photo с именем файла и данными изображения
    public Photo(String fileName, byte[] imageData) {
        this.fileName = fileName;
        this.imageData = imageData;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
