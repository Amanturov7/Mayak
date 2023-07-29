package kg.center.mayak.repository;

import kg.center.mayak.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Здесь можно добавить необходимые методы для работы с фотографиями, если нужно
}
