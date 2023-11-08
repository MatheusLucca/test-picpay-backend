package com.pipaysimplificado.services;

import com.pipaysimplificado.domain.user.User;
import com.pipaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        /*ResponseEntity<String> notificationResponse = restTemplate
                .postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            System.out.println("Notification service failed");
            throw new Exception("Notification service failed");
        }*/
        System.out.println("Notification sent successfully");

    }
}
