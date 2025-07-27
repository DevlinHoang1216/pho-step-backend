package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.NhanVien;
import fpt.duantotnghiep.sd28.entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, UUID> {

    PhieuGiamGia findFirstByNhanVienTao(NhanVien nhanVien);

}