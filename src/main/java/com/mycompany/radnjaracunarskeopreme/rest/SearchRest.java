package com.mycompany.radnjaracunarskeopreme.rest;

import com.mycompany.radnjaracunarskeopreme.service.SearchService;
import com.mycompany.radnjaracunarskeopreme.dao.SearchDao;
import com.mycompany.radnjaracunarskeopreme.dao.ResourcesManager;
import com.mycompany.radnjaracunarskeopreme.data.Search;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/searches")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchRest {
    private final SearchService searchService;

    public SearchRest() {
        try {
            // Korišćenje ResourcesManager za upravljanje konekcijom
            Connection connection = ResourcesManager.getConnection();
            SearchDao searchDao = new SearchDao(connection);
            this.searchService = new SearchService(searchDao);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Неуспешно повезивање са базом података", e);
        }
    }

    @GET
    public Response getAllSearches() {
        try (Connection connection = ResourcesManager.getConnection()) {
            return Response.ok(searchService.getAllSearches()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању свих претрага.").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getSearchById(@PathParam("id") int id) {
        try (Connection connection = ResourcesManager.getConnection()) {
            Search search = searchService.getSearchById(id);
            if (search == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Претрага са датим ID-јем није пронађена.").build();
            }
            return Response.ok(search).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању претраге.").build();
        }
    }

    @POST
    public Response createSearch(Search search) {
        try (Connection connection = ResourcesManager.getConnection()) {
            Search createdSearch = searchService.createSearch(search);
            return Response.status(Response.Status.CREATED).entity(createdSearch).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при креирању претраге.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSearch(@PathParam("id") int id) {
        try (Connection connection = ResourcesManager.getConnection()) {
            searchService.deleteSearch(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при брисању претраге.").build();
        }
    }
}
