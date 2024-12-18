package com.mycompany.radnjaracunarskeopreme.rest;

import com.mycompany.radnjaracunarskeopreme.service.PurchaseService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;


@Path("/purchases")
public class PurchaseRest {
    private PurchaseService purchaseService;

    public PurchaseRest(Connection connection) {
        this.purchaseService = new PurchaseService(
            new com.mycompany.radnjaracunarskeopreme.dao.PurchaseDao(connection),
            new com.mycompany.radnjaracunarskeopreme.dao.ProductDao(connection),
            new com.mycompany.radnjaracunarskeopreme.dao.UserDao(connection),
            connection
        );
    }

    @POST
    @Path("/makePurchase")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makePurchase(@QueryParam("userId") int userId, @QueryParam("productId") int productId) {
        try {
            purchaseService.makePurchase(userId, productId);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
