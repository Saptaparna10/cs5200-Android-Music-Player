
package project.random.fall2018.cs5200.com.myapplication.vo.songs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "artist",
    "url",
    "streamable",
    "listeners",
    "image",
    "mbid"
})
public class Track {

    @JsonProperty("name")
    private String name;
    @JsonProperty("artist")
    private String artist;
    @JsonProperty("url")
    private String url;
    @JsonProperty("streamable")
    private String streamable;
    @JsonProperty("listeners")
    private String listeners;
    @JsonProperty("image")
    private List<Image> image = null;
    @JsonProperty("mbid")
    private String mbid;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("artist")
    public String getArtist() {
        return artist;
    }

    @JsonProperty("artist")
    public void setArtist(String artist) {
        this.artist = artist;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("streamable")
    public String getStreamable() {
        return streamable;
    }

    @JsonProperty("streamable")
    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }

    @JsonProperty("listeners")
    public String getListeners() {
        return listeners;
    }

    @JsonProperty("listeners")
    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    @JsonProperty("image")
    public List<Image> getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(List<Image> image) {
        this.image = image;
    }

    @JsonProperty("mbid")
    public String getMbid() {
        return mbid;
    }

    @JsonProperty("mbid")
    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
