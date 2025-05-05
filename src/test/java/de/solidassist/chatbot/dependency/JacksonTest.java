package de.solidassist.chatbot.dependency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class tests the Jackson dependency by serializing and deserializing a simple
 * POJO (Plain Old Java Object). The test ensures that Jackson can correctly convert
 * Java objects to JSON and vice versa. The class specifically checks that the
 * fields of the `Person` class are properly serialized and deserialized.
 *
 * Expected result:
 * - A valid JSON string representing the Java object, and a successful conversion back
 *   to the Java object.
 *
 * Dependencies tested:
 * - Jackson (com.fasterxml.jackson.core:jackson-databind)
 */

public class JacksonTest {
    public static void main(String[] args) {
        // Create a simple POJO (Plain Old Java Object)
        Person person = new Person("Maisam", 30);

        try {
            // Convert Java object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(person);
            System.out.println("Serialized JSON: " + json);

            // Convert JSON back to Java object
            Person deserializedPerson = objectMapper.readValue(json, Person.class);
            System.out.println("Deserialized Object: " + deserializedPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Simple POJO
    public static class Person {
        @JsonProperty("name") // Explicitly marking the field for serialization
        private String name;

        @JsonProperty("age")  // Explicitly marking the field for serialization
        private int age;

        public Person() {} // Default constructor required for Jackson

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}