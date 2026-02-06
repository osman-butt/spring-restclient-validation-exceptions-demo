package ek.osnb.demo.openai;

import com.fasterxml.jackson.annotation.JsonValue;

public record OpenAiRequest(Model model, String input) {}

enum Model {
    GPT_4_1("gpt-4.1"),
    GPT_5_2("gpt-5.2");

    private final String modelName;

    Model(String modelName) {
        this.modelName = modelName;
    }
    @JsonValue
    public String getModelName() {
        return modelName;
    }
}
