package com.mycompany.radnjaracunarskeopreme.rest;

import com.mycompany.radnjaracunarskeopreme.service.UserService;
import com.mycompany.radnjaracunarskeopreme.data.User;
import com.mycompany.radnjaracunarskeopreme.dao.UserDao;
import com.mycompany.radnjaracunarskeopreme.dao.ResourcesManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRest {
    private final UserService userService;

    public UserRest() {
        try {
            Connection connection = ResourcesManager.getConnection(); // Korišćenje ResourcesManager-a
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
        try (Connection connection = ResourcesManager.getConnection()) {
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
        try (Connection connection = ResourcesManager.getConnection()) {
            List<User> users = userService.getAllUsers();
            return Response.ok(users).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању листе корисника.").build();
        }
    }

    @POST
    public Response createUser(User user) {
        try (Connection connection = ResourcesManager.getConnection()) {
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
        try (Connection connection = ResourcesManager.getConnection()) {
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
        try (Connection connection = ResourcesManager.getConnection()) {
            userService.deleteUser(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при брисању корисника.").build();
        }
    }
}

