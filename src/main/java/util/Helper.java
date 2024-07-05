package util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Helper {
    public  static String toJson(Object root){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(root);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
