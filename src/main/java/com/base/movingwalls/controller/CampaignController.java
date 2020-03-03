package com.base.movingwalls.controller;

import com.base.movingwalls.model.campaign.CampaignInfo;
import com.base.movingwalls.repository.CampaignRepositoryEntityManagedImpl;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.base.movingwalls.service.impl.campaign.CampaignFacade.findAllCampaignData;
import static com.base.movingwalls.service.impl.campaign.CampaignFacade.searchByCampaignData;
import static provider.DeferredResultProvider.createDeferredResult;

@Api(value = "documentController", description = "controller has all the document related api's")
@RestController
@CrossOrigin(value = "*")
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignRepositoryEntityManagedImpl managed;

    @PostConstruct
    public void init() throws InterruptedException {
        managed.createDummyCampaign();
        managed.initIndex();
    }

    @ApiOperation(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "Search Campaign",
            notes = "Search Campaign"
    )
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
            @ApiResponse(code = HttpServletResponse.SC_FOUND, message = "Data Found"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Data not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal server error")
    })
    @RequestMapping(value = "/search/{searchtext}/{sortField}/{sortOrder}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<CampaignInfo>>> search(
            @ApiParam(value = "Search Data", name = "searchtext")
            @PathVariable final String searchtext,
            @ApiParam(value = "Sort Field", name = "sortfield")
            @PathVariable final String sortField,
            @ApiParam(value = "Sort Order", name = "sortOrder")
            @PathVariable final String sortOrder) {
        System.out.println(searchtext);
        return createDeferredResult(searchByCampaignData(searchtext, sortField, sortOrder).with(managed), HttpStatus.OK);
    }

    @ApiOperation(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            value = "Get All Campaign Data",
            notes = "Get All Campaign Data"
    )
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
            @ApiResponse(code = HttpServletResponse.SC_FOUND, message = "Data Found"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Data not found"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal server error")
    })
    @RequestMapping(value = "/{sortField}/{sortOrder}", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<List<CampaignInfo>>> getAllCampaignData(
            @ApiParam(value = "Sort Field", name = "sortfield")
            @PathVariable final String sortField,
            @ApiParam(value = "Sort Order", name = "sortOrder")
            @PathVariable final String sortOrder) {
        return createDeferredResult(findAllCampaignData(sortField, sortOrder).with(managed), HttpStatus.OK);
    }
}


