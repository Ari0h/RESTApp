package RESTApp;

import RESTApp.model.Tarif;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("tarifs")
public class TarifServices {
    private Logger logger;
    private DbHelper dbHelper;

    public TarifServices() {
        logger = Logger.getRootLogger();
        dbHelper = new DbHelper(logger);
    }

    @GET
    @Produces("application/json; charset=UTF-8")
    public Response getTarifs() {
        Response response = Response.status(Response.Status.NOT_FOUND).entity("Tarif's not found").build();
        List<Tarif> tarifs = dbHelper.getTarifs();
        if (!tarifs.isEmpty()) {
            String strTarifs = new Gson().toJson(tarifs).replace("[", "").replace("]", "");
            Response.ResponseBuilder builder = Response.ok(strTarifs);
            return builder.build();
        }
        return response;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setTarif(Tarif tarif) {
        int result = dbHelper.insertTarif(tarif);
        if (result == 1) {
            return Response.status(Response.Status.OK).entity("Tarif added...").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Operation failed").build();
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateTarif(Tarif tarif){
        int result = dbHelper.updateTarif(tarif);
        if (result == 1) {
            return Response.status(Response.Status.OK).entity("Tarif updated...").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Operation failed").build();
        }
    }

}
