package com.base.movingwalls.model.campaign;

import com.base.movingwalls.common.core.Builder;
import lombok.Data;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Indexed
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Fields({
            @Field,
            @Field(name = "name_sort", analyze = Analyze.NO, store = Store.YES, index = Index.YES)
    })
    @SortableField(forField = "name_sort")
    private String name;

    @Field(termVector = TermVector.YES)
    @Fields({
            @Field,
            @Field(name = "duration_sort", analyze = Analyze.NO, store = Store.YES, index = Index.YES)
    })
    @SortableField(forField = "duration_sort")
    private String duration;

    @Fields({
            @Field,
            @Field(name = "status_sort", analyze = Analyze.NO, store = Store.YES, index = Index.YES)
    })
    @SortableField(forField = "status_sort")
    private String status;

    @Field(analyze = Analyze.NO, store = Store.NO, index = Index.NO)
    private LocalDateTime startDate;

    @Field(analyze = Analyze.NO, store = Store.NO, index = Index.NO)
    private LocalDateTime endDate;

    @Field
    private String report;

    public Campaign() {
    }

    /*@PrePersist
    protected void () {
        duration = CampaignConverter.formatDatebyDateMonthYear.apply();
    }*/

    public Campaign(String name, String duration, String status, LocalDateTime startDate, LocalDateTime endDate, String report) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.report = report;
    }

    public static Builder<Campaign> builder() {
        return Builder.of(Campaign.class);
    }
}
