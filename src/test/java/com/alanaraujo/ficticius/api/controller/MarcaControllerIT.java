package com.alanaraujo.ficticius.api.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.alanaraujo.ficticiusclean.FicticiusCleanApplication;
import com.alanaraujo.ficticiusclean.api.dto.ReferencedId;
import com.alanaraujo.ficticiusclean.api.dto.SalvarMarcaInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarMarcaOutput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarModeloInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarModeloOutput;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@TestPropertySource("/application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FicticiusCleanApplication.class)
public class MarcaControllerIT {
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
	}
	
	@Test
	public void whenPostMarcaThenReturnCreated() {
		
		SalvarMarcaInput marca = new SalvarMarcaInput("Chevrolet");
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(marca)
				.when()
				.post("/marcas")
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("id", CoreMatchers.notNullValue())
				.body("nome", CoreMatchers.equalTo(marca.getNome()));
	}
	
	@Test
	public void whenPostMarcaWithoutNomeThenReturnBadRequest() {
		
		SalvarMarcaInput marca = new SalvarMarcaInput();
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(marca)
				.when()
				.post("/marcas")
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void whenPutMarcaThenReturnOk() {
		
		SalvarMarcaInput marca = new SalvarMarcaInput("Chevrolet");
		
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(marca)
				.when()
				.post("/marcas");
		
		response.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("nome", CoreMatchers.equalTo("Chevrolet"));
				
		Integer id = response.path("id");
		marca.setNome("Kia");
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(marca)
				.when()
				.put("/marcas/{id}", id)
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", CoreMatchers.equalTo(id))
				.body("nome", CoreMatchers.equalTo("Kia"));
	}
	
	@Test
	public void whenPutMarcaWithNonExistentIdThenReturnNotFound() {
		
		SalvarMarcaInput marca = new SalvarMarcaInput("Hyundai"); 
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(marca)
				.when()
				.put("/marcas/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void whenGetMarcasThenReturnOk() {
		
		SalvarMarcaOutput mercedes = createMarca("Mercedes");
		SalvarMarcaOutput ferrari = createMarca("Ferrari");
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/marcas")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("$", hasSize(Matchers.greaterThan(0)))
				.body("id", CoreMatchers.hasItems(mercedes.getId().intValue(), ferrari.getId().intValue()))
				.body("nome", CoreMatchers.hasItems(mercedes.getNome(), ferrari.getNome()));				
	}
	
	@Test
	public void whenGetMarcaThenReturnOk() {
		
		SalvarMarcaOutput marca = createMarca("Mercedes");
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/marcas/{id}", marca.getId())
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", equalTo(marca.getId().intValue()))
				.body("nome", equalTo(marca.getNome()));
	}
	
	@Test
	public void whenGetMarcaThenReturnNotFound() {		
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/marcas/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void whenDeleteMarcaThenReturnNoContent() {
		
		SalvarMarcaOutput marca = createMarca("Mercedes");
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/marcas/{id}", marca.getId())
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());		
	}
	
	@Test
	public void whenDeleteMarcaThenReturnNotFound() {
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/marcas/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());		
	}
	
	@Test
	public void whenDeleteReferencedMarcaThenReturnConflict() {
		
		SalvarMarcaOutput marca = createMarca("Ferrari");
		createModelo("Enzo", marca.getId());
				
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/marcas/{id}", marca.getId())
				.then()
				.statusCode(HttpStatus.CONFLICT.value());		
	}
	
	private SalvarMarcaOutput createMarca(String marca) {
		
		SalvarMarcaInput marcaInput = new SalvarMarcaInput(marca);
		
		return RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(marcaInput)
				.when()
				.post("/marcas")
				.as(SalvarMarcaOutput.class);
	}
	
	private SalvarModeloOutput createModelo(String modelo, Long marcaId) {
		
		SalvarModeloInput modeloInput = new SalvarModeloInput(modelo, new ReferencedId(marcaId));
		
		return RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(modeloInput)
				.when()
				.post("/modelos")
				.as(SalvarModeloOutput.class);
	}
	
}
