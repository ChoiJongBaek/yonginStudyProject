package com.commonFunction.Controller;

import javax.annotation.Resource;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.commonFunction.Service.chatService;
import com.commonFunction.VO.chatMessageVO;
import com.commonFunction.VO.chatRoomVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EchoHandler extends TextWebSocketHandler {
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Resource(name="chatService")
	private chatService chatService;
	
	// afterConnectionEstablished : �������� ����Ǹ� ȣ��Ǵ� �Լ�
    // �������� ���� �Ǵ� �� = ����Ʈ���� �������� ��Ȯ�� ��θ� ��� ���� �Ǵ� ��
    @Override
    public void afterConnectionEstablished(WebSocketSession session)  throws Exception {
        
    }

    // ������ Ŭ���̾�Ʈ�� �ؽ�Ʈ �޼��� ���۽� ȣ��Ǵ� �޼ҵ�
    // WebSocketSession session : ���� ��ü ������ ��� ����
    // TextMessage message : ���� ���� �޼��� ����
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
    	String msg = message.getPayload();
        chatMessageVO chatMessageVO = objectMapper.readValue(msg,chatMessageVO.class);
        chatRoomVO chatRoomVO = chatRoomRepository.findRoomById(chatMessageVO.getStudyCode());
        
        chatMessageVO.setStudyCode(chatRoomVO.getStudyCode());

        // �޽����� null ���� �ƴ� ��� (���� or ������ �ƴ� �޽��� ������ ���)
        if(chatMessageVO.getMessage() != null) {
        	chatService.insertChat(chatMessageVO);
        }
        
        chatRoomVO.handleMessage(session,chatMessageVO,objectMapper);
        
    }

    // afterConnectionClosed : �������� ������ ����Ǹ� ȣ��Ǵ� �Լ�
    // �������� ������ ���� = ���� ����
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    	chatRoomRepository.removeChatRoomBySession(session);
    }
    
}