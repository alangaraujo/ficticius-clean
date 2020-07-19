package com.alanaraujo.ficticius.api.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.Date;

import javax.sql.DataSource;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.alanaraujo.ficticius.cleaner.DatabaseCleaner;
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
public class VeiculoControllerIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DataSource dataSource;
	
	private DatabaseCleaner dbCleaner;
	
	@Before
	public void setUp() {
		RestAssured.port = port;
		dbCleaner = new DatabaseCleaner(dataSource);
		dbCleaner.clearTables();
	}

	@Test
	public void whenGetRankingVehicleBIsEconomicThenReturnOk() {
	
		Date dataFabricacao = new Date();
		
		SalvarMarcaOutput marcaA = createMarca("Marca A");				
		SalvarModeloOutput modeloA = createModelo("Modelo A", marcaA.getId());
		SalvarVeiculoOutput veiculoA = createVeiculo("Veiculo A", modeloA.getId(), dataFabricacao, 10, 12);
		
		SalvarMarcaOutput marcaB = createMarca("Marca B");				
		SalvarModeloOutput modeloB = createModelo("Modelo B", marcaB.getId());
		SalvarVeiculoOutput veiculoB = createVeiculo("Veiculo B", modeloB.getId(), dataFabricacao, 9, 13);
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.queryParam("kmTotalCidade", 10)
				.queryParam("kmTotalRodovia", 120)
				.queryParam("valorCombustivel", 3.99)
				.when()
				.get("/veiculos/ranking")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("$", hasSize(2))
				.body("nome", Matchers.contains(veiculoB.getNome(), veiculoA.getNome()))
				.body("consumoCombustivel", Matchers.contains(10.34F, 11F))
				.body("valorCombustivel", Matchers.contains(41.26F, 43.89F));		
	}
	
	@Test
	public void whenGetRankingVehicleAIsEconomicThenReturnOk() {
		
		Date dataFabricacao = new Date();
		
		SalvarMarcaOutput marcaA = createMarca("Marca A");				
		SalvarModeloOutput modeloA = createModelo("Modelo A", marcaA.getId());
		SalvarVeiculoOutput veiculoA = createVeiculo("Veiculo A", modeloA.getId(), dataFabricacao, 10, 12);
		
		SalvarMarcaOutput marcaB = createMarca("Marca B");				
		SalvarModeloOutput modeloB = createModelo("Modelo B", marcaB.getId());
		SalvarVeiculoOutput veiculoB = createVeiculo("Veiculo B", modeloB.getId(), dataFabricacao, 9, 13);
				
		RestAssured.given()
		.accept(ContentType.JSON)
		.queryParam("kmTotalCidade", 200)
		.queryParam("kmTotalRodovia", 50)
		.queryParam("valorCombustivel", 3.99)
		.when()
		.get("/veiculos/ranking")
		.then()
		.statusCode(HttpStatus.OK.value())
		.body("$", hasSize(2))
		.body("nome", Matchers.contains(veiculoA.getNome(), veiculoB.getNome()))
		.body("consumoCombustivel", Matchers.contains(24.17F, 26.07F))
		.body("valorCombustivel", Matchers.contains(96.44F, 104.02F));
	}
	
	@Test
	public void whenPostVeiculoThenReturnCreated() {
		
		SalvarMarcaOutput marca = createMarca("Chevrolet");
		SalvarModeloOutput modelo = createModelo("Prisma", marca.getId());
		SalvarVeiculoInput veiculo = new SalvarVeiculoInput("Veiculo", new ReferencedId(modelo.getId()), new Date(), 1, 1);
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculo)
				.when()
				.post("/veiculos")
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("id", CoreMatchers.notNullValue())
				.body("nome", CoreMatchers.equalTo(veiculo.getNome()))
				.body("modelo.id", CoreMatchers.equalTo(modelo.getId().intValue()));
	}
	
	@Test
	public void whenPostModeloWithoutNomeAndModeloAndMarcaThenReturnBadRequest() {
		
		SalvarVeiculoInput veiculo = new SalvarVeiculoInput();
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculo)
				.when()
				.post("/veiculos")
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void whenPutVeiculoThenReturnOk() {
		
		SalvarMarcaOutput marca = createMarca("Chevrolet");
		SalvarModeloOutput modelo = createModelo("Prisma", marca.getId());
		SalvarVeiculoInput veiculo = new SalvarVeiculoInput("Veiculo", new ReferencedId(modelo.getId()), new Date(), 1, 1);
		
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculo)
				.when()
				.post("/veiculos");
		
		response.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("nome", CoreMatchers.equalTo("Veiculo"));
				
		Integer id = response.path("id");
		veiculo.setNome("Veiculo Atualizado");
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculo)
				.when()
				.put("/veiculos/{id}", id)
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", CoreMatchers.equalTo(id))
				.body("nome", CoreMatchers.equalTo("Veiculo Atualizado"))
				.body("modelo.id", CoreMatchers.equalTo(modelo.getId().intValue()));
	}
	
	@Test
	public void whenPutVeiculoWithNonExistentModeloIdThenReturnBadRequest() {
		
		SalvarMarcaOutput marca = createMarca("Chevrolet");
		SalvarModeloOutput modelo = createModelo("Prisma", marca.getId());
		SalvarVeiculoInput veiculo = new SalvarVeiculoInput("Veiculo", new ReferencedId(modelo.getId()), new Date(), 1, 1); 
		
		RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculo)
				.when()
				.put("/veiculos/{id}", 999)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void whenGetVeiculosThenReturnOk() {
		
		SalvarMarcaOutput marca = createMarca("Chevrolet");
		SalvarModeloOutput modelo = createModelo("Prisma", marca.getId());
		SalvarVeiculoOutput veiculo = createVeiculo("Veiculo", modelo.getId(), new Date(), 1, 1); 
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/veiculos")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("$", hasSize(Matchers.greaterThan(0)))
				.body("id", CoreMatchers.hasItems(veiculo.getId().intValue()))
				.body("nome", CoreMatchers.hasItems(veiculo.getNome()))
				.body("modelo.id", CoreMatchers.hasItems(modelo.getId().intValue()))
				.body("modelo.nome", CoreMatchers.hasItems(modelo.getNome()))
				.body("modelo.marca.id", CoreMatchers.hasItems(marca.getId().intValue()))
				.body("modelo.marca.nome", CoreMatchers.hasItems(marca.getNome()));
	}
	
	@Test
	public void whenGetVeiculoThenReturnOk() {
		
		SalvarMarcaOutput marca = createMarca("Chevrolet");
		SalvarModeloOutput modelo = createModelo("Prisma", marca.getId());
		SalvarVeiculoOutput veiculo = createVeiculo("Veiculo", modelo.getId(), new Date(), 1, 1); 
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/veiculos/{id}", veiculo.getId())
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("id", CoreMatchers.equalTo(veiculo.getId().intValue()))
				.body("nome", CoreMatchers.equalTo(veiculo.getNome()))
				.body("modelo.id", CoreMatchers.equalTo(modelo.getId().intValue()))
				.body("modelo.nome", CoreMatchers.equalTo(modelo.getNome()))
				.body("modelo.marca.id", CoreMatchers.equalTo(marca.getId().intValue()))
				.body("modelo.marca.nome", CoreMatchers.equalTo(marca.getNome()));
	}
	
	@Test
	public void whenGetVeiculoThenReturnNotFound() {		
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.get("/veiculos/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void whenDeleteVeiculoThenReturnNoContent() {
		
		SalvarMarcaOutput marca = createMarca("Chevrolet");
		SalvarModeloOutput modelo = createModelo("Prisma", marca.getId());
		SalvarVeiculoOutput veiculo = createVeiculo("Veiculo", modelo.getId(), new Date(), 1, 1); 
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/veiculos/{id}", veiculo.getId())
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());		
	}
	
	@Test
	public void whenDeleteVeiculoThenReturnNotFound() {
		
		RestAssured.given()
				.accept(ContentType.JSON)
				.when()
				.delete("/veiculos/{id}", 999)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());		
	}
	
	private SalvarVeiculoOutput createVeiculo(String veiculo, Long modeloId, Date dataFabricacao, int consumoCidadeKmL, int consumoRodoviaKmL) {
		
		SalvarVeiculoInput veiculoInput = new SalvarVeiculoInput(veiculo, new ReferencedId(modeloId), dataFabricacao, consumoCidadeKmL, consumoRodoviaKmL);
		
		return RestAssured.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(veiculoInput)
				.when()
				.post("/veiculos")
				.as(SalvarVeiculoOutput.class);		
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
	
}
