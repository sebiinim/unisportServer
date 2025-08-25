//package com.example.unisportserver.service;
//
//import com.openai.client.OpenAIClient;
//import com.openai.models.ChatModel;
//import com.openai.models.responses.Response;
//import com.openai.models.responses.ResponseCreateParams;
//import com.openai.models.responses.ResponseOutputItem;
//import com.openai.models.responses.ResponseOutputMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ChatGptService {
//
//    // TODO: 카드를 등록합시다.
//
//    private final OpenAIClient openAIClient;
//
//    public String ask(String userQuery) {
//        ResponseCreateParams params = ResponseCreateParams.builder()
//                .model(ChatModel.GPT_5_NANO)
//                .input(userQuery)
//                .build();
//
//        Response res = openAIClient.responses().create(params);
//
//        String text = res.output().stream()
//                .filter(ResponseOutputItem::isMessage) // 메시지 아이템만
//                .map(ResponseOutputItem::asMessage)
//                .flatMap(msg -> msg.content().stream()) // 메시지의 content 파트들
//                .filter(ResponseOutputMessage.Content::isOutputText) // 텍스트 파트만
//                .map(c -> c.asOutputText().text())
//                .collect(java.util.stream.Collectors.joining());
//
//        return text;
//    }
//}
