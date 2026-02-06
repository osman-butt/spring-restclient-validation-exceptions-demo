package ek.osnb.demo.openai;

import java.util.List;

public record OpenAiResponse(String id, List<Output> output) {}
record Output(String id, List<Content> content) {}
record Content(String text) {}
