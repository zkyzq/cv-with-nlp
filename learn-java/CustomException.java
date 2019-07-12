/**
 * @author    tangxi.zq
 * @apiNote   自定义异常：建议从RuntimeException派生出一个BaseException，然后其他各种异常都是从BaseException派生
 */

 public class CustomException
 {
	public static void main(String[] args) {
		String token = login("admin2", "password");
		System.out.println("Token: " + token);
	}

	static String login(String username, String password) {
		if (username.equals("admin")) {
			if (password.equals("password")) {
				return "xxxxxx";
			} else {
				// 抛出LoginFailedException:
				throw new LoginFailedException("Bad username or password.");
			}
		} else {
			// 抛出UserNotFoundException:
			throw new UserNotFoundException("User not found.");
		}
	}
 }
 class BaseException extends RuntimeException
 {
     //必须要声明各种参数的构造函数
     public BaseException()
     {
         super();
     }
     public BaseException(String message,Throwable cause)
     {
         super(message,cause);
     }
     public BaseException(String message)
     {
         super(message);
     }
     public BaseException(Throwable cause)
     {
         super(cause);
     }
 }
 class LoginFailedException extends BaseException
 {
    public String message;
    public LoginFailedException(String message)
    {
        super();
        this.message = message;
    }
 }

 class UserNotFoundException extends BaseException
 {
    public String message;
    public UserNotFoundException(String message)
    {
        super();
        this.message = message;
    }
 }
