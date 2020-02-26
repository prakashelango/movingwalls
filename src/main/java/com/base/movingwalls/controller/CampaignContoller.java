package com.base.movingwalls.controller;

import com.base.movingwalls.model.campaign.CampaignInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static provider.DeferredResultProvider.createDeferredResultTwoTrack;

@Api(value = "documentController", description = "controller has all the document related api's")
public class CampaignContoller {

    @ApiOperation(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "Save Campaign",
            notes = "Save Campaign"
    )
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST)
    public DeferredResult<ResponseEntity<List<CampaignInfo>>> saveCampaign(
            @ApiParam(value = "Campaign Info", name = "campaign Info")
            @RequestBody final CampaignInfo campaignInfo) {
        return createDeferredResultTwoTrack(
                save(uploadInfo)
                        .with(DocumentConfig
                                .builder()
                                .with(DocumentConfig::getFmsConfig, fmsConfig)
                                .with(DocumentConfig::getFileWriter, fileWritter(file))
                                .build()), HttpStatus.ACCEPTED);
    }
}


