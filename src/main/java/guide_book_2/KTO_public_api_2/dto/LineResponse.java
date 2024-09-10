package guide_book_2.KTO_public_api_2.dto;

import java.util.Map;

public class LineResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public LineResponse(Map<String, Object> attribute) {

        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {
        return "LINE";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
//LINE 반환값
//{
//        "sub": "U1234567890abcdef1234567890abcdef",
//        "name": "Taro Line",
//        "picture": "https://profile.line-scdn.net/0h8pWWElvzZ19qLk3ywQYYCFZraTIdAGEXEhx9ak56MDxDHiUIVEEsPBspMG1EGSEPAk4uP01t0m5G"
//        }