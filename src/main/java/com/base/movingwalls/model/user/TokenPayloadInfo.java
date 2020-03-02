package com.base.movingwalls.model.user;

import com.base.movingwalls.common.core.Builder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Token Payload Transfer between UI")
@Data
public class TokenPayloadInfo {

    @ApiModelProperty(notes = "Holds User Name")
    private String userName;

    @ApiModelProperty(notes = "Holds Password")
    private String password;

    @ApiModelProperty(notes = "Holds Token-Id For Authentication")
    private String tokenId;

    @ApiModelProperty(notes = "Is Valid")
    private Boolean valid;

    @ApiModelProperty(notes = "Authentication Response Message")
    private String message;

    public static Builder<TokenPayloadInfo> builder() {
        return Builder.of(TokenPayloadInfo.class);
    }

}
