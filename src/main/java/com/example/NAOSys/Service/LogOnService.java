package com.example.NAOSys.Service;

import com.example.NAOSys.Entity.User;
import com.example.NAOSys.POJO.SessionManager;
import com.example.NAOSys.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class LogOnService
{
    @Autowired
    private UserRepo userRepo;

    SessionManager sessionManager = new SessionManager();

    public Map<String, Object> userLogOn(String email, String password)
    {
        Optional<User> user = userRepo.findByEmailAndPassword(email, password);
        if(user.isPresent())
        {
            List<Map<String, Object>> duplicateSession = sessionManager.getList();
            String sessionId = null;
            if(user.get().getRole().equalsIgnoreCase("Recruiter"))
            {
               sessionId = "rId_.*"+UUID.randomUUID().toString()+"==";
            }
            else
            {
                sessionId = "cId_.*"+UUID.randomUUID().toString()+"==";
            }
            Long user_id = user.get().getUserId();
            Instant issuedAt = Instant.now();
            Instant expiry = Instant.now().plusSeconds(600);

            for(int i = 0; i < duplicateSession.size(); i++)
            {
                if(duplicateSession.get(i).containsValue(user_id))
                {
                    Instant expired = (Instant) duplicateSession.get(i).get("expiry");
                    if(Instant.now().isAfter(expired)) {
                        Iterator<Map<String, Object>> iterator = duplicateSession.iterator();

                        while (iterator.hasNext()) {
                            Map<String, Object> session = iterator.next();

                            if (sessionId.equals(session.get("user_sessionId"))) {
                                iterator.remove();

                            }
                        }
                    }
                    else
                    {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "An active session is already there for the user "+user.get().getFirstName()+" "+user.get().getLastName());
                    }
                }
            }

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("user_id", user_id);
            map.put("user_sessionId", sessionId);
            map.put("issuedAt", issuedAt);
            map.put("expiry", expiry);

            sessionManager.getList().add(map);

            return map;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found by credentials....email = "+email+", password = "+password);
        }
    }


    public Map<String, Object> userLogout(String sessionId) {

        if (sessionId == null || sessionId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "SessionId must not be null or empty"
            );
        }

        List<Map<String, Object>> sessions = sessionManager.getList();

        Iterator<Map<String, Object>> iterator = sessions.iterator();

        while (iterator.hasNext()) {
            Map<String, Object> session = iterator.next();

            if (sessionId.equals(session.get("user_sessionId"))) {
                iterator.remove();

                return Map.of(
                        "message", "Successfully logged out of all sessions."
                );
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error resolving user from JSON.");
    }

}
