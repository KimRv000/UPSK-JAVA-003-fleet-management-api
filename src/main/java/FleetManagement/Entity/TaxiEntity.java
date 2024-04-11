package FleetManagement.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="taxis")
public class TaxiEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable = false,unique = true)
    private int id;

    @Column(name="plate", nullable = false, unique = true)
    private String plate;


    public TaxiEntity() {}

    public TaxiEntity(int id, String plate) {
        this.id = id;
        this.plate = plate;
    }

    @OneToMany(mappedBy = "taxi", cascade = CascadeType.ALL)
    private List<TrajectoriesEntity> trajectories = new ArrayList<>();
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}


