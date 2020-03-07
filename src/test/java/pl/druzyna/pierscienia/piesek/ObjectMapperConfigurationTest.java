package pl.druzyna.pierscienia.piesek;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
class ObjectMapperConfigurationTest {

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void givenJsonWithMoreFieldsThanClass_parse() throws IOException {
		String jsonString = "{" +
				"\"id\": \"13\"," +
				"\"value\" : \"testValue\"," +
				" \"additionalField\" : \"addField\"" +
				"}";
		ExampleClass object = objectMapper.readValue(jsonString, ExampleClass.class);
		assertEquals(object.getId(), 13L);
		assertEquals(object.getValue(), "testValue");
	}

	@Test
	void givenJsonWithLessFieldsThanClass_parse() throws IOException {
		String jsonString = "{" +
				"\"id\": \"15\"" +
				"}";
		ExampleClass userAccountObj = objectMapper.readValue(jsonString, ExampleClass.class);
		assertEquals(userAccountObj.getId(), 15L);
		assertNull(userAccountObj.getValue());
	}

	private static class ExampleClass {
		private Long id;
		private String value;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
