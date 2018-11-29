package org.apache.avro;

import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.junit.Test;
import org.slf4j.Logger;

public class TestUnionSelfReference {
  /** The logger for TestUnionSelfReference */
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(TestUnionSelfReference.class);
  
  private static final String SIMPLE_BINARY_TREE =
	  "{"
	  +"    \"namespace\": \"tree\","
	  +"    \"type\": \"record\","
	  +"    \"name\": \"Node\","
	  +"    \"fields\": ["
	  +"      {"
	  +"        \"name\": \"left\","
	  +"        \"type\": ["
	  +"          \"null\","
	  +"          {"
	  +"            \"type\": \"Node\""
	  +"          }"
	  +"        ],"
	  +"        \"default\": null"
	  +"      },"
	  +"      {"
	  +"        \"name\": \"right\","
	  +"        \"type\": ["
	  +"          \"null\","
	  +"          {"
	  +"            \"type\": \"Node\""
	  +"          }"
	  +"        ],"
	  +"        \"default\": null"
	  +"      }"
	  +"    ]"
	  +"  }";
  
  private static final String THREE_TYPE_UNION =
	  "{"
	  +"    \"namespace\": \"tree\","
	  +"    \"type\": \"record\","
	  +"    \"name\": \"Node\","
	  +"    \"fields\": ["
	  +"      {"
	  +"        \"name\": \"left\","
	  +"        \"type\": ["
	  +"          \"null\","
	  +"          \"string\","
	  +"          {"
	  +"            \"type\": \"Node\""
	  +"          }"
	  +"        ],"
	  +"        \"default\": null"
	  +"      },"
	  +"      {"
	  +"        \"name\": \"right\","
	  +"        \"type\": ["
	  +"          \"null\","
	  +"          \"string\","
	  +"          {"
	  +"            \"type\": \"Node\""
	  +"          }"
	  +"        ],"
	  +"        \"default\": null"
	  +"      }"
	  +"    ]"
	  +"  }";
  @Test
  public void testSelfReferenceInUnion(){ 
     Schema schema = new Schema.Parser().parse(SIMPLE_BINARY_TREE);
     Field leftField = schema.getField("left");
     assertEquals(JsonProperties.NULL_VALUE,leftField.defaultVal());
     final Schema leftFieldSchema = leftField.schema();
     assertEquals(Type.UNION,leftFieldSchema.getType());
     assertEquals("null",leftFieldSchema.getTypes().get(0).getName());
     assertEquals("Node",leftFieldSchema.getTypes().get(1).getName());
     
     Field rightField = schema.getField("right");
     assertEquals(JsonProperties.NULL_VALUE,rightField.defaultVal());
     final Schema rightFieldSchema = rightField.schema();
     assertEquals(Type.UNION,rightFieldSchema.getType());
     assertEquals("null",rightFieldSchema.getTypes().get(0).getName());
     assertEquals("Node",rightFieldSchema.getTypes().get(1).getName());
  }
  
  @Test
  public void testSelfReferenceInThreeUnion(){ 
     Schema schema = new Schema.Parser().parse(THREE_TYPE_UNION);
     Field leftField = schema.getField("left");
     assertEquals(JsonProperties.NULL_VALUE,leftField.defaultVal());
     final Schema leftFieldSchema = leftField.schema();
     assertEquals(Type.UNION,leftFieldSchema.getType());
     assertEquals("null",leftFieldSchema.getTypes().get(0).getName());
     assertEquals("string",leftFieldSchema.getTypes().get(1).getName());
     assertEquals("Node",leftFieldSchema.getTypes().get(2).getName());
     
     Field rightField = schema.getField("right");
     assertEquals(JsonProperties.NULL_VALUE,rightField.defaultVal());
     final Schema rightFieldSchema = rightField.schema();
     assertEquals(Type.UNION,rightFieldSchema.getType());
     assertEquals("null",rightFieldSchema.getTypes().get(0).getName());
     assertEquals("string",rightFieldSchema.getTypes().get(1).getName());
     assertEquals("Node",rightFieldSchema.getTypes().get(2).getName());
  }
  
}
