package RESTApp;


import RESTApp.model.User;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("users")
public class UserServices {
    private Logger logger;
    private DbHelper dbHelper;

    public UserServices() {
        logger = Logger.getRootLogger();
        dbHelper = new DbHelper(logger);
    }

    @GET
    @Produces("application/json; charset=UTF-8")
    public Response getUser() {
        List<User> users = dbHelper.getUsers();
        if (!users.isEmpty()) {
            String strUsers = new Gson().toJson(users).replace("[", "").replace("]", "");
            Response.ResponseBuilder builder = Response.ok(strUsers);
            return builder.build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Users not found in DataBase").build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setUser(User user) {
        int result = dbHelper.insertUser(user);
        if (result == 1) {
            return Response.status(Response.Status.OK).entity("User added...").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Operation failed").build();

        }
    }
}
