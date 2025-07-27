package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.MaGiamGia;
import fpt.duantotnghiep.sd28.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MaGiamGiaRepository extends JpaRepository<MaGiamGia, Long> { // Changed Integer to Long based on MaGiamGia entity

    MaGiamGia findFirstByPhieuGiamGia(PhieuGiamGia phieuGiamGia);

    MaGiamGia findFirstByKhachHangDuocCap(KhachHang khachHang);

}