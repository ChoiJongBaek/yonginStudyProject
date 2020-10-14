package com.commonFunction.Controller;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.commonFunction.VO.chatMessageVO;
import com.commonFunction.VO.chatRoomVO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EchoHandler extends TextWebSocketHandler {
	ObjectMapper objectMapper = new ObjectMapper();
	//private Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
	
	
	// afterConnectionEstablished : �������� ����Ǹ� ȣ��Ǵ� �Լ�
    // �������� ���� �Ǵ� �� = ����Ʈ���� �������� ��Ȯ�� ��θ� ��� ���� �Ǵ� ��
    @Override
    public void afterConnectionEstablished(WebSocketSession session)  throws Exception {
        System.out.printf("%s ���� ��\n", session.getId());
        
       // users.put(session.getId(), session);
    }

    // ������ Ŭ���̾�Ʈ�� �ؽ�Ʈ �޼��� ���۽� ȣ��Ǵ� �޼ҵ�
    // WebSocketSession session : ���� ��ü ������ ��� ����
    // TextMessage message : ���� ���� �޼��� ����
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.printf("%s�κ��� [%s] ����\n", session.getId(), message.getPayload());
        
    	String msg = message.getPayload();
    	System.out.println("msg : "+msg);
        chatMessageVO chatMessageVO = objectMapper.readValue(msg,chatMessageVO.class);
        System.out.println("chatMessageVO : "+chatMessageVO.getMessage());
        chatRoomVO chatRoomVO = chatRoomRepository.findRoomById(chatMessageVO.getStudyCode());
        System.out.println("chatRoomVO session : "+chatRoomVO.getSessions());
        chatRoomVO.handleMessage(session,chatMessageVO,objectMapper);
    }

    // afterConnectionClosed : �������� ������ ����Ǹ� ȣ��Ǵ� �Լ�
    // �������� ������ ���� = ���� ����
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
       
    	chatRoomRepository.removeChatRoomBySession(session);
    	System.out.printf("%s ���� ����\n", session.getId());
        
       // users.remove(session.getId());
    }
    
}