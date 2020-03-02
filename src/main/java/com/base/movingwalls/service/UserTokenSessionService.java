package com.base.movingwalls.service;

import com.base.movingwalls.model.user.UserTokenSession;
import lombok.Getter;


public interface UserTokenSessionService {

    /**
     * Check whether there token username and session-id.
     * not yet expired.
     *
     * @param userTokenSession
     */
    ValidMappingResponse isValidUserTokenSessionMapping(UserTokenSession userTokenSession);

    /**
     * @param userTokenSession
     * @return token session record from data base.
     */
    UserTokenSession saveUserTokenSessionMapping(UserTokenSession userTokenSession);


    /**
     * Class to store isValidUserTokenSessionMapping() response.
     */
    @Getter
    class ValidMappingResponse {

        private boolean valid;
        private UserTokenSession userTokenSession;

        public ValidMappingResponse() {
        }

        public ValidMappingResponse(boolean valid, UserTokenSession userTokenSession) {
            this.valid = valid;
            this.userTokenSession = userTokenSession;
        }

    }

}
