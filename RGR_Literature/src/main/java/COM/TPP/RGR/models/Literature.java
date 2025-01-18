package COM.TPP.RGR.models;

import jakarta.persistence.*;

@Entity
@Table(name = "literature")
public class Literature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "literature_name", nullable = false)
    private String literatureName;

    @Column(name = "department_literature", nullable = false)
    private String departmentLiterature;

    @ManyToOne
    @JoinColumn(name = "univgroup_id", nullable = false)
    private Univgroup univGroup;

    // Геттери и сеттери
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLiteratureName() {
        return literatureName;
    }
    public void setLiteratureName(String literatureName) {
        this.literatureName = literatureName;
    }
    public String getDepartment() {
        return departmentLiterature;
    }
    public void setDepartment(String departmentLiterature) {
        this.departmentLiterature = departmentLiterature;
    }
    public Univgroup getUnivGroup() {
        return univGroup;
    }
    public void setUnivGroup(Univgroup univGroup) {
        this.univGroup = univGroup;
    }
}
