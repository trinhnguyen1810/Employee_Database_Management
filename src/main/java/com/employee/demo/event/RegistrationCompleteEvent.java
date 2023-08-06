package com.employee.demo.event;
import lombok.Setter;
import lombok.Getter;
import com.employee.demo.entity.User;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;

    // url for users to click on for email
    private String applicationUrl;
    public RegistrationCompleteEvent(User user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
