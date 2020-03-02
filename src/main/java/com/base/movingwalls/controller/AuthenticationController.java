package com.base.movingwalls.controller;

import com.base.movingwalls.model.user.TokenPayloadInfo;
import com.base.movingwalls.service.impl.user.UserTokenSessionServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "*")
@Api(value = "Authentication API", description = "Authenticate user using authorization token.")
public class AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);

    @Autowired
    private UserTokenSessionServiceImpl userTokenSessionService;


    @ApiOperation(value = "Authenticated User Login", response = TokenPayloadInfo.class)
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TokenPayloadInfo.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<TokenPayloadInfo> login(@RequestBody TokenPayloadInfo tokenPayload) {

        return new ResponseEntity(userTokenSessionService.saveUserTokenSessionMapping(tokenPayload), HttpStatus.OK);
    }


    @ApiOperation(value = "Validate the given token", response = TokenPayloadInfo.class)
    @RequestMapping(value = "/validateToken", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = TokenPayloadInfo.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<TokenPayloadInfo> validateToken(@RequestBody TokenPayloadInfo tokenPayload) {

        TokenPayloadInfo tokenPayloadInfo = userTokenSessionService.validateTokenandDeleteIfnotValid(tokenPayload);
        if (tokenPayloadInfo.getValid()) {
            return new ResponseEntity<>(tokenPayloadInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tokenPayloadInfo, HttpStatus.UNAUTHORIZED);
        }
    }

}
