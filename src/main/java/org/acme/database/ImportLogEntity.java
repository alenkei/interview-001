package org.acme.database;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "IMPORT_LOG")
public class ImportLogEntity extends PanacheEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime importTime;

    private String fileName;

    private long size;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReportDataEntity> data = new ArrayList<>();

    public LocalDateTime getImportTime() {
        return importTime;
    }

    public ImportLogEntity setImportTime(LocalDateTime importTime) {
        this.importTime = importTime;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ImportLogEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public long getSize() {
        return size;
    }

    public ImportLogEntity setSize(long size) {
        this.size = size;
        return this;
    }

    public List<ReportDataEntity> getData() {
        return data;
    }

    public ImportLogEntity setData(List<ReportDataEntity> data) {
        this.data = data;
        return this;
    }
}
