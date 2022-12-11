package vexi.user;

import com.google.common.collect.BiMap;

public class UserManager {

    public BiMap<String, User> users;

    public User getUser(String UUID) {
        return this.users.containsKey(UUID) ? (User)this.users.get(UUID) : null;
    }
}
