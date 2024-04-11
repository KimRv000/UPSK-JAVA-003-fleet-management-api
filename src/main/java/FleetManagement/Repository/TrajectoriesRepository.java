package FleetManagement.Repository;

import FleetManagement.Entity.TrajectoriesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface TrajectoriesRepository extends JpaRepository<TrajectoriesEntity, Integer> {

    @Query("SELECT new map(t.latitude as latitude, t.longitude as longitude, t.date as date) " +
            "FROM TrajectoriesEntity t " +
            "WHERE t.taxiId = :taxiId")
    List<Map<String, Object>> findTrajectoryInfoByTaxiId(@Param("taxiId") int taxiId);

    @Query("SELECT t FROM TrajectoriesEntity t WHERE t.taxiId = ?1 AND DATE(t.date) = DATE(?2)")
    Page<TrajectoriesEntity> findTrajectoriesByTaxiIdAndDate(Long taxiId, LocalDate date, Pageable pageable);


    @Query("SELECT new map(t.taxi.id as id, t.taxi.plate as plate, t.latitude as latitude, t.longitude as longitude, t.date as date) " +
            "FROM TrajectoriesEntity t " +
            "WHERE t.date IN (SELECT MAX(t2.date) FROM TrajectoriesEntity t2 WHERE t2.taxi = t.taxi GROUP BY t2.taxi)")
    Page<Map<String, Object>> findLatestLocationPerTaxiPaged(Pageable pageable);

}
