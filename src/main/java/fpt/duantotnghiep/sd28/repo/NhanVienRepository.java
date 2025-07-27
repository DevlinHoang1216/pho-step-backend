package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.ChucVu;
import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface NhanVienRepository extends JpaRepository<NhanVien, Long> { // Changed Integer to Long based on NhanVien entity

    NhanVien findFirstByTaiKhoan(TaiKhoan taiKhoan);

    NhanVien findFirstByChucVu(ChucVu chucVu);

}