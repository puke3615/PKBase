package pk.base.test;

import pk.base.dao.Delete;
import pk.base.dao.Key;
import pk.base.dao.SpName;

/**
 * @author zijiao
 * @version 2015/12/29
 * @Mark
 */
@SpName("user")
public interface IUserDao {

    String USERNAME = "username";
    String PASSWORD = "password";

    @Key(USERNAME)
    void setUsername(String username);

    @Key(USERNAME)
    String getUsername();

    @Delete(USERNAME)
    void deleteUsername();

    @Key(PASSWORD)
    void setPassword(String password);

    @Delete
    void deleteAll();

}
