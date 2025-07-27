package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {
    // Tìm thương hiệu theo mã thương hiệu
    Optional<ThuongHieu> findByMaThuongHieu(String maThuongHieu);

    // Thêm phương thức này để lấy danh sách thương hiệu đã sắp xếp theo tên
    @Query("SELECT th FROM ThuongHieu th ORDER BY th.tenThuongHieu ASC")
    List<ThuongHieu> findAllOrderedByName();
}
