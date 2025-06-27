package com.mycompany.radnjaracunarskeopreme.rest;

import com.mycompany.radnjaracunarskeopreme.service.PurchaseService;
import com.mycompany.radnjaracunarskeopreme.dao.PurchaseDao;
import com.mycompany.radnjaracunarskeopreme.dao.ProductDao;
import com.mycompany.radnjaracunarskeopreme.dao.UserDao;
import com.mycompany.radnjaracunarskeopreme.dao.ResourcesManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/purchases")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PurchaseRest {
    private final PurchaseService purchaseService;

    public PurchaseRest() {
        try {
            // Korišćenje ResourcesManager za kreiranje konekcije
            Connection connection = ResourcesManager.getConnection();
            this.purchaseService = PurchaseService.getInstance(
                PurchaseDao.getInstance(connection),
                ProductDao.getInstance(connection),
                UserDao.getInstance(connection),
                connection
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Неуспешно повезивање са базом података", e);
        }
    }

    @POST
    @Path("/makePurchase")
    public Response makePurchase(@QueryParam("userId") int userId, @QueryParam("productId") int productId) {
        try (Connection connection = ResourcesManager.getConnection()) {
            purchaseService.makePurchase(userId, productId);
            return Response.ok("Куповина успешно обављена.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при обради куповине.").build();
        }
    }
}
