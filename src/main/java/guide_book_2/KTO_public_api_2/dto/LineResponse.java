package guide_book_2.KTO_public_api_2.dto;

import java.util.Map;

public class LineResponse implements OAuth2Response {


    private final Map<String, Object> attribute;

    public LineResponse(Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "LINE";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();

    }
}