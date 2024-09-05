package io.bootify.ngo_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Projects")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Project {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(precision = 17, scale = 2)
    private BigDecimal goalAmount;

    @Column(precision = 17, scale = 2)
    private BigDecimal raisedAmount;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Integer createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ProjectType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "project")
    private Set<ChildProfile> projectChildProfiles;

    @OneToMany(mappedBy = "project")
    private Set<Donation> projectDonations;

    @OneToMany(mappedBy = "project")
    private Set<RecurringDonation> projectRecurringDonations;

    @OneToMany(mappedBy = "project")
    private Set<Allocation> projectAllocations;

    @OneToMany(mappedBy = "project")
    private Set<File> projectFiles;

    @OneToMany(mappedBy = "project")
    private Set<Milestone> projectMilestones;

    @OneToMany(mappedBy = "project")
    private Set<Task> projectTasks;

    @OneToMany(mappedBy = "project")
    private Set<Transaction> projectTransactions;

    @OneToMany(mappedBy = "project")
    private Set<ProjectAssinee> projectProjectAssinees;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
