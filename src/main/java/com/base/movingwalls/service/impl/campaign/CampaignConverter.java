package com.base.movingwalls.service.impl.campaign;

import com.base.movingwalls.common.core.Builder;
import com.base.movingwalls.model.campaign.Campaign;
import com.base.movingwalls.model.campaign.CampaignInfo;

import java.time.LocalDateTime;
import java.util.function.Function;

public class CampaignConverter {

    public static Campaign convertToCampaign(final CampaignInfo source) {
        return Campaign.builder()
                .on(campaign -> campaign.getEndDate())
                .set(source.getEndDate())
                .on(campaign -> campaign.getName())
                .set(source.getName())
                .on(campaign -> campaign.getStatus())
                .set(source.getStatus())
                .on(campaign -> campaign.getReport())
                .set(source.getReport())
                .on(campaign -> campaign.getStartDate())
                .set(source.getStartDate())
                .on(campaign -> campaign.getDuration())
                .set(source.getStartDate().toString() + " - " + source.getEndDate().toString())
                .build();
    }


    private static Function<LocalDateTime, String> formatDatebyDateMonthYear =
            localDateTime -> localDateTime.getDayOfMonth() + " " + localDateTime.getMonth() + " " + localDateTime.getYear();

    public static CampaignInfo convertToInfo(final Campaign source) {
        return Builder.of(CampaignInfo.class)
                .with(CampaignInfo::getDuration, formatDatebyDateMonthYear.apply(source.getStartDate()) + " - " + formatDatebyDateMonthYear.apply(source.getEndDate()))
                .with(CampaignInfo::getEndDate, source.getEndDate())
                .with(CampaignInfo::getStartDate, source.getStartDate())
                .with(CampaignInfo::getName, source.getName())
                .with(CampaignInfo::getStatus, source.getStatus())
                .with(CampaignInfo::getReport, source.getReport())
                .build();
    }

}
