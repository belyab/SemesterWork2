package ru.kpfu.itis.baigulova.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class GameService {
    public static Integer[] getGameMap(int positionOnMap) {
        if(positionOnMap == 0){
            return new Integer[] {
                    1,0,0,
                    0,0,0,
                    0,0,2
            };
        } else {
            return new Integer[]{
                    2,0,0,
                    0,0,0,
                    0,0,1
            };
        }
    }

    public static String gameMapToString(Integer[] gameMap) {
        try {

            final StringWriter writer = new StringWriter();
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, gameMap);

            System.out.println(writer.toString());
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
