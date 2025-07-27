package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.DanhGiaSanPham;
import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhGiaSanPhamRepository extends JpaRepository<DanhGiaSanPham, Long> { // Changed Integer to Long based on DanhGiaSanPham entity

    DanhGiaSanPham findFirstByKhachHang(KhachHang khachHang);

    DanhGiaSanPham findFirstBySanPham(SanPham sanPham);

}