package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.KhachHang;
import fpt.duantotnghiep.sd28.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> { // Changed Integer to Long based on KhachHang entity

    KhachHang findFirstByTaiKhoan(TaiKhoan taiKhoan);

}