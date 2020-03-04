package com.base.movingwalls.model.campaign;

import com.base.movingwalls.model.user.TokenPayloadInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "Campaign Filter", description = "API Info Used to provide filter's for campaign.")
public class CampaignFilter {

    @ApiModelProperty(notes = "Mode of Campaign Ex: All, Completed, Ongoing, Historical")
    private String campaignStatus;

    @ApiModelProperty(notes = "Search based on Campaign Name")
    private String campaignSearchKeyWord;

    @ApiModelProperty(notes = "Location of Campaign Ex: Maharastra, Mumbai, Illinois, New York" )
    private String campaignlocation;

    @ApiModelProperty(notes = "Date of Campaign Occur Ex: Last Month, Next Month" )
    private LocalDateTime campaignDate;

    @ApiModelProperty(notes = "Field which to be sorted" )
    private String sortby;

    @ApiModelProperty(notes = "Campaign Field Order of Sorting" )
    private String sortbyOrder;

    @ApiModelProperty(notes = "User Information and Tokenid for Authentication" )
    private TokenPayloadInfo tokenPayloadInfo;

    @ApiModelProperty(notes = "Type of Field Ex: String, Number, Date" )
    private String sortFieldType;

    @ApiModelProperty(notes = "Campaign Records Page Number Ex: Starts from 1" )
    private int startPage;

    @ApiModelProperty(notes = "Number of Campaign Records to Display" )
    private int totalPages;
}
