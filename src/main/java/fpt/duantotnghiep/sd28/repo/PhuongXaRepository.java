package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.PhuongXa;
import fpt.duantotnghiep.sd28.entity.QuanHuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuongXaRepository extends JpaRepository<PhuongXa, Long> {

    PhuongXa findFirstByQuanHuyen(QuanHuyen quanHuyen);

}