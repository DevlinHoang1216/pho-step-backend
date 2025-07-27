package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLieuRepository extends JpaRepository<ChatLieu, Long> {
    // Thêm phương thức này để lấy danh sách chất liệu đã sắp xếp theo tên
    @Query("SELECT cl FROM ChatLieu cl ORDER BY cl.tenChatLieu ASC")
    List<ChatLieu> findAllOrderedByName();// Changed Integer to Long based on ChatLieu entity
}