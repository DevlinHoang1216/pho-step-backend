package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.DanhMuc;
import fpt.duantotnghiep.sd28.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {

    DanhMuc findFirstBySanPhams(SanPham sanPham);
    // Tìm danh mục theo mã danh mục
    Optional<DanhMuc> findByMaDanhMuc(String maDanhMuc);

    // Thêm phương thức này để lấy danh mục cho combobox
    @Query("SELECT d FROM DanhMuc d ORDER BY d.tenDanhMuc ASC")
    List<DanhMuc> findAllOrderedByName();
}