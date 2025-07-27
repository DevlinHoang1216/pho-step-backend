package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {
    // Phương thức kiểm tra mã màu sắc đã tồn tại chưa
    boolean existsByMaMauSac(String maMauSac);

    // Phương thức để lấy tất cả màu sắc và sắp xếp theo tên
    List<MauSac> findAllByOrderByTenMauSacAsc();

    // Bạn có thể thêm các phương thức tìm kiếm khác nếu cần (ví dụ: findByHex(String hex))
    Optional<MauSac> findByMaMauSac(String maMauSac);
}