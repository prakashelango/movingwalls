package com.base.movingwalls.service.impl.user;

import com.base.movingwalls.model.user.TokenPayloadInfo;
import com.base.movingwalls.model.user.UserTokenSession;
import com.base.movingwalls.repository.UserRepository;
import com.base.movingwalls.repository.UserTokenSessionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


@Service
public class UserTokenSessionServiceImpl {

    private static final Logger LOGGER = Logger.getLogger(UserTokenSessionServiceImpl.class);

    @Value("${config.oauth2.tokenTimeout}")
    private long tokenExpiryTime;

    @Autowired
    private UserTokenSessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    private BiPredicate<TokenPayloadInfo, UserTokenSession> isTokenIdNotSame = (tPayloadInfo, uTokenSession) -> !tPayloadInfo.getTokenId().equals(uTokenSession.getToken());

    private Predicate<UserTokenSession> isTokenTimeExpired =
            userSession -> {
                long currentTimeInMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                ZonedDateTime dataBaseZonedDateTime = userSession.getCreatedTime().atZone(ZoneId.systemDefault());
                long tokenTimeInMillis = dataBaseZonedDateTime.toInstant().toEpochMilli() + (userSession.getExpiryTime() * 1000);

                return currentTimeInMillis >= tokenTimeInMillis;
            };

    public TokenPayloadInfo saveUserTokenSessionMapping(final TokenPayloadInfo tokenPayload) {

        return userRepository.findOneByUsername(tokenPayload.getUserName())
                .map(user -> validateTokenandDeleteIfnotValid(tokenPayload))
                .filter(tokenPayloadInfo -> !tokenPayloadInfo.getValid() && !tokenPayloadInfo.getMessage().equalsIgnoreCase("User Doesnot Exists"))
                .map(tokenPayloadInfo -> saveTokenSessionandReturnTokenPayload().apply(tokenPayload))
                .orElse(TokenPayloadInfo.builder().with(TokenPayloadInfo::getMessage, "UserId and Password Doesnot Matches").with(TokenPayloadInfo::getValid, false).build());

    }

    private Function<TokenPayloadInfo, TokenPayloadInfo> saveTokenSessionandReturnTokenPayload() {
        return tokenPayload -> {
            UserTokenSession userTokenSession = sessionRepository.save(new UserTokenSession(tokenPayload.getUserName(), generateToken(), "Sessionid-" + generateToken(), tokenExpiryTime));
            System.out.println(userTokenSession);
            return TokenPayloadInfo.builder().on(TokenPayloadInfo::getUserName).set(userTokenSession.getUsername())
                    .on(TokenPayloadInfo::getTokenId).set(userTokenSession.getToken())
                    .on(TokenPayloadInfo::getValid).set(true)
                    .on(TokenPayloadInfo::getMessage).set("Token is Valid").build();
        };
    }

    public TokenPayloadInfo validateTokenandDeleteIfnotValid(final TokenPayloadInfo tokenPayload) {
        return sessionRepository.findOneByUsername(tokenPayload.getUserName())
                .filter(us -> isTokenIdNotSame.test(tokenPayload, us) && isTokenTimeExpired.test(us))
                .map(uts -> deleteExpiredTokenThenReturnStatus().apply(uts))
                .orElseGet(() -> isValidTokenOrFindUserExists().apply(tokenPayload));
    }

    private Function<TokenPayloadInfo, TokenPayloadInfo> isValidTokenOrFindUserExists() {
        return tokenPayload -> sessionRepository.findOneByUsername(tokenPayload.getUserName())
                .filter(us -> !isTokenIdNotSame.test(tokenPayload, us) && !isTokenTimeExpired.test(us))
                .map(us -> TokenPayloadInfo.builder().with(TokenPayloadInfo::getMessage, "Token is Valid").with(TokenPayloadInfo::getValid, true).build())
                .orElseGet(() -> findUserExists().apply(tokenPayload));
    }

    private Function<TokenPayloadInfo, TokenPayloadInfo> findUserExists() {
        return tokenPayload -> userRepository.findOneByUsername(tokenPayload.getUserName())
                .map(user -> TokenPayloadInfo.builder().with(TokenPayloadInfo::getMessage, "Token is Not Valid Expired").with(TokenPayloadInfo::getValid, false).build())
                .orElseGet(() -> TokenPayloadInfo.builder().with(TokenPayloadInfo::getMessage, "User Doesnot Exists").with(TokenPayloadInfo::getValid, false).build());
    }

    private Function<UserTokenSession, TokenPayloadInfo> deleteExpiredTokenThenReturnStatus() {
        return userTokenSession -> {
            sessionRepository.delete(userTokenSession);
            return TokenPayloadInfo.builder().on(TokenPayloadInfo::getMessage).set("Token is Not Valid Expired")
                    .on(TokenPayloadInfo::getValid).set(false).build();
        };
    }

    private String generateToken() {
        int lowerLimit = 97;
        int upperLimit = 122;
        Random random = new Random();
        StringBuffer r = new StringBuffer(10);
        for (int i = 0; i < 8; i++) {
            int nextRandomChar = lowerLimit
                    + (int) (random.nextFloat()
                    * (upperLimit - lowerLimit + 1));
            r.append((char) nextRandomChar);
        }
        return r.toString();
    }

}
