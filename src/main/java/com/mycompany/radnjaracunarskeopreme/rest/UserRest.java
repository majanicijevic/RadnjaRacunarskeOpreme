package com.mycompany.radnjaracunarskeopreme.rest;

import com.mycompany.radnjaracunarskeopreme.service.*;
import com.mycompany.radnjaracunarskeopreme.data.*;
import com.mycompany.radnjaracunarskeopreme.dao.UserDao;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRest {
    private UserService userService;

    public UserRest() {
        try {
            // Успостављање везе са базом података
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ime_baze", "user", "password");
            UserDao userDao = new UserDao(connection);
            this.userService = new UserService(userDao);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Неуспешно повезивање са базом података", e);
        }
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Корисник није пронађен.").build();
            }
            return Response.ok(user).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању корисника.").build();
        }
    }

    @GET
    public Response getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return Response.ok(users).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању листе корисника.").build();
        }
    }

    @POST
    public Response createUser(User user) {
        try {
            userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при креирању корисника.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        try {
            user.setIdUser(id);
            userService.updateUser(user);
            return Response.ok(user).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при ажурирању корисника.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        try {
            userService.deleteUser(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при брисању корисника.").build();
        }
    }
}
