package ca.cmpt213.asn5.server.controller;

import ca.cmpt213.asn5.server.model.Tokimon;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Hakim Bashe 301390647 hbashe@sfu.ca
 * CMPT 213 Assignment 5
 * Tokimon Controller
 * This class contains a list storing all the tokimons on the server
 * It allows you to post and get tokimons from the server
 * and also reads and writes from the tokimon.json file
 */
@RestController
public class TokimonController {
    //List of tokis and counter
    public List<Tokimon> tokis = new ArrayList<>();
    private AtomicLong nextID = new AtomicLong();

    //Returns all the tokis on the server
    @GetMapping("/tokimon/all")
    public List<Tokimon> getTokis() {
        return tokis;
    }

    //Returns a toki with a specific id
    @GetMapping("/tokimon/{id}")
    public Tokimon getSpecifcToki(@PathVariable("id") long tokiId) {
        for (Tokimon temp : tokis) {
            if (temp.getId() == tokiId) {
                return temp;
            }
        }
        throw new IllegalArgumentException("Invalid Id Number");
    }

    //Adds a toki to the end of the arraylist
    @PostMapping("/tokimon/add")
    public void addToki(@RequestBody Tokimon newToki, HttpServletResponse response) {
        //Set toki to have next id;
        newToki.setId(nextID.incrementAndGet());
        response.setStatus(201);
        tokis.add(newToki);
    }

    //Replaces a toki with a specifc id
    @PostMapping("/tokimon/change/{id}")
    public void updateToki(@PathVariable("id") long tokiId, @RequestBody Tokimon newToki, HttpServletResponse response) {
        for (int i = 0; i < tokis.size(); i++) {
            if (tokis.get(i).getId() == tokiId) {
                //Replace if match found
                long temp = tokis.get(i).getId();
                response.setStatus(201);
                tokis.set(i, (newToki));
                tokis.get(i).setId(temp);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid Id number");
    }

    //Delete toki with a specific id
    @DeleteMapping("/tokimon/{id}")
    public void deleteToki(@PathVariable("id") long tokiId, HttpServletResponse response) {
        for (int i = 0; i < tokis.size(); i++) {
            if (tokis.get(i).getId() == tokiId) {
                tokis.remove(i);
                response.setStatus(204);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    //Runs on the start of the server, reads all data from the json file
    @PostConstruct
    public void InitIT() throws FileNotFoundException {
        String input = "data/tokimon.json";
        //Got part of this code from Brian Fraser's video on json/gson
        //https://www.youtube.com/watch?v=HSuVtkdej8Q&list=UUMnSzocL2Z5hhOJglLQF9oA&index=6&t=0s&app=desktop
        //Code follows similar logic to my read json function in assignment 2
        JsonElement element = JsonParser.parseReader(new FileReader(input));
        JsonArray jsonArrayOfTeams = element.getAsJsonArray();
        //Loop thru the array copying elements to put into a tokimon object
        for (int i = 0; i < jsonArrayOfTeams.size(); i++) {
            //Get all the elements of a tokimon
            JsonObject tokiArray = jsonArrayOfTeams.get(i).getAsJsonObject();
            String name = tokiArray.get("name").getAsString();
            double weight = tokiArray.get("weight").getAsDouble();
            double height = tokiArray.get("height").getAsDouble();
            String ability = tokiArray.get("ability").getAsString();
            double strength = tokiArray.get("strength").getAsDouble();
            String colour = tokiArray.get("colour").getAsString();
            Tokimon toki = new Tokimon(name, weight, height, ability, strength, colour);
            toki.setId(nextID.incrementAndGet());
            tokis.add(toki);

        }

    }

    //Run when the server starts
    @PreDestroy
    public void writeToFile() throws IOException {
        //Data from this file
        String output = "data/tokimon.json";
        FileWriter data = new FileWriter(output);
        JsonArray array = new JsonArray();
        for (int i = 0; i < tokis.size(); i++) {
            //Convert all the json arrays into tokimons
            JsonObject tempToki = new JsonObject();
            tempToki.addProperty("id", tokis.get(i).getId());
            tempToki.addProperty("name", tokis.get(i).getName());
            tempToki.addProperty("weight", tokis.get(i).getWeight());
            tempToki.addProperty("height", tokis.get(i).getHeight());
            tempToki.addProperty("ability", tokis.get(i).getAbility());
            tempToki.addProperty("strength", tokis.get(i).getStrength());
            tempToki.addProperty("colour", tokis.get(i).getColour());
            array.add(tempToki);
        }
        data.write(String.valueOf(array));
        data.flush();
    }
}



