package com.jschwery.securitydemo.event;

import com.jschwery.securitydemo.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserRegisterEmailEvent extends ApplicationEvent {
    private User user;
    private String url;
    public UserRegisterEmailEvent(User userToPublish, String urlSent) {
        super(userToPublish);
        this.url = urlSent;
        this.user = userToPublish;
    }


}
