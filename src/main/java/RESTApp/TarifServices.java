package RESTApp;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("tarifs")
public class TarifServices {
    private Logger logger;
    private DbHelper dbHelper;

    public TarifServices(){
        logger = Logger.getLogger("UserServices");
        BasicConfigurator.configure();
        logger.setLevel(Level.DEBUG);
        dbHelper = new DbHelper(logger);
    }

    @GET
    @Produces("application/json; charset=UTF-8")
    public Response getTarifs(){
    Response response = Response.status(Response.Status.NOT_FOUND).entity("Tarif's not found").build();

    return response;
    }

}
