package fpt.duantotnghiep.sd28.repo;

import fpt.duantotnghiep.sd28.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Long> {
    // Chọn toàn bộ entity KichCo, không chỉ tên
    @Query("SELECT kc FROM KichCo kc ORDER BY kc.tenKichCo ASC")
    List<KichCo> findAllOrderedByName();

    boolean existsByMaKichCo(String maKichCo);

    Optional<KichCo> findByMaKichCo(String maKichCo);
}