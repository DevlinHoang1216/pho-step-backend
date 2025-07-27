package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.AnhSanPham;
import fpt.duantotnghiep.sd28.entity.ChiTietSanPham; // Đảm bảo ChiTietSanPham có ID là UUID
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // Import UUID

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, Long> {

    AnhSanPham findFirstByChiTietSp(ChiTietSanPham chiTietSanPham);

    // Cập nhật kiểu dữ liệu của chiTietSpId từ Long sang UUID
    @Query("SELECT a FROM AnhSanPham a WHERE a.chiTietSp.id = :chiTietSpId")
    List<AnhSanPham> findByChiTietSpId(UUID chiTietSpId); // Thay Long bằng UUID

    @Query("SELECT a FROM AnhSanPham a WHERE a.chiTietSp.sanPham.id = :sanPhamId AND a.laAnhDaiDien = true")
    Optional<AnhSanPham> findRepresentativeBySanPhamId(Long sanPhamId);

    @Query("SELECT a FROM AnhSanPham a WHERE a.chiTietSp.sanPham.id = :sanPhamId")
    List<AnhSanPham> findBySanPhamId(Long sanPhamId);
}
