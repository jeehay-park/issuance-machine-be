package com.ictk.issuance.data.model;

import com.ictk.issuance.common.annotations.InjectSequenceValue;
import com.ictk.issuance.common.annotations.ValidateString;
import com.ictk.issuance.common.utils.CommonUtils;
import com.ictk.issuance.constants.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Slf4j
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_detail")
@Entity
public class WorkDetail {

    @InjectSequenceValue(sequencename = "seq", tablename = "work_detail")
    @Column(name = "seq", unique = true, nullable = false, updatable = false)
    public long seq;

    @Id
    @Column(name = "workdet_id", unique = true, nullable = false)
    private String workdetId;

    @Column(name = "work_id", nullable = false)
    private String workId;

    @Column(name = "detail_data")
    private String detailData;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "comment")
    private String comment;

    // Relationship with WorkInfo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id", referencedColumnName = "work_id", insertable = false, updatable = false)
    private WorkInfo workInfo;

    @PrePersist
    public void onSave(){
        if(!CommonUtils.hasValue(workdetId) || AppConstants.TEMPORARY_ID.equals(workdetId))
            workId = "workdet_" + String.format("%04d", seq);

        updatedAt = LocalDateTime.now();
    }

}
