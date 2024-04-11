package FleetManagement.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "trajectories")
public class TrajectoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idTrajectory;

    @ManyToOne
    @JoinColumn(name = "taxi_id")
    private TaxiEntity taxi;

    @Column(name = "taxi_id", insertable = false, updatable = false)
    private int taxiId;
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;


    public int getIdTrajectory() {
        return idTrajectory;
    }

    public void setIdTrajectory(int id) {
        this.idTrajectory = id;
    }

    public int getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(int taxiId) {
        this.taxiId = taxiId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
