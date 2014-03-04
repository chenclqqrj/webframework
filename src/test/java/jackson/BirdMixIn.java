package jackson;
 
import com.fasterxml.jackson.annotation.JsonProperty;
 
public abstract class BirdMixIn {
    BirdMixIn(@JsonProperty("name") String name) {
    };
 
    @JsonProperty("sound")
    abstract String getSound();
 
    @JsonProperty("mm")
    abstract String getHabitat();
}