package fpt.duantotnghiep.sd28.repo;


import fpt.duantotnghiep.sd28.entity.TrangThai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository // Đánh dấu đây là một Spring Repository
public interface TrangThaiRepository extends JpaRepository<TrangThai, Long> {

    // Phương thức tìm kiếm trạng thái theo tên trạng thái
    Optional<TrangThai> findByTenTrangThai(String tenTrangThai);

    @Query("SELECT tt FROM TrangThai tt WHERE LOWER(tt.tenTrangThai) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TrangThai> searchByTenTrangThai(@Param("keyword") String keyword);
}
