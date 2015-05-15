package com.tan.oldphone.http;
import java.io.IOException;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.tan.oldphone.model.ChannelJson;
import com.tan.oldphone.model.MessageJson;

public class Json2Entity {
	private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    public void init() {
        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	public void readJson2Entity() {
		String json = HttpUtils.URLPost("http://localhost:8080/navim-api/member/login?username=abcdefghi&password=8AA99B1F439FF71293E95357BAC6FD94");
		try {
			MessageJson msg = objectMapper.readValue(json, MessageJson.class);
			System.out.println(msg.getMessage());
			json = HttpUtils.URLPost("http://localhost:8080/navim-api/member/getChannel");
			ChannelJson channel = objectMapper.readValue(json, ChannelJson.class);
			System.out.println(channel.getMessage());
//			System.out.println(channel.getData().getChannel());
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Json2Entity json2Entity = new Json2Entity();
		json2Entity.init();
		json2Entity.readJson2Entity();
	}
}
