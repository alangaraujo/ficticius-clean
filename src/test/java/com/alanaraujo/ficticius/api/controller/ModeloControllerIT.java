package com.alanaraujo.ficticius.api.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.Date;

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
import com.alanaraujo.ficticiusclean.api.dto.SalvarVeiculoInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarVeiculoOutput;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@TestPropertySource("/application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FicticiusCleanApplication.class)
public class ModeloControllerIT {
	
	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
	}
	
	@Test
	public void whenPostModeloThenReturnCreated() {
		
		SalvarModeloInput modelo = new SalvarModeloInput("Picanto", new ReferencedId(1L));
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(modelo)
				.when()
				.post("/modelos")
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("id", CoreMatchers.notNullValue())
				.body("nome", CoreMatchers.equalTo(modelo.getNome()));
	}
	
	@Test
	public void whenPostModeloWithoutNomeAndMarcaThenReturnBadRequest() {
		
		SalvarModeloInput modelo = new SalvarModeloInput();
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(modelo)
				.when()
				.post("/modelos")
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void whenPutModeloThenReturnOk() {
		
		SalvarModeloInput modelo = new SalvarModeloInput("Picanto", new ReferencedId(1L));
		
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(modelo)
				.when()
				.post("/modelos");
		
		response.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("nome", CoreMatchers.equalTo("Picanto"));
				
		Integer id = response.path("id");
		modelo.setNome("Kia");
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(modelo)
				.when()
				.put("/modelos/{id}", id)
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", CoreMatchers.equalTo(id))
				.body("nome", CoreMatchers.equalTo("Kia"));
	}
	
	@Test
	public void whenPutModeloWithNonExistentMarcaIdThenReturnBadRequest() {
		
		SalvarModeloInput modelo = new SalvarModeloInput("i30", new ReferencedId(30L)); 
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(modelo)
				.when()
				.put("/modelos/{id}", 999)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void whenGetModelosThenReturnOk() {
		
		SalvarMarcaOutput marca = createMarca("Mercedes");				
		SalvarModeloOutput modelo = createModelo("C120", marca.getId());
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/modelos")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("$", hasSize(Matchers.greaterThan(0)))
				.body("id", CoreMatchers.hasItems(modelo.getId().intValue()))
				.body("nome", CoreMatchers.hasItems(modelo.getNome()))
				.body("marca.id", CoreMatchers.hasItems(marca.getId().intValue()))
				.body("marca.nome", CoreMatchers.hasItems(marca.getNome()));
	}
	
	@Test
	public void whenGetModeloThenReturnOk() {
		
		SalvarMarcaOutput marca = createMarca("Mercedes");
		SalvarModeloOutput modelo = createModelo("C120", marca.getId());
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/modelos/{id}", modelo.getId())
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", equalTo(modelo.getId().intValue()))
				.body("nome", equalTo(modelo.getNome()))
				.body("marca.id", equalTo(marca.getId().intValue()))
				.body("marca.nome", equalTo(marca.getNome()));
	}
	
	@Test
	public void whenGetModeloThenReturnNotFound() {		
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/modelos/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void whenDeleteModeloThenReturnNoContent() {
		
		SalvarMarcaOutput marca = createMarca("Mercedes");
		SalvarModeloOutput modelo = createModelo("C120", marca.getId());
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/modelos/{id}", modelo.getId())
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());		
	}
	
	@Test
	public void whenDeleteModeloThenReturnNotFound() {
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/modelos/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());		
	}
	
	@Test
	public void whenDeleteReferencedModeloThenReturnConflict() {
		
		SalvarMarcaOutput marca = createMarca("Mercedes");
		SalvarModeloOutput modelo = createModelo("C120", marca.getId());
		createVeiculo("Veiculo Mercedes", modelo.getId());
				
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/modelos/{id}", modelo.getId())
				.then()
				.statusCode(HttpStatus.CONFLICT.value());		
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
	
	private SalvarVeiculoOutput createVeiculo(String veiculo, Long modeloId) {
		
		SalvarVeiculoInput veiculoInput = new SalvarVeiculoInput(veiculo, new ReferencedId(modeloId), new Date(), 1, 1);
		
		return RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculoInput)
				.when()
				.post("/veiculos")
				.as(SalvarVeiculoOutput.class);		
	}

}
