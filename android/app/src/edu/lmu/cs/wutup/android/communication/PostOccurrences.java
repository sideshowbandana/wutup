package edu.lmu.cs.wutup.android.communication;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.lmu.cs.wutup.android.manager.LogTags;

public class PostOccurrences extends HttpWutup {
    
    public static final String DEFAULT_ERROR_MESSAGE = "Failed to post occurrence!";
    
    public static final int INDEX_OF_EVENT_ID_IN_PARAMETERS = 0;
    public static final int INDEX_OF_VENUE_ID_IN_PARAMETERS = 1;
    public static final int INDEX_OF_START_IN_PARAMETERS = 2;
    public static final int INDEX_OF_END_IN_PARAMETERS = 3;

    @Override
    protected Object doInBackground(Object... parameters) {
        
        if (parameters[INDEX_OF_EVENT_ID_IN_PARAMETERS] instanceof Integer &&
            parameters[INDEX_OF_VENUE_ID_IN_PARAMETERS] instanceof Integer &&
            parameters[INDEX_OF_START_IN_PARAMETERS] instanceof String &&
            parameters[INDEX_OF_END_IN_PARAMETERS] instanceof String) {
            
            Integer eventId = (Integer) parameters[INDEX_OF_EVENT_ID_IN_PARAMETERS];
            Integer venueId = (Integer) parameters[INDEX_OF_VENUE_ID_IN_PARAMETERS];
            String start = (String) parameters[INDEX_OF_START_IN_PARAMETERS];
            String end = (String) parameters[INDEX_OF_END_IN_PARAMETERS];
            
            try {
                postOccurrence(eventId, venueId, start, end);
                
            } catch (ClientProtocolException clientProtocolException) {
                Log.e(LogTags.HTTP, DEFAULT_ERROR_MESSAGE, clientProtocolException);
                
            } catch (IOException ioException) {
                Log.e(LogTags.HTTP, DEFAULT_ERROR_MESSAGE, ioException);
            }
            
        } else {
            
            IllegalArgumentException illegalArgumentException  = new IllegalArgumentException();
            
            Log.e(LogTags.HTTP, 
                  "Passed invalid parameters for posting an occurrence! Requires an event ID, venue ID, start, and end.", 
                  illegalArgumentException);
            
            throw illegalArgumentException;
                    
        }

        return null;
        
    }
    
    private void postOccurrence(Integer eventId, Integer venueId, String start, String end) throws ClientProtocolException, IOException {
        
        HttpPost postOccurrence = new HttpPost(ADDRESS_OF_OCCURRENCES);
        String jsonForPostingOccurrence = generateJsonForPostingOccurrence(eventId, venueId, start, end);
        StringEntity entityForPostingOccurrence = new StringEntity(jsonForPostingOccurrence);
        
        postOccurrence.setEntity(entityForPostingOccurrence);
        postOccurrence.addHeader("Content-type", "application/json");
        
        client.execute(postOccurrence);
        
        Log.i(LogTags.HTTP, "Executed HTTP call to post occurrence with the following JSON. " + jsonForPostingOccurrence);
        
    }
    
    private String generateJsonForPostingOccurrence(Integer eventId, Integer venueId, String start, String end) throws JsonProcessingException {
        
        String jsonFormat = "{\"event\":{\"id\":%s},\"venue\":{\"id\":%s},\"start\":\"%s\",\"end\":\"%s\"}";
        String json = String.format(jsonFormat, eventId, venueId, start, end);
        
        return json;
                
    }

}
