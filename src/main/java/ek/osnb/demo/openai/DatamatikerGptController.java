package ek.osnb.demo.openai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gpt")
public class DatamatikerGptController {
    private final DatamatikerGptClient client;

    public DatamatikerGptController(DatamatikerGptClient client) {
        this.client = client;
    }

    public record ContentResponse(String response) {}

    @GetMapping
    public ContentResponse getResponse(@RequestParam(required = true) String prompt) {
        OpenAiRequest request = new OpenAiRequest(Model.GPT_4_1,prompt);
        OpenAiResponse response = client.getResponse(request);
        String message =response.output().getFirst().content().getFirst().text();
        return new ContentResponse(message);
    }
}
