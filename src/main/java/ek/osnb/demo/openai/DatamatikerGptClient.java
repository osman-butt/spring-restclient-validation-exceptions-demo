package ek.osnb.demo.openai;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/responses")
public interface DatamatikerGptClient {

    @PostExchange
    OpenAiResponse getResponse(@RequestBody OpenAiRequest request);
}
