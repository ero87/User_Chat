package am.vtc.userchat.servlet.user;

import am.vtc.userchat.exception.DatabaseException;
import am.vtc.userchat.model.User;
import am.vtc.userchat.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

@WebServlet("/usersList")
public class UsersListUserServlet extends BaseUserServlet {

    private Map<Integer, Date> usersActivityMap;

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (usersActivityMap == null) {
                usersActivityMap = (Map<Integer, Date>) req.getServletContext().getAttribute("users_activity");
            }
            resp.setContentType("application/json");
            List<User> userList = super.userService.getAll();
            List<UserView> userViews = new ArrayList<>(userList.size());
            for (User user : userList) {
                UserView userView = new UserView();
                userView.id = user.getId();
                userView.imageUrl = user.getImageUrl();
                userView.fullName = user.getName() + " " + user.getSurname();
                Date activeDate = usersActivityMap.get(user.getId());
                userView.active = (activeDate != null &&
                        System.currentTimeMillis() - activeDate.getTime() < 15 * 60 * 1000);
                userView.lastActiveTime = activeDate != null ? activeDate.getTime() : -1;
                userViews.add(userView);
            }
            userViews.sort(Comparator.comparing(UserView::getLastActiveTime).reversed());
            try (Writer writer = resp.getWriter()){
                writer.write(JsonUtil.serialize(userViews));
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static class UserView {
        @JsonProperty("sender_id")
        int id;
        @JsonProperty("image_url")
        String imageUrl;
        @JsonProperty("full_name")
        String fullName;
        boolean active;

        @JsonProperty("has_new_message")
        boolean hasNewMessage;

        @JsonIgnore
        long lastActiveTime;

        public long getLastActiveTime() {
            return lastActiveTime;
        }

        public void setLastActiveTime(long lastActiveTime) {
            this.lastActiveTime = lastActiveTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}
