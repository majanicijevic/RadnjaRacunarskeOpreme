package com.mycompany.radnjaracunarskeopreme.rest;

import com.mycompany.radnjaracunarskeopreme.service.ProductService;
import com.mycompany.radnjaracunarskeopreme.data.Product;
import com.mycompany.radnjaracunarskeopreme.dao.ProductDao;
import com.mycompany.radnjaracunarskeopreme.dao.ResourcesManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductRest {
    private final ProductService productService;

    public ProductRest() {
        try {
            Connection connection = ResourcesManager.getConnection(); // Korišćenje ResourcesManager-a
            ProductDao productDao = new ProductDao(connection);
            this.productService = new ProductService(productDao);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Неуспешно повезивање са базом података", e);
        }
    }

    @GET
    public Response getAllProducts() {
        try (Connection connection = ResourcesManager.getConnection()) {
            List<Product> products = productService.getAllProducts();
            return Response.ok(products).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању производа.").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") int id) {
        try (Connection connection = ResourcesManager.getConnection()) {
            Product product = productService.getProductById(id);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Производ није пронађен.").build();
            }
            return Response.ok(product).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при добијању производа.").build();
        }
    }

    @POST
    public Response createProduct(Product product) {
        try (Connection connection = ResourcesManager.getConnection()) {
            Product createdProduct = productService.createProduct(product);
            return Response.status(Response.Status.CREATED).entity(createdProduct).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при креирању производа.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") int id, Product product) {
        try (Connection connection = ResourcesManager.getConnection()) {
            product.setIdProduct(id);
            productService.updateProduct(product);
            return Response.ok(product).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при ажурирању производа.").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        try (Connection connection = ResourcesManager.getConnection()) {
            productService.deleteProduct(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Грешка при брисању производа.").build();
        }
    }
}
