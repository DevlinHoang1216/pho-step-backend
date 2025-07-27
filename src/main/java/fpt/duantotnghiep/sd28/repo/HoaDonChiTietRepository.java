package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.HoaDon;
import fpt.duantotnghiep.sd28.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> { // Changed Integer to Long based on HoaDonChiTiet entity

    HoaDonChiTiet findFirstByHoaDon(HoaDon hoaDon);

    HoaDonChiTiet findFirstByChiTietSp(ChiTietSanPham chiTietSanPham);

}