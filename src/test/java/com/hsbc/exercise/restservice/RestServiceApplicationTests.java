package com.hsbc.exercise.restservice;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@Test
	void getAllBook() throws Exception {

		mvc.perform(get("/books")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Introduction To Java")));
	}

	@Test
	void findBooksByPartialTitleReturnsAllBooks() throws Exception {
		mvc.perform(get("/books/title/Java")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Introduction To Java")))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	void findBooksByWrongTitleReturnsNoBook() throws Exception {
		mvc.perform(get("/books/title/FOO")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void findBooksByTitleReturnsSingleBook() throws Exception {
		mvc.perform(get("/books/title/Introduction To Java")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().string(containsString("Introduction To Java")));
	}

	@Test
	void findBookByIsbnCorrectlyFindsBook() throws Exception {

		mvc.perform(get("/books/isbn/ISBN123432")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Introduction To Java")))
				.andExpect(jsonPath("$.isbn", is("ISBN123432")));
	}

	@Test
	void canBorrowABook() throws Exception {
		mvc.perform(post("/borrow")
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("isbn", "ISBN123432")
				.accept(MediaType.APPLICATION_JSON))
           		.andExpect(status().isCreated())
           		.andExpect(jsonPath("$.period", is(14)));

		//borrowing same book causes error
		mvc.perform(post("/borrow")
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("isbn", "ISBN123432")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError())
				.andExpect(res -> res.getResponse().getErrorMessage().contains("Book already rented to the user"));
	}

	@Test
	void returnBook() throws Exception {
		mvc.perform(post("/borrow")
				.contentType(MediaType.APPLICATION_JSON)
				.param("userId", "1")
				.param("isbn", "ISBN6543")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		mvc.perform(post("/return")
				.param("userId", "1")
				.param("isbn", "ISBN6543")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	//Admin Controller

	@Test
	void addsNewBook() throws Exception {

		String requestBody =  "{ \"title\": \"junit\", \"isbn\": \"5432\", \"author\" : \"raza\", \"quantity\": 10 }";
		mvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.isbn", is("5432")));
	}

	@Test
	void removesBook() throws Exception {
		mvc.perform(delete("/book/4")).andExpect(status().isOk());
	}
}
