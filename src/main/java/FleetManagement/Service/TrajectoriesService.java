package FleetManagement.Service;

import FleetManagement.Entity.TrajectoriesEntity;
import FleetManagement.Repository.TrajectoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TrajectoriesService {

    private final TrajectoriesRepository trajectoriesRepository;

    @Autowired
    public TrajectoriesService(TrajectoriesRepository trajectoriesRepository) {
        this.trajectoriesRepository = trajectoriesRepository;
    }

    public List<Map<String, Object>> findTrajectoryInfoByTaxiId(int taxiId) {
        return trajectoriesRepository.findTrajectoryInfoByTaxiId(taxiId);
    }

    public Page<TrajectoriesEntity> findTrajectoriesByTaxiIdAndDate(Long taxiId, LocalDate date, Pageable pageable) {
        return trajectoriesRepository.findTrajectoriesByTaxiIdAndDate(taxiId, date, pageable);
    }

    public List<Map<String, Object>> findTrajectoriesByTaxiIdAndDate(int taxiId, LocalDate date) {
        return null;
    }
}