package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChiTietSanPham;
import fpt.duantotnghiep.sd28.entity.GioHang;
import fpt.duantotnghiep.sd28.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> { // Changed Integer to Long based on GioHangChiTiet entity

    GioHangChiTiet findFirstByGioHang(GioHang gioHang);

    GioHangChiTiet findFirstByChiTietSp(ChiTietSanPham chiTietSanPham);

}