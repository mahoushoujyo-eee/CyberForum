package bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User
{
    public int id;
    public String userName;
    public String nickName;
    public String encryptedPassword;
    public String email;
}
