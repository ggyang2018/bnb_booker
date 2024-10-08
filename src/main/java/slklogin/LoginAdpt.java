package slklogin;
/* Adapter for Databus for tranfer data object
 * @Author GY
 */
import java.util.List;

public interface LoginAdpt 
{
   public List<LoginBuz> getAllUser();	
   public LoginBuz getUser(int pos);
   public void setActiveUser(LoginBuz us);
   public void intentNext();
   public void setNewUser(LoginBuz us);
   
}
