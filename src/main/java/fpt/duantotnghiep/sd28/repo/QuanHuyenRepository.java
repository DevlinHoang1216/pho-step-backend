package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.QuanHuyen;
import fpt.duantotnghiep.sd28.entity.TinhThanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuanHuyenRepository extends JpaRepository<QuanHuyen, Long> {

    QuanHuyen findFirstByTinhThanh(TinhThanh tinhThanh);

}